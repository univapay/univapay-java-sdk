package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.Country;

public class TransactionTokenBillingData {
  @SerializedName("line1")
  private String line1;

  @SerializedName("line2")
  private String line2;

  @SerializedName("state")
  private String state;

  @SerializedName("city")
  private String city;

  @SerializedName("country")
  private Country country;

  @SerializedName("zip")
  private String zip;

  @SerializedName("phone_number")
  private PhoneNumber phoneNumber;

  public String getLine1() {
    return line1;
  }

  public String getLine2() {
    return line2;
  }

  public String getState() {
    return state;
  }

  public String getCity() {
    return city;
  }

  /**
   * The returned type will be changed to {@link Country} on later release
   *
   * @return country
   */
  public String getCountry() {
    if (country == null) {
      return null;
    }
    return country.getAlpha2();
  }

  /**
   * This method will be deleted when the returned type by "getCountry(String)" is changed to {@link
   * Country}
   *
   * @return country enum
   */
  public Country getCountryEnum() {
    return country;
  }

  public String getZip() {
    return zip;
  }

  public PhoneNumber getPhoneNumber() {
    return phoneNumber;
  }
}
