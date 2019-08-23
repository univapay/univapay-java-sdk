package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum TransferPeriod {
  @SerializedName("weekly")
  WEEKLY,
  @SerializedName("biweekly")
  BIWEEKLY,
  @SerializedName("monthly")
  MONTHLY,
  @SerializedName("semimonthly")
  SEMIMONTHLY
}
