package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum LedgerOrigin {
  @SerializedName("cancel")
  CANCEL,
  @SerializedName("charge")
  CHARGE,
  @SerializedName("refund")
  REFUND,
  @SerializedName("manual")
  MANUAL
}
