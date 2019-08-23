package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;

public class VerificationPhoneNumber {

  @SerializedName("country_code")
  private Integer countryCode;

  @SerializedName("local_number")
  private String localNumber;

  public VerificationPhoneNumber(Integer countryCode, String localNumber) {
    this.countryCode = countryCode;
    this.localNumber = localNumber;
  }

  public Integer getCountryCode() {
    return countryCode;
  }

  public String getLocalNumber() {
    return localNumber;
  }
}
