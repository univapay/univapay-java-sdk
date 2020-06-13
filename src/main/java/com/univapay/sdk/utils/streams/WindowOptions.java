package com.univapay.sdk.utils.streams;

import io.reactivex.rxjava3.annotations.NonNull;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;

public class WindowOptions {

  private final WindowSize windowSize;
  private final long length;
  private final TimeUnit timeUnit;

  public WindowOptions(@Nonnull WindowSize windowSize, long length, @NonNull TimeUnit timeUnit) {
    this.windowSize = windowSize;
    this.length = length;
    this.timeUnit = timeUnit;
  }

  public long getLength() {
    return length;
  }

  public TimeUnit getTimeUnit() {
    return timeUnit;
  }

  public long getWindowSize() {
    return windowSize.size;
  }
}
