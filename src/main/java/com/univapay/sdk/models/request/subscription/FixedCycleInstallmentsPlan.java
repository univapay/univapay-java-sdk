package com.univapay.sdk.models.request.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.InstallmentPlanType;

public class FixedCycleInstallmentsPlan extends InstallmentPlanRequest {

  @SerializedName("fixed_cycles")
  private Integer fixedCycles;

  public Integer getFixedCycles() {
    return fixedCycles;
  }

  public FixedCycleInstallmentsPlan(Integer fixedCycles) {
    this.fixedCycles = fixedCycles;
    this.planType = InstallmentPlanType.FIXED_CYCLES;
  }
}
