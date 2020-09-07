package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;

public class OnlineConfiguration {

  @SerializedName("enabled")
  private final Boolean enabled;

  public OnlineConfiguration(Boolean enabled) {
    this.enabled = enabled;
  }

  public Boolean getEnabled() {
    return enabled;
  }
}
