package com.univapay.sdk.models.common.threeDs;

import com.google.gson.annotations.SerializedName;

public enum TransactionToken3dsStatus {
  @SerializedName("pending")
  PENDING,
  @SerializedName("awaiting")
  AWAITING,
  @SerializedName("successful")
  SUCCESSFUL,
  @SerializedName("failed")
  FAILED,
  @SerializedName("error")
  ERROR
}
