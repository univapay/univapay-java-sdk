package com.univapay.sdk.utils;

public interface Sleeper {
  void reset();

  void sleep() throws InterruptedException;
}
