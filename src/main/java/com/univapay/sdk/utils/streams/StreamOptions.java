package com.univapay.sdk.utils.streams;

import io.reactivex.rxjava3.annotations.NonNull;
import java.util.concurrent.TimeUnit;

public class StreamOptions {

  // I am hoping this class can be extended with some nice quality of life additions such as:
  // * Number of retries on a failed step (with a retry library)
  // * A back off policy (exponential, linear, fibonacci, etc).
  // * Reject/Accept results
  // * Number of executors to run on

  private static final WindowOptions DEFAULT_WINDOW_OPTIONS =
      new WindowOptions(new WindowSize(5), 100, TimeUnit.MILLISECONDS);

  private WindowOptions windowOptions;

  private Integer parallelism;

  private StreamOptions() {}

  public static StreamOptions newInstance() {
    return new StreamOptions();
  }

  public WindowOptions getWindowOptions() {
    return windowOptions == null ? DEFAULT_WINDOW_OPTIONS : windowOptions;
  }

  public StreamOptions setWindowOptions(@NonNull WindowOptions windowOptions) {
    this.windowOptions = windowOptions;
    return this;
  }

  public int getParallelism() {
    if (parallelism == null) {
      return Runtime.getRuntime().availableProcessors();
    }
    return parallelism;
  }

  public StreamOptions setParallelism(Integer parallelism) {
    this.parallelism = parallelism;
    return this;
  }
}
