package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;

public class ExternalCardConfiguration {

  @SerializedName("enabled")
  private final Boolean enabled;

  public ExternalCardConfiguration(Boolean enabled) {
    this.enabled = enabled;
  }

  public Boolean getEnabled() {
    return enabled;
  }
}
