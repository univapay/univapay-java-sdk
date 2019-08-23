package com.univapay.sdk.builders;

import com.univapay.sdk.models.errors.TooManyRequestsException;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.utils.Backoff;
import com.univapay.sdk.utils.ExponentialBackoff;
import com.univapay.sdk.utils.UnivapayCallback;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ResourceMonitor<T> {
  static final long DEFAULT_TIMEOUT = 60_000;
  private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
  private RequestBuilder<T, Request<T>> requestBuilder;
  private ResourcePredicate<T> predicate;

  public ResourceMonitor(
      RequestBuilder<T, Request<T>> requestBuilder, ResourcePredicate<T> predicate) {
    this.requestBuilder = requestBuilder;
    this.predicate = predicate;
  }

  Backoff createBackoff() {
    return new ExponentialBackoff(0, 30_000, 2, 0.5);
  }

  long currentTimeMillis() {
    return System.currentTimeMillis();
  }

  void sleep(long millis) throws InterruptedException {
    Thread.sleep(millis);
  }

  void delay(Runnable command, long millis) {
    executorService.schedule(command, millis, TimeUnit.MILLISECONDS);
  }

  public T await() throws IOException, InterruptedException, TimeoutException, UnivapayException {
    return await(DEFAULT_TIMEOUT);
  }

  public T await(long timeout)
      throws IOException, InterruptedException, TimeoutException, UnivapayException {
    return await(timeout, createBackoff());
  }

  public T await(long timeout, Backoff backoff)
      throws IOException, InterruptedException, TimeoutException, UnivapayException {
    T resource;

    final long start = currentTimeMillis();

    do {
      if (currentTimeMillis() - start > timeout) {
        throw new TimeoutException(String.format("Retry timeout exceeded : %dms ", timeout));
      }

      sleep(backoff.next());

      try {
        resource = requestBuilder.build().dispatch();
      } catch (TooManyRequestsException | IOException e) {
        // retry until timeout exceeded.
        resource = null;
      }

    } while (resource == null || !predicate.test(resource));

    return resource;
  }

  public void await(final UnivapayCallback<T> callback) {
    await(callback, DEFAULT_TIMEOUT);
  }

  public void await(final UnivapayCallback<T> callback, final long timeout) {
    await(callback, timeout, createBackoff());
  }

  public void await(final UnivapayCallback<T> callback, final long timeout, final Backoff backoff) {
    final long start = currentTimeMillis();

    Runnable runnable =
        new Runnable() {
          @Override
          public void run() {
            final Runnable self = this;
            UnivapayCallback<T> command =
                new UnivapayCallback<T>() {

                  private void retry() {
                    if (currentTimeMillis() - start > timeout) {
                      callback.getFailure(
                          new TimeoutException(
                              String.format("Retry timeout exceeded : %dms ", timeout)));
                    } else {
                      delay(self, backoff.next());
                    }
                  }

                  @Override
                  public void getResponse(T response) {
                    if (predicate.test(response)) {
                      callback.getResponse(response);
                    } else {
                      retry();
                    }
                  }

                  @Override
                  public void getFailure(Throwable error) {
                    if (error instanceof TooManyRequestsException || error instanceof IOException) {
                      retry();
                    } else {
                      callback.getFailure(error);
                    }
                  }
                };

            try {
              requestBuilder.build().dispatch(command);
            } catch (Throwable e) {
              callback.getFailure(new Error("Failed to execute request", e));
            }
          }
        };

    delay(runnable, backoff.next());
  }
}
