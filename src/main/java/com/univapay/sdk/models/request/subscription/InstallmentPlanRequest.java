package com.univapay.sdk.models.request.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.InstallmentPlanType;

public abstract class InstallmentPlanRequest {

  @SerializedName("plan_type")
  InstallmentPlanType planType;

  public InstallmentPlanType getPlanType() {
    return planType;
  }
}
