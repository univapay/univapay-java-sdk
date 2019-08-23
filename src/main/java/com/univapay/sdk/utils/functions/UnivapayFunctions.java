package com.univapay.sdk.utils.functions;

import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.utils.UnivapayCallback;
import java.io.IOException;
import com.univapay.sdk.builders.Request;
import com.univapay.sdk.models.errors.TooManyRequestsException;
import com.univapay.sdk.utils.Sleeper;

public class UnivapayFunctions {

  public static <T> EndoFunction<T> identity() {
    return new EndoFunction<T>() {
      @Override
      public T apply(T t) throws Exception {
        return t;
      }
    };
  }

  private static <T> boolean shouldHandle(ErrorHandler<T> handler, Throwable t) {
    return handler != null && handler.getShouldHandleError().test(t);
  }

  public static <M1, T1 extends Request<M1>, M2, T2 extends Request<M2>> Request<M2> compose(
      final T1 firstRequest,
      final Function<M1, T2> onSuccess,
      final ErrorHandler<T2> errorHandler) {

    return new Request<M2>() {
      @Override
      public void dispatch(final UnivapayCallback<M2> callback) {

        firstRequest.dispatch(
            new UnivapayCallback<M1>() {
              @Override
              public void getResponse(M1 response) {
                onSuccess.apply(response).dispatch(callback);
              }

              @Override
              public void getFailure(Throwable error) {

                if (shouldHandle(errorHandler, error)) {
                  errorHandler.getHandler().apply(error).dispatch(callback);
                } else {
                  callback.getFailure(error);
                }
              }
            });
      }

      @Override
      public M2 dispatch() throws IOException, UnivapayException, TooManyRequestsException {
        try {
          M1 response = firstRequest.dispatch();
          return onSuccess.apply(response).dispatch();
        } catch (IOException | UnivapayException error) {
          if (shouldHandle(errorHandler, error)) {
            return errorHandler.getHandler().apply(error).dispatch();
          } else {
            throw error;
          }
        }
      }

      @Override
      public M2 dispatch(int maxRetry, Sleeper sleeper)
          throws IOException, UnivapayException, TooManyRequestsException, InterruptedException {
        try {
          M1 response = firstRequest.dispatch(maxRetry, sleeper);
          return onSuccess.apply(response).dispatch(maxRetry, sleeper);
        } catch (IOException | UnivapayException error) {
          if (shouldHandle(errorHandler, error)) {
            return errorHandler.getHandler().apply(error).dispatch(maxRetry, sleeper);
          } else {
            throw error;
          }
        }
      }
    };
  }

  public static <M1, T1 extends Request<M1>, M2, T2 extends Request<M2>> Request<M2> compose(
      final T1 firstRequest, final Function<M1, T2> onSuccess) {
    return compose(firstRequest, onSuccess, null);
  }

  public static <M1, T1 extends Request<M1>> Request<M1> retry(
      final T1 request, final ErrorHandler<T1> errorHandler) {

    return new Request<M1>() {
      @Override
      public void dispatch(final UnivapayCallback<M1> callback) {
        request.dispatch(
            new UnivapayCallback<M1>() {
              @Override
              public void getResponse(M1 response) {
                callback.getResponse(response);
              }

              @Override
              public void getFailure(Throwable error) {
                if (shouldHandle(errorHandler, error)) {
                  errorHandler.getHandler().apply(error).dispatch(callback);
                } else {
                  callback.getFailure(error);
                }
              }
            });
      }

      @Override
      public M1 dispatch() throws IOException, UnivapayException, TooManyRequestsException {
        try {
          return request.dispatch();
        } catch (IOException | UnivapayException error) {
          if (shouldHandle(errorHandler, error)) {
            return errorHandler.getHandler().apply(error).dispatch();
          } else {
            throw error;
          }
        }
      }

      @Override
      public M1 dispatch(int maxRetry, Sleeper sleeper)
          throws IOException, UnivapayException, TooManyRequestsException, InterruptedException {
        try {
          return request.dispatch(maxRetry, sleeper);
        } catch (IOException | UnivapayException error) {
          if (shouldHandle(errorHandler, error)) {
            return errorHandler.getHandler().apply(error).dispatch(maxRetry, sleeper);
          } else {
            throw error;
          }
        }
      }
    };
  }
}
