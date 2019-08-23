package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;

public class PaidyConfiguration {
  @SerializedName("enabled")
  private Boolean enabled;

  public PaidyConfiguration(Boolean enabled) {
    this.enabled = enabled;
  }

  public Boolean getEnabled() {
    return enabled;
  }
}
