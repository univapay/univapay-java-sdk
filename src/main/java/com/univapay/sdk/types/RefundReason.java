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
  @SerializedName("chargeback_fee_exempt")
  CHARGEBACK_FEE_EXEMPT,
  @SerializedName("chargeback_reverse")
  CHARGEBACK_REVERSE,
  @SerializedName("system_failure")
  SYSTEM_FAILURE
}
