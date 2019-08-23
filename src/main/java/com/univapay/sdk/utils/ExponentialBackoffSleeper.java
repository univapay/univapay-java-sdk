package com.univapay.sdk.utils;

public class ExponentialBackoffSleeper implements Sleeper {

  private final Backoff backoff;

  public ExponentialBackoffSleeper(
      long initialIntervalMillis,
      long maxIntervalMillis,
      double multiplier,
      double randomizationFactor) {
    this.backoff =
        new ExponentialBackoff(
            initialIntervalMillis, maxIntervalMillis, multiplier, randomizationFactor);
  }

  @Override
  public void reset() {
    backoff.reset();
  }

  @Override
  public void sleep() throws InterruptedException {
    Thread.sleep(backoff.next());
  }
}
