package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.models.common.FlatFee;
import com.univapay.sdk.models.common.InstallmentsConfiguration;
import com.univapay.sdk.models.common.KonbiniConfiguration;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.PaidyConfiguration;
import com.univapay.sdk.models.common.QrMerchantConfiguration;
import com.univapay.sdk.models.common.TransferScheduleConfiguration;
import com.univapay.sdk.models.common.UserTransactionsConfiguration;
import com.univapay.sdk.models.common.stores.SecurityConfiguration;
import com.univapay.sdk.models.response.store.CardConfiguration;
import com.univapay.sdk.models.response.store.QrScanConfiguration;
import com.univapay.sdk.models.response.store.RecurringTokenConfiguration;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.response.configuration.Configuration;
import com.univapay.sdk.models.response.subscription.SubscriptionConfiguration;
import org.threeten.bp.ZoneId;

public class MerchantConfiguration extends Configuration {

  public MerchantConfiguration(
      @Nullable BigDecimal percentFee,
      @Nullable List<FlatFee> flatFees,
      @Nullable URL logoUrl,
      @Nullable Country country,
      @Nullable Locale language,
      @Nullable MoneyLike minTransferPayout,
      @Nullable List<MoneyLike> maximumChargeAmounts,
      @Nullable TransferScheduleConfiguration transferScheduleConfiguration,
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
      @Nullable SubscriptionConfiguration subscriptionSettings) {
    super(
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
        subscriptionSettings);
  }
}
