package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.charge.CvvAuthorizationEnable;
import com.univapay.sdk.models.common.threeDs.TransactionToken3dsCreateData;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.models.response.transactiontoken.PhoneNumber;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.types.PaymentTypeName;
import lombok.Getter;

public class CreditCard implements PaymentData {
  @Getter
  @SerializedName("cardholder")
  private final String cardholder;

  @Getter
  @SerializedName("card_number")
  private final String cardNumber;

  @Getter
  @SerializedName("exp_month")
  private final int expMonth;

  @Getter
  @SerializedName("exp_year")
  private final int expYear;

  @Getter
  @SerializedName("cvv")
  private final String cvv;

  @Getter
  @SerializedName("line1")
  private String line1;

  @Getter
  @SerializedName("line2")
  private String line2;

  @Getter
  @SerializedName("state")
  private String state;

  @Getter
  @SerializedName("city")
  private String city;

  @SerializedName("country")
  private Country country;

  @Getter
  @SerializedName("zip")
  private String postalCode;

  @Getter
  @SerializedName("phone_number")
  private PhoneNumber phoneNumber;

  @Getter
  @SerializedName("cvv_authorize")
  private CvvAuthorizationEnable cvvAuthorize;

  @Getter
  @SerializedName("three_ds")
  private TransactionToken3dsCreateData threeDs;

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

  @Getter private final transient CardBrand cardType;

  public CreditCard(String cardholder, String cardNumber, int expMonth, int expYear) {
    this(cardholder, cardNumber, expMonth, expYear, null);
  }

  public CreditCard(String cardholder, String cardNumber, int expMonth, int expYear, String cvv) {
    this.cardholder = cardholder;
    this.cardNumber = cardNumber;
    this.expMonth = expMonth;
    this.expYear = expYear;
    this.cvv = cvv;
    this.cardType = CardBrand.forCardNumber(cardNumber);
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

  public CreditCard withCvvAuthorization(boolean enabled) {
    this.cvvAuthorize = new CvvAuthorizationEnable(enabled);
    return this;
  }

  public CreditCard withThreeDsConfiguration(boolean enabled, String redirectEndpoint) {
    this.threeDs = new TransactionToken3dsCreateData(enabled, redirectEndpoint);

    return this;
  }

  public CreditCard withThreeDsConfiguration(String redirectEndpoint) {

    this.threeDs = new TransactionToken3dsCreateData(null, redirectEndpoint);
    return this;
  }
}
