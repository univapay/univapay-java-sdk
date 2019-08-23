package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.models.response.transactiontoken.PhoneNumber;

public class ApplePay implements PaymentData {
  @SerializedName("applepay_token")
  private String applepayToken;

  @SerializedName("cardholder")
  private String cardholder;

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
  private String postalCode;

  @SerializedName("phone_number")
  private PhoneNumber phoneNumber;

  public String getApplepayToken() {
    return applepayToken;
  }

  public String getCardholder() {
    return cardholder;
  }

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

  public String getPostalCode() {
    return postalCode;
  }

  public PhoneNumber getPhoneNumber() {
    return phoneNumber;
  }

  public ApplePay(String applepayToken, String cardholder) {
    this.applepayToken = applepayToken;
    this.cardholder = cardholder;
  }

  @Deprecated
  /**
   * This method will be deleted on later release
   *
   * @deprecated
   */
  public ApplePay addAddress(
      String country, String state, String city, String line1, String line2, String postalCode) {
    this.country = Country.getCountryByAlpha2(country);
    this.state = state;
    this.city = city;
    this.line1 = line1;
    this.line2 = line2;
    this.postalCode = postalCode;
    return this;
  }

  public ApplePay addAddress(
      Country country, String state, String city, String line1, String line2, String postalCode) {
    this.country = country;
    this.state = state;
    this.city = city;
    this.line1 = line1;
    this.line2 = line2;
    this.postalCode = postalCode;
    return this;
  }

  public ApplePay addPhoneNumber(PhoneNumber phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  @Override
  public PaymentTypeName getPaymentType() {
    return PaymentTypeName.APPLE_PAY;
  }
}
