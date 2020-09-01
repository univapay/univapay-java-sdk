package com.univapay.sdk.models.response.store;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.response.CardLimit;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import java.util.List;
import javax.annotation.Nullable;

public class CardConfiguration {
  @SerializedName("enabled")
  private Boolean enabled;

  @SerializedName("debit_enabled")
  private Boolean debitEnabled;

  @SerializedName("prepaid_enabled")
  private Boolean prepaidEnabled;

  @SerializedName("forbidden_card_brands")
  private List<CardBrand> forbiddenCardBrands;

  @SerializedName("allowed_countries_by_ip")
  private List<Country> allowedCountriesByIp;

  @SerializedName("foreign_cards_allowed")
  private Boolean foreignCardsAllowed;

  @SerializedName("fail_on_new_email")
  private Boolean failOnNewEmail;

  @SerializedName("allow_empty_cvv")
  private Boolean allowEmptyCvv;

  @SerializedName("card_limit")
  private CardLimit cardLimit;

  @SerializedName("only_direct_currency")
  private final Boolean onlyDirectCurrency;

  public Boolean getEnabled() {
    return enabled;
  }

  public Boolean getDebitEnabled() {
    return debitEnabled;
  }

  public Boolean getPrepaidEnabled() {
    return prepaidEnabled;
  }

  public List<CardBrand> getForbiddenCardBrands() {
    return forbiddenCardBrands;
  }

  public List<Country> getAllowedCountriesByIp() {
    return allowedCountriesByIp;
  }

  public Boolean getForeignCardsAllowed() {
    return foreignCardsAllowed;
  }

  public Boolean getFailOnNewEmail() {
    return failOnNewEmail;
  }

  public Boolean getAllowEmptyCvv() {
    return allowEmptyCvv;
  }

  public CardLimit getCardLimit() {
    return cardLimit;
  }

  public Boolean getOnlyDirectCurrency() {
    return onlyDirectCurrency;
  }

  public CardConfiguration(
      @Nullable Boolean enabled,
      @Nullable Boolean debitEnabled,
      @Nullable Boolean prepaidEnabled,
      @Nullable List<CardBrand> forbiddenCardBrands,
      @Nullable List<Country> allowedCountriesByIp,
      @Nullable Boolean foreignCardsAllowed,
      @Nullable Boolean failOnNewEmail,
      @Nullable Boolean allowEmptyCvv,
      @Nullable CardLimit cardLimit,
      @Nullable Boolean onlyDirectCurrency) {
    this.enabled = enabled;
    this.debitEnabled = debitEnabled;
    this.prepaidEnabled = prepaidEnabled;
    this.forbiddenCardBrands = forbiddenCardBrands;
    this.allowedCountriesByIp = allowedCountriesByIp;
    this.foreignCardsAllowed = foreignCardsAllowed;
    this.failOnNewEmail = failOnNewEmail;
    this.allowEmptyCvv = allowEmptyCvv;
    this.cardLimit = cardLimit;
    this.onlyDirectCurrency = onlyDirectCurrency;
  }
}
