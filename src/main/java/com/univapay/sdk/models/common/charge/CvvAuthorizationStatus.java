package com.univapay.sdk.models.common.charge;

import com.google.gson.annotations.SerializedName;

public enum CvvAuthorizationStatus {
  @SerializedName("error")
  ERROR,
  @SerializedName("failed")
  FAILED,
  @SerializedName("pending")
  PENDING,
  @SerializedName("current")
  CURRENT,
  @SerializedName("inactive")
  INACTIVE;
}
