package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.charge.CvvAuthorizationEnable;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.models.response.transactiontoken.PhoneNumber;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.types.PaymentTypeName;

public class CreditCard implements PaymentData {
  @SerializedName("cardholder")
  private String cardholder;

  @SerializedName("card_number")
  private String cardNumber;

  @SerializedName("exp_month")
  private int expMonth;

  @SerializedName("exp_year")
  private int expYear;

  @SerializedName("cvv")
  private Integer cvv;

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

  @SerializedName("cvv_authorize")
  private CvvAuthorizationEnable cvvAuthorize;

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

  @Deprecated
  /** @deprecated This method will be deleted on later release */
  public CreditCard addAddress(
      String country, String state, String city, String line1, String line2, String postalCode) {
    this.country = Country.getCountryByAlpha2(country);
    this.state = state;
    this.city = city;
    this.line1 = line1;
    this.line2 = line2;
    this.postalCode = postalCode;
    return this;
  }

  public CreditCard addAddress(
      Country country, String state, String city, String line1, String line2, String postalCode) {
    this.country = country;
    this.state = state;
    this.city = city;
    this.line1 = line1;
    this.line2 = line2;
    this.postalCode = postalCode;
    return this;
  }

  public CreditCard addPhoneNumber(PhoneNumber phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  private transient CardBrand cardType;

  public CreditCard(String cardholder, String cardNumber, int expMonth, int expYear) {
    this(cardholder, cardNumber, expMonth, expYear, null);
  }

  public CreditCard(String cardholder, String cardNumber, int expMonth, int expYear, Integer cvv) {
    this.cardholder = cardholder;
    this.cardNumber = cardNumber;
    this.expMonth = expMonth;
    this.expYear = expYear;
    this.cvv = cvv;
    this.cardType = CardBrand.forCardNumber(cardNumber);
  }

  public String getCardholder() {
    return cardholder;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public int getExpMonth() {
    return expMonth;
  }

  public int getExpYear() {
    return expYear;
  }

  public Integer getCvv() {
    return cvv;
  }

  public CardBrand getCardType() {
    return cardType;
  }

  public String getState() {
    return state;
  }

  public String getCity() {
    return city;
  }

  public String getLine1() {
    return line1;
  }

  public String getLine2() {
    return line2;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public PhoneNumber getPhoneNumber() {
    return phoneNumber;
  }

  public boolean validateDate() {
    return DateValidator.isValid(this.expMonth, this.expYear);
  }

  public boolean validateNumber() {
    return DateValidator.isValid(this.expMonth, this.expYear);
  }

  public boolean validateCard() {
    return validateDate() && validateNumber();
  }

  @Override
  public PaymentTypeName getPaymentType() {
    return PaymentTypeName.CARD;
  }

  public CvvAuthorizationEnable getCvvAuthorize() {
    return cvvAuthorize;
  }

  public CreditCard withCvvAuthorization(boolean enabled) {
    this.cvvAuthorize = new CvvAuthorizationEnable(enabled);
    return this;
  }
}
