package com.univapay.sdk.models.response.store;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.types.brand.OnlineBrand;
import com.univapay.sdk.types.brand.QrMpmBrand;
import java.util.Set;

public class CheckoutFeatureSupport {
  @SerializedName("card_brand")
  private final CardBrand cardBrand;

  @SerializedName("online_brand")
  private final OnlineBrand onlineBrand;

  @SerializedName("qr_code")
  private final QrMpmBrand qrMpmBrand;

  @SerializedName("dynamic_info")
  private final Boolean dynamicInfo;

  @SerializedName("support_auth_capture")
  private final Boolean supportAuthCapture;

  @SerializedName("requires_full_name")
  private final Boolean requiresFullName;

  @SerializedName("requires_cvv")
  private final Boolean requiresCVV;

  @SerializedName("countries_allowed")
  private final Set<Country> countriesAllowed;

  @SerializedName("supported_currencies")
  private final Set<String> supportedCurrencies;

  protected CheckoutFeatureSupport(
      CardBrand cardBrand,
      OnlineBrand onlineBrand,
      QrMpmBrand qrMpmBrand,
      Boolean dynamicInfo,
      Boolean supportAuthCapture,
      Boolean requiresFullName,
      Boolean requiresCVV,
      Set<Country> countriesAllowed,
      Set<String> supportedCurrencies) {
    this.cardBrand = cardBrand;
    this.onlineBrand = onlineBrand;
    this.qrMpmBrand = qrMpmBrand;
    this.dynamicInfo = dynamicInfo;
    this.supportAuthCapture = supportAuthCapture;
    this.requiresFullName = requiresFullName;
    this.requiresCVV = requiresCVV;
    this.countriesAllowed = countriesAllowed;
    this.supportedCurrencies = supportedCurrencies;
  }

  public CheckoutFeatureSupport(
      CardBrand cardBrand,
      Boolean supportAuthCapture,
      Boolean requiresFullName,
      Boolean requiresCVV,
      Set<Country> countriesAllowed,
      Set<String> supportedCurrencies,
      Boolean dynamicInfo) {
    this(
        cardBrand,
        null,
        null,
        dynamicInfo,
        supportAuthCapture,
        requiresFullName,
        requiresCVV,
        countriesAllowed,
        supportedCurrencies);
  }

  public CheckoutFeatureSupport(
      OnlineBrand onlineBrand,
      Boolean supportAuthCapture,
      Boolean requiresFullName,
      Boolean requiresCVV,
      Set<Country> countriesAllowed,
      Set<String> supportedCurrencies,
      Boolean dynamicInfo) {
    this(
        null,
        onlineBrand,
        null,
        dynamicInfo,
        supportAuthCapture,
        requiresFullName,
        requiresCVV,
        countriesAllowed,
        supportedCurrencies);
  }

  public CheckoutFeatureSupport(
      QrMpmBrand qrMpmBrand,
      Boolean supportAuthCapture,
      Boolean requiresFullName,
      Boolean supportsDynamicDescriptor,
      Boolean requiresCVV,
      Set<Country> countriesAllowed,
      Set<String> supportedCurrencies,
      Boolean dynamicInfo) {
    this(
        null,
        null,
        qrMpmBrand,
        dynamicInfo,
        supportAuthCapture,
        requiresFullName,
        requiresCVV,
        countriesAllowed,
        supportedCurrencies);
  }

  public CardBrand getCardBrand() {
    return cardBrand;
  }

  public OnlineBrand getOnlineBrand() {
    return onlineBrand;
  }

  public Boolean getSupportAuthCapture() {
    return supportAuthCapture;
  }

  public Boolean getRequiresFullName() {
    return requiresFullName;
  }

  public Boolean getRequiresCVV() {
    return requiresCVV;
  }

  public Set<Country> getCountriesAllowed() {
    return countriesAllowed;
  }

  public Set<String> getSupportedCurrencies() {
    return supportedCurrencies;
  }

  public QrMpmBrand getQrMpmBrand() {
    return qrMpmBrand;
  }
}
