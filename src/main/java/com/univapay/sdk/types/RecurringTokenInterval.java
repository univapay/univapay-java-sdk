package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum RecurringTokenInterval {
  @SerializedName("daily")
  DAILY,
  @SerializedName("weekly")
  WEEKLY,
  @SerializedName("monthly")
  MONTHLY,
  @SerializedName("annually")
  ANNUALLY
}
