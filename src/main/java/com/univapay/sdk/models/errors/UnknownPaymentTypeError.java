package com.univapay.sdk.models.errors;

public class UnknownPaymentTypeError extends Exception {
  private String message;

  @Override
  public String getMessage() {
    return message;
  }

  public UnknownPaymentTypeError(String message) {
    this.message = message;
  }
}
