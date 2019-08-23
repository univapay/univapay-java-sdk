package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum ProcessingMode {
  @SerializedName("test")
  TEST,
  @SerializedName("live")
  LIVE,
  @SerializedName("live_test")
  LIVE_TEST
}
