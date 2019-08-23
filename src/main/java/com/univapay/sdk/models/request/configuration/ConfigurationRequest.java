package com.univapay.sdk.models.request.configuration;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.FlatFee;
import com.univapay.sdk.models.common.InstallmentsConfiguration;
import com.univapay.sdk.models.common.KonbiniConfiguration;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.PaidyConfiguration;
import com.univapay.sdk.models.common.QrMerchantConfiguration;
import com.univapay.sdk.models.common.UserTransactionsConfiguration;
import com.univapay.sdk.models.common.stores.SecurityConfiguration;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.response.store.CardConfiguration;
import com.univapay.sdk.models.response.store.QrScanConfiguration;
import com.univapay.sdk.models.response.store.RecurringTokenConfiguration;
import com.univapay.sdk.models.response.subscription.SubscriptionConfiguration;
import org.threeten.bp.ZoneId;

public class ConfigurationRequest {

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

  @SerializedName("transfer_schedule")
  private TransferScheduleConfigurationRequest transferScheduleConfiguration;

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

  public MoneyLike getMinTransferPayout() {
    return minTransferPayout;
  }

  public List<MoneyLike> getMaximumChargeAmounts() {
    return maximumChargeAmounts;
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

  public RecurringTokenConfiguration getRecurringConfiguration() {
    return recurringConfiguration;
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

  public QrMerchantConfiguration getQrMerchantConfiguration() {
    return qrMerchantConfiguration;
  }

  public SubscriptionConfiguration getSubscriptionConfiguration() {
    return subscriptionConfiguration;
  }

  public ConfigurationRequest(
      @Nullable BigDecimal percentFee,
      @Nullable List<FlatFee> flatFees,
      @Nullable URL logoUrl,
      @Nullable Country country,
      @Nullable Locale language,
      @Nullable MoneyLike minTransferPayout,
      @Nullable List<MoneyLike> maximumChargeAmounts,
      @Nullable TransferScheduleConfigurationRequest transferScheduleConfiguration,
      @Nullable ZoneId timeZone,
      @Nullable UserTransactionsConfiguration userTransactionsConfiguration,
      @Nullable CardConfiguration cardConfiguration,
      @Nullable QrScanConfiguration qrScanConfiguration,
      @Nullable KonbiniConfiguration convenienceConfiguration,
      @Nullable PaidyConfiguration paidyConfiguration,
      @Nullable QrMerchantConfiguration qrMerchantConfiguration,
      @Nullable RecurringTokenConfiguration recurringConfiguration,
      @Nullable SecurityConfiguration securityConfiguration,
      @Nullable Map<CardBrand, BigDecimal> cardBrandPercentFees,
      @Nullable InstallmentsConfiguration installmentsConfiguration,
      @Nullable SubscriptionConfiguration subscriptionConfiguration) {
    this.percentFee = percentFee;
    this.flatFees = flatFees;
    this.logoUrl = logoUrl;
    this.country = country;
    this.language = language;
    this.minTransferPayout = minTransferPayout;
    this.maximumChargeAmounts = maximumChargeAmounts;
    this.transferScheduleConfiguration = transferScheduleConfiguration;
    this.timeZone = timeZone;
    this.userTransactionsConfiguration = userTransactionsConfiguration;
    this.cardConfiguration = cardConfiguration;
    this.qrScanConfiguration = qrScanConfiguration;
    this.convenienceConfiguration = convenienceConfiguration;
    this.paidyConfiguration = paidyConfiguration;
    this.qrMerchantConfiguration = qrMerchantConfiguration;
    this.recurringConfiguration = recurringConfiguration;
    this.securityConfiguration = securityConfiguration;
    this.cardBrandPercentFees = cardBrandPercentFees;
    this.installmentsConfiguration = installmentsConfiguration;
    this.subscriptionConfiguration = subscriptionConfiguration;
  }

  public ConfigurationRequest() {}
}
