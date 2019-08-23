package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;

public class PaidyShippingAddress {

  @SerializedName("line1")
  private String line1;

  @SerializedName("line2")
  private String line2;

  @SerializedName("city")
  private String city;

  @SerializedName("state")
  private String state;

  @SerializedName("zip")
  private String postalCode;

  public PaidyShippingAddress(String postalCode) {
    this.postalCode = postalCode;
  }

  public PaidyShippingAddress(
      String postalCode, String state, String city, String line1, String line2) {
    this.state = state;
    this.city = city;
    this.line1 = line1;
    this.line2 = line2;
    this.postalCode = postalCode;
  }

  public PaidyShippingAddress addState(String state) {
    this.state = state;
    return this;
  }

  public PaidyShippingAddress addCity(String city) {
    this.city = city;
    return this;
  }

  public PaidyShippingAddress addAddressDetails(String line1) {
    this.line1 = line1;
    return this;
  }

  public PaidyShippingAddress addAddressDetails(String line1, String line2) {
    this.line1 = line1;
    this.line2 = line2;
    return this;
  }

  public String getLine1() {
    return line1;
  }

  public String getLine2() {
    return line2;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getPostalCode() {
    return postalCode;
  }
}
