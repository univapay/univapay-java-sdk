package com.univapay.sdk.utils;

public interface UnivapayCallback<T> {
  void getResponse(T response);

  void getFailure(Throwable error);
}
