package com.univapay.sdk.models.request.subscription;

import com.univapay.sdk.types.PaymentPlanType;

public class RevolvingInstallmentsPlan extends PaymentPlanRequest {

  public RevolvingInstallmentsPlan() {
    this.planType = PaymentPlanType.REVOLVING;
  }
}
