package com.univapay.sdk.utils;

import java.util.Random;

public class ExponentialBackoff implements Backoff {

  final long initialIntervalMillis;
  final long maxIntervalMillis;
  final double multiplier;
  final double randomizationFactor;
  final Random random = new Random();

  double currentIntervalMillis;

  public ExponentialBackoff(
      long initialIntervalMillis,
      long maxIntervalMillis,
      double multiplier,
      double randomizationFactor) {
    if (multiplier < 1) {
      throw new IllegalArgumentException("multiplier must be >= 1");
    }
    this.maxIntervalMillis = maxIntervalMillis;
    this.multiplier = multiplier;
    this.randomizationFactor = randomizationFactor;
    this.initialIntervalMillis = initialIntervalMillis;

    currentIntervalMillis = initialIntervalMillis;
  }

  @Override
  public long next() {

    double jitter = (1 + random.nextDouble() * randomizationFactor * 2) - randomizationFactor;
    long returnValue = (long) (currentIntervalMillis * Math.abs(jitter));

    calcNextInterval();

    return returnValue;
  }

  @Override
  public void reset() {
    currentIntervalMillis = initialIntervalMillis;
  }

  void calcNextInterval() {
    if (currentIntervalMillis < 1) {
      currentIntervalMillis = 1000;
    }

    if (currentIntervalMillis * multiplier >= maxIntervalMillis) {
      currentIntervalMillis = maxIntervalMillis;
    } else {
      currentIntervalMillis *= multiplier;
    }
  }
}
