package com.univapay.sdk.models.response.configuration;

import com.google.gson.annotations.SerializedName;

public class CheckoutConfiguration {

  @SerializedName("ec_email")
  private final EcMailConfiguration ecMailConfiguration;

  @SerializedName("ec_products")
  private final EcProductsConfiguration ecProductsConfiguration;

  public CheckoutConfiguration(
      EcMailConfiguration ecMailConfiguration, EcProductsConfiguration ecProductsConfiguration) {
    this.ecMailConfiguration = ecMailConfiguration;
    this.ecProductsConfiguration = ecProductsConfiguration;
  }

  public CheckoutConfiguration() {
    this.ecMailConfiguration = new EcMailConfiguration();
    this.ecProductsConfiguration = new EcProductsConfiguration();
  }

  public EcMailConfiguration getEcMail() {
    return ecMailConfiguration;
  }

  public EcProductsConfiguration getEcProducts() {
    return ecProductsConfiguration;
  }

  public static class EcMailConfiguration {

    @SerializedName("enabled")
    private final Boolean enabled;

    public EcMailConfiguration() {
      enabled = null;
    }

    public EcMailConfiguration(Boolean enabled) {
      this.enabled = enabled;
    }

    public Boolean getEnabled() {
      return enabled;
    }
  }

  public static class EcProductsConfiguration {

    @SerializedName("enabled")
    private final Boolean enabled;

    public EcProductsConfiguration() {
      enabled = null;
    }

    public EcProductsConfiguration(Boolean enabled) {
      this.enabled = enabled;
    }

    public Boolean getEnabled() {
      return enabled;
    }
  }
}
