package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;

public class KonbiniConfiguration {

  @SerializedName("enabled")
  private Boolean enabled;

  public KonbiniConfiguration(Boolean enabled) {
    this.enabled = enabled;
  }

  public Boolean getEnabled() {
    return enabled;
  }
}
