package com.univapay.sdk.models.response.merchant;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.VerificationPhoneNumber;
import com.univapay.sdk.types.Country;
import org.jetbrains.annotations.Nullable;

public class MerchantCompanyContactInfo {
  @SerializedName("name")
  private String name;

  @SerializedName("company_name")
  private String companyName;

  @SerializedName("phone_number")
  private VerificationPhoneNumber phoneNumber;

  @SerializedName("line1")
  private String adressLine1;

  @SerializedName("line2")
  private String adressLine2;

  @SerializedName("state")
  private String state;

  @SerializedName("city")
  private String city;

  @SerializedName("country")
  private Country country;

  @SerializedName("zip")
  private String zip;

  @Deprecated
  /**
   * This constructor will be deleted on later release
   *
   * @deprecated
   */
  public MerchantCompanyContactInfo(
      String name,
      String companyName,
      VerificationPhoneNumber phoneNumber,
      String adressLine1,
      @Nullable String adressLine2,
      String state,
      String city,
      String country,
      String zip) {
    this.name = name;
    this.companyName = companyName;
    this.phoneNumber = phoneNumber;
    this.adressLine1 = adressLine1;
    this.adressLine2 = adressLine2;
    this.state = state;
    this.city = city;
    this.country = Country.getCountryByAlpha2(country);
    this.zip = zip;
  }

  public MerchantCompanyContactInfo(
      String name,
      String companyName,
      VerificationPhoneNumber phoneNumber,
      String adressLine1,
      @Nullable String adressLine2,
      String state,
      String city,
      Country country,
      String zip) {
    this.name = name;
    this.companyName = companyName;
    this.phoneNumber = phoneNumber;
    this.adressLine1 = adressLine1;
    this.adressLine2 = adressLine2;
    this.state = state;
    this.city = city;
    this.country = country;
    this.zip = zip;
  }

  public String getName() {
    return name;
  }

  public String getCompanyName() {
    return companyName;
  }

  public VerificationPhoneNumber getPhoneNumber() {
    return phoneNumber;
  }

  public String getAdressLine1() {
    return adressLine1;
  }

  public String getAdressLine2() {
    return adressLine2;
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
}
