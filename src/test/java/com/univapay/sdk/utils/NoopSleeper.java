package com.univapay.sdk.utils;

public class NoopSleeper implements Sleeper {

  @Override
  public void reset() {
    // noop
  }

  @Override
  public void sleep() throws InterruptedException {
    // noop
  }
}
