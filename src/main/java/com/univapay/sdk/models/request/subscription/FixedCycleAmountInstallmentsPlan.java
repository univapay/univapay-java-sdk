package com.univapay.sdk.models.request.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.InstallmentPlanType;
import java.math.BigInteger;

public class FixedCycleAmountInstallmentsPlan extends InstallmentPlanRequest {

  @SerializedName("fixed_cycle_amount")
  private BigInteger fixedCycleAmount;

  public BigInteger getFixedCycleAmount() {
    return fixedCycleAmount;
  }

  public FixedCycleAmountInstallmentsPlan(BigInteger fixedCycleAmount) {
    this.fixedCycleAmount = fixedCycleAmount;
    this.planType = InstallmentPlanType.FIXED_CYCLE_AMOUNT;
  }
}
