package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum RefundReason {
  @SerializedName("duplicate")
  DUPLICATE,
  @SerializedName("fraud")
  FRAUD,
  @SerializedName("customer_request")
  CUSTOMER_REQUEST,
  @SerializedName("chargeback")
  CHARGEBACK,
  @SerializedName("system_failure")
  SYSTEM_FAILURE
}
