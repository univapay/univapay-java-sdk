package com.univapay.sdk.models.response.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.PaymentPlanType;
import java.math.BigInteger;

public class PaymentPlan {
  @SerializedName("plan_type")
  private PaymentPlanType planType;

  @SerializedName("fixed_cycle_amount")
  private BigInteger fixedCycleAmount;

  @SerializedName("fixed_cycles")
  private Integer fixedCycles;

  public PaymentPlanType getPlanType() {
    return planType;
  }

  public BigInteger getFixedCycleAmount() {
    return fixedCycleAmount;
  }

  public Integer getFixedCycles() {
    return fixedCycles;
  }
}
