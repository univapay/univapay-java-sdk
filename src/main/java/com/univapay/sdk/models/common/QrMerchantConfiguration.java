package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;

public class QrMerchantConfiguration {
  @SerializedName("enabled")
  private Boolean enabled;

  public QrMerchantConfiguration(Boolean enabled) {
    this.enabled = enabled;
  }

  public Boolean getEnabled() {
    return enabled;
  }
}
