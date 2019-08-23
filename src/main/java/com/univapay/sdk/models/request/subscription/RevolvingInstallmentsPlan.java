package com.univapay.sdk.models.request.subscription;

import com.univapay.sdk.types.InstallmentPlanType;

public class RevolvingInstallmentsPlan extends InstallmentPlanRequest {

  public RevolvingInstallmentsPlan() {
    this.planType = InstallmentPlanType.REVOLVING;
  }
}
