package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum TransferStatus {
  @SerializedName("created")
  CREATED,
  @SerializedName("approved")
  APPROVED,
  @SerializedName("canceled")
  CANCELED,
  @SerializedName("processing")
  PROCESSING,
  @SerializedName("paid")
  PAID,
  @SerializedName("failed")
  FAILED,
  @SerializedName("blank")
  BLANK,
}
