package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum SubscriptionPeriod {
  @SerializedName("daily")
  DAILY,
  @SerializedName("weekly")
  WEEKLY,
  @SerializedName("biweekly")
  BIWEEKLY,
  @SerializedName("monthly")
  MONTHLY,
  @SerializedName("quarterly")
  QUARTERLY,
  @SerializedName("semiannually")
  SEMIANNUALLY,
  @SerializedName("annually")
  ANNUALLY
}
