package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum ChargeStatus {
  @SerializedName("pending")
  PENDING,
  @SerializedName("successful")
  SUCCESSFUL,
  @SerializedName("authorized")
  AUTHORIZED,
  @SerializedName("failed")
  FAILED,
  @SerializedName("error")
  ERROR,
  @SerializedName("canceled")
  CANCELED,
  @SerializedName("awaiting")
  AWAITING
}
