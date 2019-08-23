package com.univapay.sdk.utils;

public interface Backoff {
  long next();

  void reset();
}
