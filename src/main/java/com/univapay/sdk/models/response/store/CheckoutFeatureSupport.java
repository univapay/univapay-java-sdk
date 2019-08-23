package com.univapay.sdk.models.response.store;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import java.util.Set;

public class CheckoutFeatureSupport {
  @SerializedName("card_brand")
  private CardBrand cardBrand;

  @SerializedName("support_auth_capture")
  private Boolean supportAuthCapture;

  @SerializedName("requires_full_name")
  private Boolean requiresFullName;

  @SerializedName("support_dynamic_descriptor")
  private Boolean supportsDynamicDescriptor;

  @SerializedName("requires_cvv")
  private Boolean requiresCVV;

  @SerializedName("countries_allowed")
  private Set<Country> countriesAllowed;

  @SerializedName("supported_currencies")
  private Set<String> supportedCurrencies;

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
}
