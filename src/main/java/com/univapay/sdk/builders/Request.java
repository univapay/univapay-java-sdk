package com.univapay.sdk.builders;

import com.univapay.sdk.models.errors.TooManyRequestsException;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.utils.Sleeper;
import com.univapay.sdk.utils.UnivapayCallback;
import java.io.IOException;

public interface Request<E> {

  /**
   * Executes the request, processing asynchronously and calling the corresponding callback
   * (response/failure)
   * @param callback to be executed
   */
  void dispatch(UnivapayCallback<E> callback);

  E dispatch() throws IOException, UnivapayException, TooManyRequestsException;

  E dispatch(int maxRetry, Sleeper sleeper)
      throws IOException, UnivapayException, TooManyRequestsException, InterruptedException;
}
