package com.univapay.sdk.models.response.configuration;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.stores.SecurityConfiguration;
import com.univapay.sdk.models.response.store.CardConfiguration;
import com.univapay.sdk.models.response.store.QrScanConfiguration;
import com.univapay.sdk.models.response.store.RecurringTokenConfiguration;
import com.univapay.sdk.models.response.subscription.SubscriptionConfiguration;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import java.math.BigDecimal;
import java.net.URL;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

public class Configuration {

  @SerializedName("percent_fee")
  private BigDecimal percentFee;

  @SerializedName("flat_fees")
  private List<FlatFee> flatFees;

  @SerializedName("logo_url")
  private URL logoUrl;

  @SerializedName("country")
  private Country country;

  @SerializedName("language")
  private Locale language;

  @SerializedName("min_transfer_payout")
  private MoneyLike minTransferPayout;

  @SerializedName("maximum_charge_amounts")
  private List<MoneyLike> maximumChargeAmounts;

  @SerializedName("minimum_charge_amounts")
  private List<MoneyLike> minimumChargeAmounts;

  @SerializedName("transfer_schedule")
  private TransferScheduleConfiguration transferScheduleConfiguration;

  @SerializedName("display_time_zone")
  private ZoneId timeZone;

  @SerializedName("user_transactions_configuration")
  private UserTransactionsConfiguration userTransactionsConfiguration;

  @SerializedName("card_configuration")
  private CardConfiguration cardConfiguration;

  @SerializedName("qr_scan_configuration")
  private QrScanConfiguration qrScanConfiguration;

  @SerializedName("convenience_configuration")
  private KonbiniConfiguration convenienceConfiguration;

  @SerializedName("paidy_configuration")
  private PaidyConfiguration paidyConfiguration;

  @SerializedName("qr_merchant_configuration")
  private QrMerchantConfiguration qrMerchantConfiguration;

  @SerializedName("online_configuration")
  private OnlineConfiguration onlineConfiguration;

  @SerializedName("recurring_token_configuration")
  private RecurringTokenConfiguration recurringConfiguration;

  @SerializedName("security_configuration")
  private SecurityConfiguration securityConfiguration;

  @SerializedName("installments_configuration")
  private InstallmentsConfiguration installmentsConfiguration;

  @SerializedName("card_brand_percent_fees")
  private Map<CardBrand, BigDecimal> cardBrandPercentFees;

  @SerializedName("subscription_configuration")
  private SubscriptionConfiguration subscriptionConfiguration;

  @SerializedName("platform_credentials_enabled")
  private Boolean platformCredentialsEnabled;

  @SerializedName("descriptor_provided_configuration")
  private DescriptorProvidedConfiguration descriptorProvidedConfiguration;

  public BigDecimal getPercentFee() {
    return percentFee;
  }

  public List<FlatFee> getFlatFees() {
    return flatFees;
  }

  public URL getLogoUrl() {
    return logoUrl;
  }

  public Country getCountry() {
    return country;
  }

  public Locale getLanguage() {
    return language;
  }

  public MoneyLike getMinTransferPayout() {
    return minTransferPayout;
  }

  public List<MoneyLike> getMaximumChargeAmounts() {
    return maximumChargeAmounts;
  }

  public TransferScheduleConfiguration getTransferScheduleConfiguration() {
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

  public SecurityConfiguration getSecurityConfiguration() {
    return securityConfiguration;
  }

  public InstallmentsConfiguration getInstallmentsConfiguration() {
    return installmentsConfiguration;
  }

  public Map<CardBrand, BigDecimal> getCardBrandPercentFees() {
    return cardBrandPercentFees;
  }

  public KonbiniConfiguration getConvenienceConfiguration() {
    return convenienceConfiguration;
  }

  public PaidyConfiguration getPaidyConfiguration() {
    return paidyConfiguration;
  }

  public RecurringTokenConfiguration getRecurringConfiguration() {
    return recurringConfiguration;
  }

  public QrMerchantConfiguration getQrMerchantConfiguration() {
    return qrMerchantConfiguration;
  }

  public SubscriptionConfiguration getSubscriptionConfiguration() {
    return subscriptionConfiguration;
  }

  public Boolean getPlatformCredentialsEnabled() {
    return platformCredentialsEnabled;
  }

  public DescriptorProvidedConfiguration getDescriptorProvidedConfiguration() {
    return descriptorProvidedConfiguration;
  }

  public OnlineConfiguration getOnlineConfiguration() {
    return onlineConfiguration;
  }

  public List<MoneyLike> getMinimumChargeAmounts() {
    return minimumChargeAmounts;
  }

  public Configuration(
      @Nullable BigDecimal percentFee,
      @Nullable List<FlatFee> flatFees,
      @Nullable URL logoUrl,
      @Nullable Country country,
      @Nullable Locale language,
      @Nullable MoneyLike minTransferPayout,
      @Nullable List<MoneyLike> maximumChargeAmounts,
      @Nullable List<MoneyLike> minimumChargeAmounts,
      @Nullable TransferScheduleConfiguration transferScheduleConfiguration,
      @Nullable ZoneId timeZone,
      @Nullable UserTransactionsConfiguration userTransactionsConfiguration,
      @Nullable CardConfiguration cardConfiguration,
      @Nullable QrScanConfiguration qrScanConfiguration,
      @Nullable KonbiniConfiguration convenienceConfiguration,
      @Nullable PaidyConfiguration paidyConfiguration,
      @Nullable QrMerchantConfiguration qrMerchantConfiguration,
      @Nullable OnlineConfiguration onlineConfiguration,
      @Nullable RecurringTokenConfiguration recurringConfiguration,
      @Nullable SecurityConfiguration securityConfiguration,
      @Nullable Map<CardBrand, BigDecimal> cardBrandPercentFees,
      @Nullable InstallmentsConfiguration installmentsConfiguration,
      @Nullable SubscriptionConfiguration subscriptionConfiguration,
      @Nullable Boolean platformCredentialsEnabled,
      @Nullable DescriptorProvidedConfiguration descriptorProvidedConfiguration) {
    this.percentFee = percentFee;
    this.flatFees = flatFees;
    this.logoUrl = logoUrl;
    this.country = country;
    this.language = language;
    this.minTransferPayout = minTransferPayout;
    this.maximumChargeAmounts = maximumChargeAmounts;
    this.minimumChargeAmounts = minimumChargeAmounts;
    this.transferScheduleConfiguration = transferScheduleConfiguration;
    this.timeZone = timeZone;
    this.userTransactionsConfiguration = userTransactionsConfiguration;
    this.cardConfiguration = cardConfiguration;
    this.qrScanConfiguration = qrScanConfiguration;
    this.convenienceConfiguration = convenienceConfiguration;
    this.paidyConfiguration = paidyConfiguration;
    this.qrMerchantConfiguration = qrMerchantConfiguration;
    this.onlineConfiguration = onlineConfiguration;
    this.recurringConfiguration = recurringConfiguration;
    this.securityConfiguration = securityConfiguration;
    this.cardBrandPercentFees = cardBrandPercentFees;
    this.installmentsConfiguration = installmentsConfiguration;
    this.subscriptionConfiguration = subscriptionConfiguration;
    this.platformCredentialsEnabled = platformCredentialsEnabled;
    this.descriptorProvidedConfiguration = descriptorProvidedConfiguration;
  }

  public Configuration() {}
}
