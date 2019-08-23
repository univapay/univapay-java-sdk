package com.univapay.sdk.utils.streams;

// The window size class exists because it is too easy to mix up the timeSpan and number of
// on WindowOptions.
public class WindowSize {
  public final long size;

  public WindowSize(long size) {
    this.size = size;
  }
}
