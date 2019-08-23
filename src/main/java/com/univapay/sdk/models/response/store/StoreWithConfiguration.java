package com.univapay.sdk.models.response.store;

import com.google.gson.annotations.SerializedName;

public class StoreWithConfiguration extends Store {
  @SerializedName("configuration")
  private StoreConfiguration configuration;

  public StoreConfiguration getConfiguration() {
    return configuration;
  }
}
