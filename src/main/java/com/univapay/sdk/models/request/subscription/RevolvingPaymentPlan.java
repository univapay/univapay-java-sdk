package com.univapay.sdk.models.request.subscription;

import com.univapay.sdk.types.PaymentPlanType;

public class RevolvingPaymentPlan extends PaymentPlanRequest {

  public RevolvingPaymentPlan() {
    this.planType = PaymentPlanType.REVOLVING;
  }
}
