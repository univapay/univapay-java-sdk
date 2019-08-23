package com.univapay.sdk.models.errors;

/** Exceptions thrown by the Univapay SDK not related to responses from the API. */
public class SDKException extends Exception {

  public SDKException() {}

  public SDKException(String message) {
    super(message);
  }

  public SDKException(String message, Throwable cause) {
    super(message, cause);
  }

  public SDKException(Throwable cause) {
    super(cause);
  }

  public SDKException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
