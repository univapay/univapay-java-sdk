package com.univapay.sdk.models.response.merchant;

import com.google.gson.annotations.SerializedName;

public class MerchantWithConfiguration extends Merchant {
  @SerializedName("configuration")
  MerchantConfiguration configuration;

  public MerchantConfiguration getConfiguration() {
    return configuration;
  }
}
