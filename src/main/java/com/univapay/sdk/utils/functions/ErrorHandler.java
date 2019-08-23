package com.univapay.sdk.utils.functions;

public class ErrorHandler<T> {

  private Predicate shouldHandleError;
  private Function<Throwable, T> handler;

  public ErrorHandler(Predicate shouldHandleError, Function<Throwable, T> handleError) {
    this.shouldHandleError = shouldHandleError;
    this.handler = handleError;
  }

  public ErrorHandler(Function<Throwable, T> handler) {
    this.handler = handler;
    this.shouldHandleError =
        new Predicate() {
          @Override
          public boolean test(Throwable t) {
            return true;
          }
        };
  }

  public Predicate getShouldHandleError() {
    return shouldHandleError;
  }

  public Function<Throwable, T> getHandler() {
    return handler;
  }
}
