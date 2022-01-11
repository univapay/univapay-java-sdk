package com.univapay.sdk.models.response.store;

import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.stores.SecurityConfiguration;
import com.univapay.sdk.models.response.configuration.CheckoutConfiguration;
import com.univapay.sdk.models.response.configuration.Configuration;
import com.univapay.sdk.models.response.subscription.SubscriptionConfiguration;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import java.math.BigDecimal;
import java.net.URL;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class StoreConfiguration extends Configuration {

  protected StoreConfiguration(
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
      @Nullable BankTransferConfiguration bankTransferConfiguration,
      @Nullable RecurringTokenConfiguration recurringConfiguration,
      @Nullable SecurityConfiguration securityConfiguration,
      @Nullable InstallmentsConfiguration installmentsConfiguration,
      @Nullable CheckoutConfiguration checkoutConfiguration,
      @Nullable Map<CardBrand, BigDecimal> cardBrandPercentFees,
      @Nullable SubscriptionConfiguration subscriptionConfiguration,
      @Nullable Boolean platformCredentialsEnabled,
      @Nullable Boolean taggedPlatformCredentialsEnabled,
      @Nullable DescriptorProvidedConfiguration descriptorProvidedConfiguration) {
    super(
        percentFee,
        flatFees,
        logoUrl,
        country,
        language,
        minTransferPayout,
        maximumChargeAmounts,
        minimumChargeAmounts,
        transferScheduleConfiguration,
        timeZone,
        userTransactionsConfiguration,
        cardConfiguration,
        qrScanConfiguration,
        convenienceConfiguration,
        paidyConfiguration,
        qrMerchantConfiguration,
        onlineConfiguration,
        bankTransferConfiguration,
        recurringConfiguration,
        securityConfiguration,
        installmentsConfiguration,
        checkoutConfiguration,
        cardBrandPercentFees,
        subscriptionConfiguration,
        platformCredentialsEnabled,
        taggedPlatformCredentialsEnabled,
        descriptorProvidedConfiguration);
  }
}
