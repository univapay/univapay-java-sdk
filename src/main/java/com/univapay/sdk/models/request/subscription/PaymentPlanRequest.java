package com.univapay.sdk.models.request.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.PaymentPlanType;

public abstract class PaymentPlanRequest {

  @SerializedName("plan_type")
  PaymentPlanType planType;

  public PaymentPlanType getPlanType() {
    return planType;
  }
}
