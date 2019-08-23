package com.univapay.sdk.models.errors;

/** Exceptions that result from errors returned by the Univapay API. */
public class UnivapayException extends Exception {

  final int httpStatusCode;
  final String httpStatusMessage;
  final UnivapayErrorBody body;

  public UnivapayException(int code, String message, UnivapayErrorBody body) {
    this.httpStatusCode = code;
    this.httpStatusMessage = message;
    this.body = body;
  }

  public int getHttpStatusCode() {
    return httpStatusCode;
  }

  public String getHttpStatusMessage() {
    return httpStatusMessage;
  }

  public UnivapayErrorBody getBody() {
    return body;
  }

  @Override
  public String toString() {
    return getClass().getName()
        + "{ "
        + "HTTPStatus: "
        + httpStatusCode
        + " "
        + httpStatusMessage
        + ", "
        + "UnivapayError: "
        + (body != null ? body.toString() : "null")
        + "}";
  }
}
