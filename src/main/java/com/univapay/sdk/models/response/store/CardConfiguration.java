package com.univapay.sdk.models.response.store;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.response.CardLimit;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import java.util.List;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public class CardConfiguration {

  @SerializedName("enabled")
  private final Boolean enabled;

  @SerializedName("debit_enabled")
  private final Boolean debitEnabled;

  @SerializedName("prepaid_enabled")
  private final Boolean prepaidEnabled;

  @SerializedName("forbidden_card_brands")
  private final List<CardBrand> forbiddenCardBrands;

  @SerializedName("allowed_countries_by_ip")
  private final List<Country> allowedCountriesByIp;

  @SerializedName("foreign_cards_allowed")
  private final Boolean foreignCardsAllowed;

  @SerializedName("fail_on_new_email")
  private final Boolean failOnNewEmail;

  @SerializedName("allow_empty_cvv")
  private final Boolean allowEmptyCvv;

  @SerializedName("card_limit")
  private final CardLimit cardLimit;

  @SerializedName("only_direct_currency")
  private final Boolean onlyDirectCurrency;

  @SerializedName("debit_authorization_enabled")
  private final Boolean debitAuthorizationEnabled;

  @SerializedName("prepaid_authorization_enabled")
  private final Boolean prepaidAuthorizationEnabled;

  @SerializedName("three_ds_required")
  private final Boolean threeDSRequired;

  @SerializedName("three_ds_address_required")
  private final Boolean threeDSAddressRequired;

  @SerializedName("three_ds_skip_enabled")
  private final Boolean threeDSSkipEnabled;

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
      @Nullable Boolean onlyDirectCurrency,
      @Nullable Boolean debitAuthorizationEnabled,
      @Nullable Boolean prepaidAuthorizationEnabled,
      @Nullable Boolean threeDSRequired,
      @Nullable Boolean threeDSAddressRequired,
      @Nullable Boolean threeDSSkipEnabled) {
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
    this.debitAuthorizationEnabled = debitAuthorizationEnabled;
    this.prepaidAuthorizationEnabled = prepaidAuthorizationEnabled;
    this.threeDSRequired = threeDSRequired;
    this.threeDSAddressRequired = threeDSAddressRequired;
    this.threeDSSkipEnabled = threeDSSkipEnabled;
  }

  public CardConfiguration() {
    this.enabled = null;
    this.debitEnabled = null;
    this.prepaidEnabled = null;
    this.forbiddenCardBrands = null;
    this.allowedCountriesByIp = null;
    this.foreignCardsAllowed = null;
    this.failOnNewEmail = null;
    this.allowEmptyCvv = null;
    this.cardLimit = null;
    this.onlyDirectCurrency = null;
    this.debitAuthorizationEnabled = null;
    this.prepaidAuthorizationEnabled = null;
    this.threeDSRequired = null;
    this.threeDSAddressRequired = null;
    this.threeDSSkipEnabled = null;
  }
}
