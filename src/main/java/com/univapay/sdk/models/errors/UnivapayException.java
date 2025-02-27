package com.univapay.sdk.models.errors;

import lombok.Getter;

/** Exceptions that result from errors returned by the Univapay API. */
@Getter
public class UnivapayException extends Exception {

  final int httpStatusCode;
  final String httpStatusMessage;
  final UnivapayErrorBody body;

  public UnivapayException(int code, String message, UnivapayErrorBody body) {
    this.httpStatusCode = code;
    this.httpStatusMessage = message;
    this.body = body;
  }

  @Override
  public String getMessage() {
    return "{HTTPStatus: "
        + httpStatusCode
        + " "
        + httpStatusMessage
        + ", "
        + "UnivapayError: "
        + (body != null ? body.toString() : "null")
        + "}";
  }

  @Override
  public String toString() {
    return getClass().getName() + " " + getMessage();
  }
}
