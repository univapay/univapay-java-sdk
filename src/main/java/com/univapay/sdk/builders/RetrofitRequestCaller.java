package com.univapay.sdk.builders;

import com.google.gson.JsonParseException;
import com.univapay.sdk.constants.UnivapayConstants;
import com.univapay.sdk.models.errors.TooManyRequestsException;
import com.univapay.sdk.models.errors.UnivapayErrorBody;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.types.IdempotencyStatus;
import com.univapay.sdk.utils.Sleeper;
import com.univapay.sdk.utils.UnivapayCallback;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Optional;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.*;

public class RetrofitRequestCaller<E extends UnivapayResponse> implements Request<E> {

  private final Call<E> apiCall;
  private final Converter<ResponseBody, UnivapayErrorBody> errorConverter;

  public RetrofitRequestCaller(Retrofit retrofit, Call<E> apiCall) {
    this.errorConverter =
        retrofit.responseBodyConverter(UnivapayErrorBody.class, new Annotation[0]);
    this.apiCall = apiCall;
  }

  public RetrofitRequestCaller(
      Converter<ResponseBody, UnivapayErrorBody> errorConverter, Call<E> apiCall) {
    this.errorConverter = errorConverter;
    this.apiCall = apiCall;
  }

  @Override
  public Cancelable dispatch(final UnivapayCallback<E> callback) {

    apiCall.enqueue(
        new Callback<E>() {
          @Override
          public void onResponse(@NotNull Call<E> call, @NotNull Response<E> response) {

            E model;
            try {
              model = extractModel(response);
            } catch (Throwable e) {
              callback.getFailure(e);
              return;
            }

            callback.getResponse(model);
          }

          @Override
          public void onFailure(@NotNull Call call, @NotNull Throwable t) {

            // OkHttp, on cancel, throws this java.io.IOException: Canceled
            if (t instanceof IOException && "Canceled".equals(t.getMessage())) {
              // Make sure we make this more transparent for the consumer
              callback.onCancel();
            } else {
              callback.getFailure(t);
            }
          }
        });

    // return method
    return apiCall::cancel;
  }

  @Override
  public E dispatch() throws IOException, UnivapayException, TooManyRequestsException {
    try {
      return dispatch(1, null);
    } catch (InterruptedException e) {
      throw new AssertionError("Thread never sleep when maxRetry is 1");
    }
  }

  @Override
  public E dispatch(int maxRetry, Sleeper sleeper)
      throws IOException, UnivapayException, TooManyRequestsException, InterruptedException {

    Exception lastException = null;
    Call<E> call = apiCall;

    for (int retry = 0; retry < maxRetry; retry++) {
      try {
        return extractModel(call.execute());
      } catch (IOException | TooManyRequestsException e) {
        lastException = e;
        if (retry + 1 < maxRetry) {
          sleeper.sleep();
          call = call.clone();
        }
      }
    }

    assert lastException != null;
    if (lastException instanceof IOException) {
      throw (IOException) lastException;
    } else {
      throw (TooManyRequestsException) lastException;
    }
  }

  private E extractModel(Response<E> response)
      throws UnivapayException, IOException, TooManyRequestsException {
    if (response.isSuccessful()) {
      E model = response.body();
      String unparsedIdempotencyStatus =
          response.headers().get(UnivapayConstants.idempotencyStatusHeaderName);

      if (model != null) {
        if (unparsedIdempotencyStatus == null) {
          model.setIdempotencyStatus(IdempotencyStatus.NO_STATUS);
        } else {
          model.setIdempotencyStatus(
              IdempotencyStatus.valueOf(unparsedIdempotencyStatus.toUpperCase()));
        }
      }
      return model;
    } else if (response.code() == 429 /* Too Many Requests */) {
      throw new TooManyRequestsException(response.code(), response.message());
    } else {
      UnivapayErrorBody body = null;
      ResponseBody errorBody = response.errorBody();
      MediaType contentType = errorBody.contentType();

      if (contentType != null && contentType.subtype().equals("json")) {

        body =
            Optional.of(errorBody)
                .flatMap(
                    value -> {
                      try { // {code:null, status:null, details:null}
                        return Optional.ofNullable(this.errorConverter.convert(errorBody));
                      } catch (IOException | JsonParseException e) {
                        return Optional.empty();
                      }
                    })
                .filter(
                    value ->
                        value.getCode() != null
                            || value.getStatus() != null
                            || value.getErrors() != null)
                .orElse(null);
      }
      throw new UnivapayException(response.code(), response.message(), body);
    }
  }
}
