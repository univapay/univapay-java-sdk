package com.univapay.sdk.utils.builders;

import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.FlatFee;
import com.univapay.sdk.models.common.InstallmentsConfiguration;
import com.univapay.sdk.models.common.KonbiniConfiguration;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.PaidyConfiguration;
import com.univapay.sdk.models.common.QrMerchantConfiguration;
import com.univapay.sdk.models.common.UserTransactionsConfiguration;
import com.univapay.sdk.models.common.stores.SecurityConfiguration;
import com.univapay.sdk.models.request.configuration.ConfigurationRequest;
import com.univapay.sdk.models.request.configuration.TransferScheduleConfigurationRequest;
import com.univapay.sdk.models.response.store.CardConfiguration;
import com.univapay.sdk.models.response.store.QrScanConfiguration;
import com.univapay.sdk.models.response.store.RecurringTokenConfiguration;
import com.univapay.sdk.models.response.subscription.SubscriptionConfiguration;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.threeten.bp.ZoneId;

public class ConfigurationBuilder<T extends ConfigurationRequest> implements Builder<T> {
  private BigDecimal percentFee;
  private List<FlatFee> flatFees;
  private URL logoUrl;
  private Locale language;
  private Country country;
  private MoneyLike minTransferPayout;
  private List<MoneyLike> maximumChargeAmounts;
  private TransferScheduleConfigurationRequest transferScheduleConfiguration;
  private ZoneId timeZone;
  private UserTransactionsConfiguration userTransactionsConfiguration;
  private CardConfiguration cardConfiguration;
  private QrScanConfiguration qrScanConfiguration;
  private KonbiniConfiguration convenienceConfiguration;
  private PaidyConfiguration paidyConfiguration;
  private QrMerchantConfiguration qrMerchantConfiguration;
  private RecurringTokenConfiguration recurringConfiguration;
  private InstallmentsConfiguration installmentsConfiguration;
  private SecurityConfiguration securityConfiguration;
  private Map<CardBrand, BigDecimal> cardBrandPercentFees;
  private SubscriptionConfiguration subscriptionConfiguration;

  public ConfigurationBuilder withPercentFee(BigDecimal percentFee) {
    this.percentFee = percentFee;
    return this;
  }

  public ConfigurationBuilder withFlatFees(List<FlatFee> flatFees) {
    this.flatFees = flatFees;
    return this;
  }

  public ConfigurationBuilder withLogoUrl(URL logoUrl) {
    this.logoUrl = logoUrl;
    return this;
  }

  public ConfigurationBuilder withLanguage(Locale language) {
    this.language = language;
    return this;
  }

  @Deprecated
  /** @deprecated This method will be deleted on later release */
  public ConfigurationBuilder withCountry(String country) {
    this.country = Country.getCountryByAlpha2(country);
    return this;
  }

  public ConfigurationBuilder withCountry(Country country) {
    this.country = country;
    return this;
  }

  public ConfigurationBuilder withMinTransferPayout(MoneyLike minTransferPayout) {
    this.minTransferPayout = minTransferPayout;
    return this;
  }

  public ConfigurationBuilder withMaxPayoutPeriod(List<MoneyLike> maxPayoutPeriod) {
    this.maximumChargeAmounts = maxPayoutPeriod;
    return this;
  }

  public ConfigurationBuilder withTransferScheduleConfiguration(
      TransferScheduleConfigurationRequest transferScheduleConfiguration) {
    this.transferScheduleConfiguration = transferScheduleConfiguration;
    return this;
  }

  public ConfigurationBuilder withTimeZone(ZoneId timeZone) {
    this.timeZone = timeZone;
    return this;
  }

  public ConfigurationBuilder withUserTransactionsConfiguration(
      UserTransactionsConfiguration userTransactionsConfiguration) {
    this.userTransactionsConfiguration = userTransactionsConfiguration;
    return this;
  }

  public ConfigurationBuilder withCardConfiguration(CardConfiguration cardConfiguration) {
    this.cardConfiguration = cardConfiguration;
    return this;
  }

  public ConfigurationBuilder withQrScanConfiguration(QrScanConfiguration qrScanConfiguration) {
    this.qrScanConfiguration = qrScanConfiguration;
    return this;
  }

  public ConfigurationBuilder withConvenienceConfiguration(
      KonbiniConfiguration convenienceConfiguration) {
    this.convenienceConfiguration = convenienceConfiguration;
    return this;
  }

  public ConfigurationBuilder withPaidyConfiguration(PaidyConfiguration paidyConfiguration) {
    this.paidyConfiguration = paidyConfiguration;
    return this;
  }

  public ConfigurationBuilder withQrMerchantConfiguration(
      QrMerchantConfiguration qrMerchantConfiguration) {
    this.qrMerchantConfiguration = qrMerchantConfiguration;
    return this;
  }

  public ConfigurationBuilder withRecurringConfiguration(
      RecurringTokenConfiguration recurringConfiguration) {
    this.recurringConfiguration = recurringConfiguration;
    return this;
  }

  public ConfigurationBuilder withSecurityConfiguration(
      SecurityConfiguration securityConfiguration) {
    this.securityConfiguration = securityConfiguration;
    return this;
  }

  public ConfigurationBuilder withInstallmentsConfiguration(
      InstallmentsConfiguration installmentsConfiguration) {
    this.installmentsConfiguration = installmentsConfiguration;
    return this;
  }

  public ConfigurationBuilder withCardBrandPercentFees(
      Map<CardBrand, BigDecimal> cardBrandPercentFees) {
    this.cardBrandPercentFees = cardBrandPercentFees;
    return this;
  }

  public ConfigurationBuilder withSubscriptionConfiguration(
      SubscriptionConfiguration subscriptionConfiguration) {
    this.subscriptionConfiguration = subscriptionConfiguration;
    return this;
  }

  @Override
  public T build() {
    return (T)
        new ConfigurationRequest(
            percentFee,
            flatFees,
            logoUrl,
            country,
            language,
            minTransferPayout,
            maximumChargeAmounts,
            transferScheduleConfiguration,
            timeZone,
            userTransactionsConfiguration,
            cardConfiguration,
            qrScanConfiguration,
            convenienceConfiguration,
            paidyConfiguration,
            qrMerchantConfiguration,
            recurringConfiguration,
            securityConfiguration,
            cardBrandPercentFees,
            installmentsConfiguration,
            subscriptionConfiguration);
  }

  public BigDecimal getPercentFee() {
    return percentFee;
  }

  public List<FlatFee> getFlatFees() {
    return flatFees;
  }

  public URL getLogoUrl() {
    return logoUrl;
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

  public Locale getLanguage() {
    return language;
  }

  public TransferScheduleConfigurationRequest getTransferScheduleConfiguration() {
    return transferScheduleConfiguration;
  }

  public ZoneId getTimeZone() {
    return timeZone;
  }

  public UserTransactionsConfiguration getUserTransactionsConfiguration() {
    return userTransactionsConfiguration;
  }

  public CardConfiguration getCardConfiguration() {
    return cardConfiguration;
  }

  public QrScanConfiguration getQrScanConfiguration() {
    return qrScanConfiguration;
  }

  public KonbiniConfiguration getConvenienceConfiguration() {
    return convenienceConfiguration;
  }

  public PaidyConfiguration getPaidyConfiguration() {
    return paidyConfiguration;
  }

  public QrMerchantConfiguration getQrMerchantConfiguration() {
    return qrMerchantConfiguration;
  }

  public RecurringTokenConfiguration getRecurringConfiguration() {
    return recurringConfiguration;
  }

  public SecurityConfiguration getSecurityConfiguration() {
    return securityConfiguration;
  }

  public Map<CardBrand, BigDecimal> getCardBrandPercentFees() {
    return cardBrandPercentFees;
  }

  public MoneyLike getMinTransferPayout() {
    return minTransferPayout;
  }

  public InstallmentsConfiguration getInstallmentsConfiguration() {
    return installmentsConfiguration;
  }

  public SubscriptionConfiguration getSubscriptionConfiguration() {
    return subscriptionConfiguration;
  }
}
