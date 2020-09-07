package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;

public class DescriptorProvidedConfiguration {

  @SerializedName("name")
  private final String name;

  @SerializedName("phone_number")
  private final String phoneNumber;

  public DescriptorProvidedConfiguration(String name, String phoneNumber) {
    this.name = name;
    this.phoneNumber = phoneNumber;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getName() {
    return name;
  }
}
