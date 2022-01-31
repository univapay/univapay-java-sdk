package com.univapay.sdk.models.response.store;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import java.util.Set;

public class CheckoutFeatureSupport {
  @SerializedName("card_brand")
  private final CardBrand cardBrand;

  @SerializedName("support_auth_capture")
  private final Boolean supportAuthCapture;

  @SerializedName("requires_full_name")
  private final Boolean requiresFullName;

  @SerializedName("support_dynamic_descriptor")
  private final Boolean supportsDynamicDescriptor;

  @SerializedName("requires_cvv")
  private final Boolean requiresCVV;

  @SerializedName("countries_allowed")
  private final Set<Country> countriesAllowed;

  @SerializedName("supported_currencies")
  private final Set<String> supportedCurrencies;

  public CardBrand getCardBrand() {
    return cardBrand;
  }

  public Boolean getSupportAuthCapture() {
    return supportAuthCapture;
  }

  public Boolean getRequiresFullName() {
    return requiresFullName;
  }

  public Boolean getSupportsDynamicDescriptor() {
    return supportsDynamicDescriptor;
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

  // Not to be manually created
  public CheckoutFeatureSupport(
      CardBrand cardBrand,
      Boolean supportAuthCapture,
      Boolean requiresFullName,
      Boolean supportsDynamicDescriptor,
      Boolean requiresCVV,
      Set<Country> countriesAllowed,
      Set<String> supportedCurrencies) {
    this.cardBrand = cardBrand;
    this.supportAuthCapture = supportAuthCapture;
    this.requiresFullName = requiresFullName;
    this.supportsDynamicDescriptor = supportsDynamicDescriptor;
    this.requiresCVV = requiresCVV;
    this.countriesAllowed = countriesAllowed;
    this.supportedCurrencies = supportedCurrencies;
  }
}
