package com.univapay.sdk.models.request.store;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.request.configuration.ConfigurationRequest;

@SuppressWarnings("FieldCanBeLocal")
public class StoreCreateData {

  @SerializedName("name")
  private String name;

  @SerializedName("configuration")
  private ConfigurationRequest configuration;

  public StoreCreateData(String name, ConfigurationRequest configuration) {
    this.name = name;
    this.configuration = configuration;
  }
}
