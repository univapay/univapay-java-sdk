package com.univapay.sdk.models.response.subscription;

import com.google.gson.annotations.SerializedName;

public class SubscriptionConfiguration {

  @SerializedName("failed_charges_to_cancel")
  private Integer failedChargesToCancel;

  @SerializedName("suspend_on_cancel")
  private Boolean suspendOnCancel;

  public SubscriptionConfiguration(Integer failedChargesToCancel, Boolean suspendOnCancel) {
    this.failedChargesToCancel = failedChargesToCancel;
    this.suspendOnCancel = suspendOnCancel;
  }

  public Integer getFailedChargesToCancel() {
    return failedChargesToCancel;
  }

  public Boolean getSuspendOnCancel() {
    return suspendOnCancel;
  }
}
