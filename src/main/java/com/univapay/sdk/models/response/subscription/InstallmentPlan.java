package com.univapay.sdk.models.response.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.InstallmentPlanType;
import java.math.BigInteger;

public class InstallmentPlan {
  @SerializedName("plan_type")
  private InstallmentPlanType planType;

  @SerializedName("fixed_cycle_amount")
  private BigInteger fixedCycleAmount;

  @SerializedName("fixed_cycles")
  private Integer fixedCycles;

  public InstallmentPlanType getPlanType() {
    return planType;
  }

  public BigInteger getFixedCycleAmount() {
    return fixedCycleAmount;
  }

  public Integer getFixedCycles() {
    return fixedCycles;
  }
}
