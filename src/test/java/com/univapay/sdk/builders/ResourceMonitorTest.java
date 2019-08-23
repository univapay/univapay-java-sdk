package com.univapay.sdk.builders;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.errors.TooManyRequestsException;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.utils.UnivapayCallback;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ResourceMonitorTest {

  @Mock RequestBuilder<Integer, Request<Integer>> requestBuilder;

  @Mock Request<Integer> request;

  @Mock ResourcePredicate<Integer> predicate;

  ResourceMonitor<Integer> resourceMonitor;

  ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

  long currentTimeMillis = 0;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);

    when(request.dispatch()).thenReturn(0);

    doAnswer(
            new Answer<Void>() {
              @Override
              public Void answer(InvocationOnMock invocation) throws Throwable {
                final UnivapayCallback callback =
                    invocation.getArgumentAt(0, UnivapayCallback.class);

                executorService.submit(
                    new Runnable() {
                      @Override
                      public void run() {
                        callback.getResponse(0);
                      }
                    });
                return null;
              }
            })
        .when(request)
        .dispatch(any(UnivapayCallback.class));

    when(requestBuilder.build()).thenReturn(request);

    resourceMonitor = spy(new ResourceMonitor<>(requestBuilder, predicate));

    // Don't sleep for quick test, but accumulate the sleep time then return it at
    // #currentTimeMillis().
    doAnswer(
            new Answer<Object>() {
              @Override
              public Object answer(InvocationOnMock invocation) throws Throwable {
                final Long sleepTime = invocation.getArgumentAt(0, Long.class);
                currentTimeMillis += sleepTime;
                return null;
              }
            })
        .when(resourceMonitor)
        .sleep(anyLong());

    // Return the cumulative sleep time to simulate the elapsed time.
    doAnswer(
            new Answer<Long>() {
              @Override
              public Long answer(InvocationOnMock invocation) throws Throwable {
                return currentTimeMillis;
              }
            })
        .when(resourceMonitor)
        .currentTimeMillis();

    // Don't sleep for quick test
    doAnswer(
            new Answer<Object>() {
              @Override
              public Object answer(InvocationOnMock invocation) throws Throwable {
                final Runnable runnable = invocation.getArgumentAt(0, Runnable.class);
                executorService.schedule(runnable, 0, TimeUnit.MILLISECONDS);
                return null;
              }
            })
        .when(resourceMonitor)
        .delay(any(Runnable.class), anyInt());
  }

  @Test
  public void shouldAbortWhenExceedTimeout() throws Exception {
    // Arrange /////////////////////////////////
    when(predicate.test(anyInt())).thenReturn(false); // Never pass test
    when(resourceMonitor.currentTimeMillis()).thenReturn(0L, 9_999L, 10_000L, 10_001L);

    try {
      // Act /////////////////////////////////
      resourceMonitor.await(10_000);

      // Assert /////////////////////////////////
      fail("Timeout should occured");
    } catch (TimeoutException e) {
      verify(resourceMonitor, times(2)).sleep(anyLong());
      verify(request, times(2)).dispatch();
    }
  }

  @Test
  public void shouldUseDefaultTimeout() throws Exception {
    // Arrange //////////////////////////////
    // Never pass test
    when(predicate.test(anyInt())).thenReturn(false);
    // Never timeout
    when(resourceMonitor.currentTimeMillis())
        .thenReturn(
            0L, (long) ResourceMonitor.DEFAULT_TIMEOUT, (long) ResourceMonitor.DEFAULT_TIMEOUT + 1);

    try {
      // Act /////////////////////////////////
      resourceMonitor.await();
      // Assert /////////////////////////////////
      fail("Timeout should occured");
    } catch (TimeoutException e) {
      verify(resourceMonitor, times(1)).sleep(anyLong());
      verify(request, times(1)).dispatch();
    }
  }

  @Test
  public void shouldRetryRequestUntilPassTest() throws Exception {
    // Arrange /////////////////////////////////
    when(predicate.test(anyInt())).thenReturn(false, false, false, true);
    // Never timeout
    when(resourceMonitor.currentTimeMillis()).thenReturn(0L);

    // Act /////////////////////////////////
    resourceMonitor.await();

    // Assert /////////////////////////////////
    verify(request, times(4)).dispatch();
    verify(resourceMonitor, times(4)).sleep(anyLong());
  }

  @Test(expected = UnivapayException.class)
  public void shouldThrowIfRequestFailure() throws Exception {
    // Arrange //////////////////////////////
    // Never pass test
    when(request.dispatch()).thenThrow(new UnivapayException(400, "Test Error", null));

    // Act /////////////////////////////////
    resourceMonitor.await();
    // Assert /////////////////////////////////
    fail("API Error should be thrown");
  }

  @Test(expected = TimeoutException.class)
  public void shouldThrowTimeoutExceptionIfIOExceptionOccurred() throws Exception {
    // Arrange //////////////////////////////
    // Never pass test
    when(request.dispatch()).thenThrow(new SocketTimeoutException("Test"));

    // Act /////////////////////////////////
    resourceMonitor.await();
    // Assert /////////////////////////////////
    fail("API Error should be thrown");
  }

  @Test
  public void shouldRetryRequestUntilPassTestAsynchronous() throws Exception {
    // Arrange /////////////////////////////////
    when(predicate.test(anyInt())).thenReturn(false, false, false, true);
    // Never timeout
    when(resourceMonitor.currentTimeMillis()).thenReturn(0L);

    // Act /////////////////////////////////
    final CountDownLatch latch = new CountDownLatch(1);
    resourceMonitor.await(
        new UnivapayCallback<Integer>() {
          @Override
          public void getResponse(Integer response) {
            latch.countDown();
          }

          @Override
          public void getFailure(Throwable error) {
            fail();
            latch.countDown();
          }
        });

    latch.await(10, TimeUnit.SECONDS);

    // Assert /////////////////////////////////
    verify(request, times(4)).dispatch(any(UnivapayCallback.class));
    verify(resourceMonitor, times(4)).delay(any(Runnable.class), anyLong());
  }

  @Test
  public void shouldAbortWhenExceedTimeoutAsynchronous() throws Exception {
    // Arrange /////////////////////////////////
    when(predicate.test(anyInt())).thenReturn(false);
    when(resourceMonitor.currentTimeMillis()).thenReturn(0L, 9_999L, 10_000L, 999999L);

    // Act /////////////////////////////////
    final CountDownLatch latch = new CountDownLatch(1);
    resourceMonitor.await(
        new UnivapayCallback<Integer>() {
          @Override
          public void getResponse(Integer response) {
            latch.countDown();
            fail();
          }

          @Override
          public void getFailure(Throwable error) {
            latch.countDown();
            assertThat(error, instanceOf(TimeoutException.class));
          }
        },
        10_000);

    latch.await(10, TimeUnit.SECONDS);

    // Assert /////////////////////////////////
    verify(request, times(3)).dispatch(any(UnivapayCallback.class));
    verify(resourceMonitor, times(3)).delay(any(Runnable.class), anyLong());
  }

  @Test
  public void shouldRetryWhenTooManyRequestsUntilTimeoutAsynchronous() throws Exception {
    // Arrange /////////////////////////////////
    doAnswer(
            new Answer<Void>() {
              @Override
              public Void answer(InvocationOnMock invocation) throws Throwable {
                final UnivapayCallback callback =
                    invocation.getArgumentAt(0, UnivapayCallback.class);
                executorService.submit(
                    new Runnable() {
                      @Override
                      public void run() {
                        callback.getFailure(new TooManyRequestsException(429, "Too Many Requests"));
                      }
                    });
                return null;
              }
            })
        .when(request)
        .dispatch(any(UnivapayCallback.class));

    shouldThrowTimeoutExcepiton();
  }

  @Test
  public void shouldRetryWhenIOExceptionUntilTimeoutAsynchronous() throws Exception {
    // Arrange /////////////////////////////////
    doAnswer(
            new Answer<Void>() {
              @Override
              public Void answer(InvocationOnMock invocation) throws Throwable {
                final UnivapayCallback callback =
                    invocation.getArgumentAt(0, UnivapayCallback.class);
                executorService.submit(
                    new Runnable() {
                      @Override
                      public void run() {
                        callback.getFailure(new SocketTimeoutException());
                      }
                    });
                return null;
              }
            })
        .when(request)
        .dispatch(any(UnivapayCallback.class));

    shouldThrowTimeoutExcepiton();
  }

  private void shouldThrowTimeoutExcepiton() throws InterruptedException {
    when(resourceMonitor.currentTimeMillis()).thenReturn(0L, 9_999L, 10_000L, 999999L);

    final List<Throwable> result = new ArrayList<>();
    // Act /////////////////////////////////
    final CountDownLatch latch = new CountDownLatch(1);
    resourceMonitor.await(
        new UnivapayCallback<Integer>() {
          @Override
          public void getResponse(Integer response) {
            latch.countDown();
          }

          @Override
          public void getFailure(Throwable error) {
            result.add(error);
            latch.countDown();
          }
        },
        10_000);

    latch.await(10, TimeUnit.SECONDS);

    // Assert /////////////////////////////////
    Throwable error = result.get(0);
    assertThat(error, instanceOf(TimeoutException.class));
  }

  @Test
  public void shouldCallbackExceptionIfRequestFailure() throws Exception {
    // Arrange /////////////////////////////////
    doAnswer(
            new Answer<Void>() {
              @Override
              public Void answer(InvocationOnMock invocation) throws Throwable {
                invocation
                    .getArgumentAt(0, UnivapayCallback.class)
                    .getFailure(new UnivapayException(400, "Test Error", null));
                return null;
              }
            })
        .when(request)
        .dispatch(any(UnivapayCallback.class));

    // Act /////////////////////////////////
    final CountDownLatch latch = new CountDownLatch(1);
    resourceMonitor.await(
        new UnivapayCallback<Integer>() {
          @Override
          public void getResponse(Integer response) {
            fail();
            latch.countDown();
          }

          @Override
          public void getFailure(Throwable error) {
            assertThat(error, instanceOf(UnivapayException.class));
            latch.countDown();
          }
        });

    latch.await(10, TimeUnit.SECONDS);
  }
}
