package com.univapay.sdk.models.request.subscription;

import com.google.gson.annotations.SerializedName;

public class ScheduledPaymentPatchData {

  @SerializedName("is_paid")
  private Boolean isPaid;

  public ScheduledPaymentPatchData(Boolean isPaid) {
    this.isPaid = isPaid;
  }

  public Boolean getPaid() {
    return isPaid;
  }
}
