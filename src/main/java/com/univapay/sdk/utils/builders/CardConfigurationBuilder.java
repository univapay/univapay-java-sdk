package com.univapay.sdk.utils.builders;

import com.univapay.sdk.models.response.CardLimit;
import com.univapay.sdk.models.response.store.CardConfiguration;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import java.util.List;

public class CardConfigurationBuilder implements Builder<CardConfiguration> {

  private Boolean enabled;
  private Boolean debitEnabled;
  private Boolean prepaidEnabled;
  private List<CardBrand> forbiddenCardBrands;
  private List<Country> allowedCountriesByIp;
  private Boolean foreignCardsAllowed;
  private Boolean failOnNewEmail;
  private Boolean allowEmptyCvv;
  private CardLimit cardLimit;
  private Boolean onlyDirectCurrency;
  private Boolean debitAuthorizationEnabled;
  private Boolean prepaidAuthorizationEnabled;

  public CardConfigurationBuilder withEnabled(Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  public CardConfigurationBuilder withDebitEnabled(Boolean debitEnabled) {
    this.debitEnabled = debitEnabled;
    return this;
  }

  public CardConfigurationBuilder withPrepaidEnabled(Boolean prepaidEnabled) {
    this.prepaidEnabled = prepaidEnabled;
    return this;
  }

  public CardConfigurationBuilder withDebitAuthorizationEnabled(Boolean debitAuthorizationEnabled) {
    this.debitAuthorizationEnabled = debitAuthorizationEnabled;
    return this;
  }

  public CardConfigurationBuilder withPrepaidAuthorizationEnabled(
      Boolean prepaidAuthorizationEnabled) {
    this.prepaidAuthorizationEnabled = prepaidAuthorizationEnabled;
    return this;
  }

  public CardConfigurationBuilder withForbiddenCardBrands(List<CardBrand> forbiddenCardBrands) {
    this.forbiddenCardBrands = forbiddenCardBrands;
    return this;
  }

  public CardConfigurationBuilder withAllowedCountriesByIp(List<Country> allowedCountriesByIp) {
    this.allowedCountriesByIp = allowedCountriesByIp;
    return this;
  }

  public CardConfigurationBuilder withForeignCardsAllowed(Boolean foreignCardsAllowed) {
    this.foreignCardsAllowed = foreignCardsAllowed;
    return this;
  }

  public CardConfigurationBuilder withFailOnNewEmail(Boolean failOnNewEmail) {
    this.failOnNewEmail = failOnNewEmail;
    return this;
  }

  public CardConfigurationBuilder withAllowEmptyCvv(Boolean allowEmptyCvv) {
    this.allowEmptyCvv = allowEmptyCvv;
    return this;
  }

  public CardConfigurationBuilder withCardLimit(CardLimit cardLimit) {
    this.cardLimit = cardLimit;
    return this;
  }

  public CardConfigurationBuilder withOnlyDirectCurrency(Boolean onlyDirectCurrency) {
    this.onlyDirectCurrency = onlyDirectCurrency;
    return this;
  }

  @Override
  public CardConfiguration build() {
    return new CardConfiguration(
        enabled,
        debitEnabled,
        prepaidEnabled,
        forbiddenCardBrands,
        allowedCountriesByIp,
        foreignCardsAllowed,
        failOnNewEmail,
        allowEmptyCvv,
        cardLimit,
        onlyDirectCurrency,
        debitAuthorizationEnabled,
        prepaidAuthorizationEnabled);
  }

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

  public Boolean getPrepaidAuthorizationEnabled() {
    return prepaidAuthorizationEnabled;
  }

  public Boolean getDebitAuthorizationEnabled() {
    return debitAuthorizationEnabled;
  }
}
