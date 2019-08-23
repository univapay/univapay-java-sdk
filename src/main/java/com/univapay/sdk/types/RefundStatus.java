package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum RefundStatus {
  @SerializedName("pending")
  PENDING,
  @SerializedName("successful")
  SUCCESSFUL,
  @SerializedName("failed")
  FAILED,
  @SerializedName("error")
  ERROR
}
