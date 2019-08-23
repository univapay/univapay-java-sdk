package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum TransactionType {
  @SerializedName("refund")
  REFUND,
  @SerializedName("charge")
  CHARGE
}
