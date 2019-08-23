package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;

public class PhoneNumber {

  @SerializedName("local_number")
  private String localNumber;

  @SerializedName("country_code")
  private Integer countryCode;

  public PhoneNumber(Integer countryCode, String localNumber) {
    this.localNumber = localNumber;
    this.countryCode = countryCode;
  }

  public String getLocalNumber() {
    return localNumber;
  }

  public Integer getCountryCode() {
    return countryCode;
  }
}
