package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum TransactionTokenType {
  @SerializedName("subscription")
  SUBSCRIPTION,
  @SerializedName("one_time")
  ONE_TIME,
  @SerializedName("recurring")
  RECURRING
}
