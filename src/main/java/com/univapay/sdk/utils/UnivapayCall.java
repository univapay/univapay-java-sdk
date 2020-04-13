package com.univapay.sdk.utils;

import java.io.IOException;
import okhttp3.Headers;
import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnivapayCall<T> implements Call<T> {

  private Call<T> call;
  private boolean callbackFired;
  private okhttp3.Headers headers;
  private int code;
  private String message;

  public UnivapayCall(Call<T> call) {
    this.call = call;
  }

  /**
   * Synchronously send the request and return its response.
   *
   * @throws IOException if a problem occurred talking to the server.
   * @throws RuntimeException (and subclasses) if an unexpected error occurs creating the request or
   *     decoding the response.
   */
  @Override
  public Response<T> execute() throws IOException {
    return call.execute();
  }

  /**
   * Asynchronously send the request and notify {@code callback} of its response or if an error
   * occurred talking to the server, creating the request, or processing the response.
   *
   * @param callback
   */
  @Override
  public void enqueue(Callback<T> callback) {
    call.enqueue(callback);
  }

  /**
   * Returns true if this call has been either {@linkplain #execute() executed} or {@linkplain
   * #enqueue(Callback) enqueued}. It is an error to execute or enqueue a call more than once.
   */
  @Override
  public boolean isExecuted() {
    return call.isExecuted();
  }

  /**
   * Cancel this call. An attempt will be made to cancel in-flight calls, and if the call has not
   * yet been executed it never will be.
   */
  @Override
  public void cancel() {
    call.cancel();
  }

  /** True if {@link #cancel()} was called. */
  @Override
  public boolean isCanceled() {
    return call.isCanceled();
  }

  /**
   * Create a new, identical call to this one which can be enqueued or executed even if this call
   * has already been.
   */
  @Override
  public Call<T> clone() {
    // TODO: Implement the clone
    throw new RuntimeException("To be implemented");
  }

  /** The original HTTP request. */
  @Override
  public Request request() {
    return call.request();
  }

  @Override
  public Timeout timeout() {
    return call.timeout();
  }

  public boolean isCallbackFired() {
    return callbackFired;
  }

  public Headers getHeaders() {
    return headers;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public void setResponseInfo(Response response) {
    this.callbackFired = true;
    this.message = response.message();
    this.code = response.code();
    this.headers = response.headers();
  }

  public void setCallbackFired(boolean callbackFired) {
    this.callbackFired = callbackFired;
  }
}
