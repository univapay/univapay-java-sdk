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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

  // Payment Configuration related objects

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

  @SerializedName("bank_transfer_configuration")
  private BankTransferConfiguration bankTransferConfiguration;

  //  End Payment Configuration related objects

  @SerializedName("recurring_token_configuration")
  private RecurringTokenConfiguration recurringConfiguration;

  @SerializedName("security_configuration")
  private SecurityConfiguration securityConfiguration;

  @SerializedName("installments_configuration")
  private InstallmentsConfiguration installmentsConfiguration;

  @SerializedName("checkout_configuration")
  private CheckoutConfiguration checkoutConfiguration;

  @SerializedName("card_brand_percent_fees")
  private Map<CardBrand, BigDecimal> cardBrandPercentFees;

  @SerializedName("subscription_configuration")
  private SubscriptionConfiguration subscriptionConfiguration;

  @SerializedName("platform_credentials_enabled")
  private Boolean platformCredentialsEnabled;

  @SerializedName("tagged_platform_credentials_enabled")
  private Boolean taggedPlatformCredentialsEnabled;

  @SerializedName("descriptor_provided_configuration")
  private DescriptorProvidedConfiguration descriptorProvidedConfiguration;
}
