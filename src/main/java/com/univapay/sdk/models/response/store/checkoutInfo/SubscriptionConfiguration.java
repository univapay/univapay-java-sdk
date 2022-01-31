package com.univapay.sdk.models.response.store.checkoutInfo;

import com.google.gson.annotations.SerializedName;

public class SubscriptionConfiguration {

  @SerializedName("enabled")
  private Boolean enabled;

  public Boolean getEnabled() {
    return enabled;
  }

  public SubscriptionConfiguration(Boolean enabled) {
    this.enabled = enabled;
  }
}
