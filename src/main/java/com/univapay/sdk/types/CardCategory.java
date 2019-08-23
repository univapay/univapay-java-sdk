package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum CardCategory {
  @SerializedName("classic")
  CLASSIC,
  @SerializedName("corporate")
  CORPORATE,
  @SerializedName("prepaid")
  PREPAID
}
