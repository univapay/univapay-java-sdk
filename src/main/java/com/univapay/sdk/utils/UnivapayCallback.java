package com.univapay.sdk.utils;

public interface UnivapayCallback<T> {
  void getResponse(T response);

  void getFailure(Throwable error);

  /**
   * In case there was a cancel event, it is signalled downstream, unfortunately, there is no
   * information whether this request was already in-flight or not, therefore treat this request as
   * might have been successful, the cancel event are manually invoked
   */
  default void onCancel() {}
}
