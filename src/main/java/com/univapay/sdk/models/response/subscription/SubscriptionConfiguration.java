package com.univapay.sdk.models.response.subscription;

import com.google.gson.annotations.SerializedName;

public class SubscriptionConfiguration {

  @SerializedName("enabled")
  private final Boolean enabled;

  @SerializedName("failed_charges_to_cancel")
  private final Integer failedChargesToCancel;

  @SerializedName("suspend_on_cancel")
  private final Boolean suspendOnCancel;

  public SubscriptionConfiguration(
      Boolean enabled, Integer failedChargesToCancel, Boolean suspendOnCancel) {
    this.enabled = enabled;
    this.failedChargesToCancel = failedChargesToCancel;
    this.suspendOnCancel = suspendOnCancel;
  }

  public SubscriptionConfiguration() {
    this.enabled = null;
    this.failedChargesToCancel = null;
    this.suspendOnCancel = null;
  }

  public Integer getFailedChargesToCancel() {
    return failedChargesToCancel;
  }

  public Boolean getSuspendOnCancel() {
    return suspendOnCancel;
  }

  public Boolean getEnabled() {
    return enabled;
  }
}
