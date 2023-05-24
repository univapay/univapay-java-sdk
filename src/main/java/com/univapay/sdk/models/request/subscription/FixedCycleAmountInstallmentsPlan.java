package com.univapay.sdk.models.request.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.PaymentPlanType;
import java.math.BigInteger;

public class FixedCycleAmountInstallmentsPlan extends PaymentPlanRequest {

  @SerializedName("fixed_cycle_amount")
  private BigInteger fixedCycleAmount;

  public BigInteger getFixedCycleAmount() {
    return fixedCycleAmount;
  }

  public FixedCycleAmountInstallmentsPlan(BigInteger fixedCycleAmount) {
    this.fixedCycleAmount = fixedCycleAmount;
    this.planType = PaymentPlanType.FIXED_CYCLE_AMOUNT;
  }
}
