package com.univapay.sdk.utils;

import static it.ozimov.cirneco.hamcrest.java7.J7Matchers.betweenInclusive;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ExponentialBackoffTest {
  @Test
  public void shouldCalcNextIntervalExponentially() {

    ExponentialBackoff backoff = new ExponentialBackoff(1_000, 20_000, 1.5, 0.5);

    Assert.assertEquals(1000, backoff.currentIntervalMillis, 1);
    backoff.calcNextInterval();
    Assert.assertEquals(1500, backoff.currentIntervalMillis, 1);
    backoff.calcNextInterval();
    Assert.assertEquals(2250, backoff.currentIntervalMillis, 1);
    backoff.calcNextInterval();
    Assert.assertEquals(3375, backoff.currentIntervalMillis, 1);
    backoff.calcNextInterval();
    Assert.assertEquals(5062, backoff.currentIntervalMillis, 1);
    backoff.calcNextInterval();
    Assert.assertEquals(7593, backoff.currentIntervalMillis, 1);

    backoff = new ExponentialBackoff(0, 30_000, 2, 0.5);

    Assert.assertEquals(0, backoff.currentIntervalMillis, 0);
    backoff.calcNextInterval();
    Assert.assertEquals(2000, backoff.currentIntervalMillis, 1);
    backoff.calcNextInterval();
    Assert.assertEquals(4000, backoff.currentIntervalMillis, 1);
    backoff.calcNextInterval();
    Assert.assertEquals(8000, backoff.currentIntervalMillis, 1);
    backoff.calcNextInterval();
    Assert.assertEquals(16000, backoff.currentIntervalMillis, 1);
    backoff.calcNextInterval();
    Assert.assertEquals(30000, backoff.currentIntervalMillis, 1);
    backoff.calcNextInterval();
    Assert.assertEquals(30000, backoff.currentIntervalMillis, 1);
  }

  @Test
  public void shouldIncreaseBackoffUpToMaxInterval() {

    ExponentialBackoff backoff = new ExponentialBackoff(1_000, 20_000, 2, 0.5);

    Assert.assertEquals(1000, backoff.currentIntervalMillis, 1);
    backoff.calcNextInterval();
    Assert.assertEquals(2000, backoff.currentIntervalMillis, 1);
    backoff.calcNextInterval();
    Assert.assertEquals(4000, backoff.currentIntervalMillis, 1);
    backoff.calcNextInterval();
    Assert.assertEquals(8000, backoff.currentIntervalMillis, 1);
    backoff.calcNextInterval();
    Assert.assertEquals(16000, backoff.currentIntervalMillis, 1);
    backoff.calcNextInterval();
    Assert.assertEquals(20000, backoff.currentIntervalMillis, 1);
    backoff.calcNextInterval();
    Assert.assertEquals(20000, backoff.currentIntervalMillis, 1);
  }

  @Test
  public void shouldMakeUniformlyDistributedRandomeBackoff() {

    List<Long> vals = new ArrayList<>();
    long sum = 0;
    for (int i = 0; i < 1000; i++) {
      ExponentialBackoff backoff = new ExponentialBackoff(100, 20_000, 1.5, 0.5);

      long val = backoff.next();
      assertThat(val, betweenInclusive(50L, 150L));
      vals.add(val);
      sum += val;
    }
    double avg = sum / vals.size();

    double vars = 0;
    for (Long val : vals) {
      vars += (val - avg) * (val - avg);
    }
    double stddev = Math.sqrt(vars / vals.size());

    double expectedStdDev = (150 - 50) / (2 * Math.sqrt(3));
    assertEquals(expectedStdDev, stddev, 2);
  }

  @Test
  public void shouldRandomizeBackoffInFactorRange() {

    for (int i = 0; i < 10; i++) {
      ExponentialBackoff backoff = new ExponentialBackoff(1_000, 20_000, 1.5, 0.5);

      Assert.assertThat(backoff.next(), betweenInclusive((long) (1000 * 0.5), (long) (1000 * 1.5)));
      Assert.assertThat(backoff.next(), betweenInclusive((long) (1500 * 0.5), (long) (1500 * 1.5)));
      Assert.assertThat(backoff.next(), betweenInclusive((long) (2250 * 0.5), (long) (2250 * 1.5)));
      Assert.assertThat(backoff.next(), betweenInclusive((long) (3375 * 0.5), (long) (3375 * 1.5)));
      Assert.assertThat(backoff.next(), betweenInclusive((long) (5062 * 0.5), (long) (5062 * 1.5)));
    }
  }

  @Test
  public void shouldAlwaysGeneratePositiveNumber() {

    for (int i = 0; i < 10; i++) {
      ExponentialBackoff backoff = new ExponentialBackoff(1_000, 30_000, 2, 1.5);
      Assert.assertThat(backoff.next(), greaterThanOrEqualTo((long) 0));
      Assert.assertThat(backoff.next(), greaterThanOrEqualTo((long) 0));
      Assert.assertThat(backoff.next(), greaterThanOrEqualTo((long) 0));
      Assert.assertThat(backoff.next(), greaterThanOrEqualTo((long) 0));
      Assert.assertThat(backoff.next(), greaterThanOrEqualTo((long) 0));
    }
  }
}
