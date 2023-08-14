package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum PaymentPlanType {
  @SerializedName("revolving")
  REVOLVING,
  @SerializedName("fixed_cycles")
  FIXED_CYCLES,
  @SerializedName("fixed_cycle_amount")
  FIXED_CYCLE_AMOUNT
}
