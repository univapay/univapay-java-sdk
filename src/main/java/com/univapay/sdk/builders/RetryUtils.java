package com.univapay.sdk.builders;

import com.univapay.sdk.models.errors.DetailedError;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.utils.functions.ErrorHandler;
import com.univapay.sdk.utils.functions.Function;
import com.univapay.sdk.utils.functions.Predicate;
import com.univapay.sdk.utils.functions.UnivapayFunctions;

public abstract class RetryUtils {

  public static <M, B extends RequestBuilder<M, Request<M>>> Request<M> retryIgnoringDescriptor(
      Request<M> originalRequest, final DescriptorRetry<B, M> builder) {
    return UnivapayFunctions.retry(
        originalRequest,
        new ErrorHandler<>(
                t -> t instanceof UnivapayException
                    && ((UnivapayException) t)
                        .getBody()
                        .getErrors()
                        .contains(new DetailedError("descriptor", "NOT_SUPPORTED_BY_PROCESSOR")),
            new Function<Throwable, Request<M>>() {
              @Override
              public Request<M> apply(Throwable arg) {
                return builder.withDescriptor(null).build();
              }
            }));
  }
}
