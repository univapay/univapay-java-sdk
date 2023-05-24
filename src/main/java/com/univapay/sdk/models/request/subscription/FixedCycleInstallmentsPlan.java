package com.univapay.sdk.models.request.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.PaymentPlanType;

public class FixedCycleInstallmentsPlan extends PaymentPlanRequest {

  @SerializedName("fixed_cycles")
  private Integer fixedCycles;

  public Integer getFixedCycles() {
    return fixedCycles;
  }

  public FixedCycleInstallmentsPlan(Integer fixedCycles) {
    this.fixedCycles = fixedCycles;
    this.planType = PaymentPlanType.FIXED_CYCLES;
  }
}
