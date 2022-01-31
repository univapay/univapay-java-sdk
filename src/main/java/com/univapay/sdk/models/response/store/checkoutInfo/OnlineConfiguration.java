package com.univapay.sdk.models.response.store.checkoutInfo;

import com.google.gson.annotations.SerializedName;

public class OnlineConfiguration {

  @SerializedName("enabled")
  private Boolean enabled;

  public Boolean getEnabled() {
    return enabled;
  }

  public OnlineConfiguration(Boolean enabled) {
    this.enabled = enabled;
  }
}
