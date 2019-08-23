package com.univapay.sdk.models.errors;

public class TooManyRequestsException extends UnivapayException {
  public TooManyRequestsException(int code, String message) {
    super(code, message, null);
  }
}
