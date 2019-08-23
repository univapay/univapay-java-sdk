package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum SubscriptionStatus {
  @SerializedName("unverified")
  UNVERIFIED,
  @SerializedName("unconfirmed")
  UNCONFIRMED,
  @SerializedName("current")
  CURRENT,
  @SerializedName("unpaid")
  UNPAID,
  @SerializedName("authorized")
  AUTHORIZED,
  @SerializedName("suspended")
  SUSPENDED,
  @SerializedName("canceled")
  CANCELED,
  @SerializedName("completed")
  COMPLETED
}
