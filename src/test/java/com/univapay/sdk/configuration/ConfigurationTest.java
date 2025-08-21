package com.univapay.sdk.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.request.configuration.PreconfiguredMonthlySchedule;
import com.univapay.sdk.models.request.configuration.PreconfiguredSemimonthlySchedule;
import com.univapay.sdk.models.request.configuration.PreconfiguredWeeklySchedule;
import com.univapay.sdk.models.response.configuration.Configuration;
import com.univapay.sdk.models.response.store.CardConfiguration;
import com.univapay.sdk.types.*;
import com.univapay.sdk.utils.RetrofitBuilder;
import com.univapay.sdk.utils.mockcontent.JsonLoader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.time.Duration;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class ConfigurationTest {

  private final RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

  @Test
  void shouldWritePreconfiguredTransferScheduleProperly() throws Exception {
    final Gson gson = retrofitBuilder.getGson();

    PreconfiguredMonthlySchedule monthly = new PreconfiguredMonthlySchedule(new DayOfMonth(5));
    String monthlyString = gson.toJson(monthly);
    assertThat(monthlyString, is("{\"monthly\":{\"day_of_month\":5}}"));

    PreconfiguredWeeklySchedule weekly =
        new PreconfiguredWeeklySchedule(DayOfWeek.FRIDAY, DayOfWeek.MONDAY);
    String weeklyString = gson.toJson(weekly);
    assertThat(
        weeklyString, is("{\"weekly\":{\"closing_day\":\"friday\",\"payout_day\":\"monday\"}}"));

    PreconfiguredSemimonthlySchedule semimonthlySchedule = new PreconfiguredSemimonthlySchedule();
    String semimonthlyScheduleString = gson.toJson(semimonthlySchedule);
    assertThat(semimonthlyScheduleString, is("{\"semimonthly\":{}}"));
  }

  @Test
  void shouldParseConfigurationDataProperly() throws Exception {

    final String json = JsonLoader.loadJson("responses/configuration/full-configuration.json");

    final Gson gson = retrofitBuilder.getGson();

    Configuration configuration = gson.fromJson(json, Configuration.class);

    assertThat(configuration.getPercentFee(), is(BigDecimal.valueOf(0.08)));
    assertThat(
        configuration.getFlatFees().get(0), is(new FlatFee(BigInteger.valueOf(3000), "JPY")));
    assertThat(configuration.getLogoUrl(), is(new URL("http://www.store.com/some-logo.png")));
    assertThat(configuration.getCountry(), Matchers.is(Country.JAPAN));
    assertThat(configuration.getLanguage(), is(Locale.GERMAN));
    assertThat(configuration.getTimeZone(), is(ZoneId.of("Asia/Tokyo")));
    assertThat(
        configuration.getMinTransferPayout(), is(new MoneyLike(BigInteger.valueOf(100000), "JPY")));

    assertEquals(1, configuration.getMaximumChargeAmounts().size());

    assertThat(
        configuration.getMaximumChargeAmounts(),
        containsInAnyOrder(new MoneyLike(BigInteger.valueOf(200000), "USD")));

    assertTrue(configuration.getUserTransactionsConfiguration().getEnabled());
    assertTrue(configuration.getUserTransactionsConfiguration().getNotifyCustomer());
    CardConfiguration cardConfiguration = configuration.getCardConfiguration();
    assertTrue(cardConfiguration.getEnabled());
    assertTrue(cardConfiguration.getDebitEnabled());
    assertTrue(cardConfiguration.getPrepaidEnabled());
    assertTrue(cardConfiguration.getOnlyDirectCurrency());
    assertThat(cardConfiguration.getForbiddenCardBrands().get(0), Matchers.is(CardBrand.JCB));
    assertThat(cardConfiguration.getForbiddenCardBrands().get(1), is(CardBrand.MAESTRO));
    assertThat(cardConfiguration.getAllowedCountriesByIp().get(0), is(Country.AMERICAN_SAMOA));
    assertThat(cardConfiguration.getAllowedCountriesByIp().get(1), is(Country.ARGENTINA));
    assertThat(cardConfiguration.getAllowedCountriesByIp().get(2), is(Country.FIJI));
    assertFalse(cardConfiguration.getForeignCardsAllowed());
    assertFalse(cardConfiguration.getFailOnNewEmail());
    assertThat(cardConfiguration.getCardLimit().getAmount(), is(BigInteger.valueOf(100000)));
    assertThat(cardConfiguration.getCardLimit().getCurrency(), is("jpy"));
    assertThat(
        cardConfiguration.getCardLimit().getAmountFormatted(), is(BigDecimal.valueOf(100000)));
    assertThat(cardConfiguration.getCardLimit().getDuration(), is(Period.ofDays(35)));
    assertTrue(cardConfiguration.getAllowEmptyCvv());

    assertTrue(configuration.getQrScanConfiguration().getEnabled());
    assertThat(
        configuration.getQrScanConfiguration().getForbiddenQrScanGateways().get(0),
        Matchers.is(Gateway.QQ));
    assertFalse(configuration.getConvenienceConfiguration().getEnabled());
    assertThat(configuration.getPaidyConfiguration().getEnabled(), is(true));
    assertThat(
        configuration.getTransferScheduleConfiguration().getWaitPeriod(), is(Period.ofDays(7)));
    assertThat(
        configuration.getTransferScheduleConfiguration().getPeriod(),
        Matchers.is(TransferPeriod.MONTHLY));
    assertTrue(configuration.getTransferScheduleConfiguration().getFullPeriodRequired());
    assertThat(
        configuration.getTransferScheduleConfiguration().getDayOfWeek(),
        Matchers.is(DayOfWeek.THURSDAY));
    assertThat(
        configuration.getTransferScheduleConfiguration().getWeekOfMonth(),
        Matchers.is(WeekOfMonth.FOURTH));
    assertThat(
        configuration.getTransferScheduleConfiguration().getDayOfMonth(), is(new DayOfMonth(27)));
    assertThat(
        configuration.getRecurringConfiguration().getRecurringType(),
        Matchers.is(RecurringTokenPrivilege.BOUNDED));
    assertThat(
        configuration.getRecurringConfiguration().getChargeWaitPeriod(), is(Duration.ofHours(72)));
    assertThat(
        configuration.getRecurringConfiguration().getRecurringType(),
        is(RecurringTokenPrivilege.BOUNDED));
    assertTrue(
        configuration.getRecurringConfiguration().getRecurringTokenCVVConfirmation().getEnabled());
    assertThat(
        configuration.getRecurringConfiguration().getRecurringTokenCVVConfirmation().getThreshold(),
        is(Collections.singletonList(new MoneyLike(BigInteger.valueOf(10000), "jpy"))));
    assertThat(
        configuration.getSecurityConfiguration().getInspectSuspiciousLoginAfter(),
        is(Period.ofDays(20)));
    assertThat(
        configuration.getSecurityConfiguration().getRefundPercentLimit(),
        is(BigDecimal.valueOf(0.75)));
    assertThat(
        configuration
            .getSecurityConfiguration()
            .getLimitChargeByCardConfiguration()
            .getDurationWindow(),
        is(Duration.ofHours(2)));
    assertThat(
        configuration
            .getSecurityConfiguration()
            .getLimitChargeByCardConfiguration()
            .getQuantityOfCharges(),
        is(30));
    assertThat(configuration.getSecurityConfiguration().getConfirmationRequired(), is(true));
    assertThat(configuration.getSubscriptionConfiguration().getFailedChargesToCancel(), is(3));
    assertTrue(configuration.getSubscriptionConfiguration().getSuspendOnCancel());
    assertTrue(configuration.getInstallmentsConfiguration().getEnabled());
    assertThat(
        configuration.getInstallmentsConfiguration().getMinChargeAmount(),
        is(
            InstallmentsConfiguration.InstallmentsMinChargeAmount.builder()
                .minChargeAmount(BigInteger.valueOf(1000))
                .minChargeCurrency("jpy")
                .build()));
    assertThat(
        configuration.getInstallmentsConfiguration().getMaxPayoutPeriod(), is(Period.ofDays(50)));
    assertTrue(configuration.getInstallmentsConfiguration().getOnlyWithProcessor());
    List<PaymentTypeName> supportedPaymentTypes = new ArrayList<>();
    supportedPaymentTypes.add(PaymentTypeName.CARD);
    supportedPaymentTypes.add(PaymentTypeName.QR_SCAN);
    assertThat(
        configuration.getInstallmentsConfiguration().getSupportedPaymentTypes(),
        is(supportedPaymentTypes));
    assertThat(
        configuration.getCardBrandPercentFees().get(CardBrand.VISA), is(BigDecimal.valueOf(0.025)));
    assertThat(
        configuration.getCardBrandPercentFees().get(CardBrand.AMERICAN_EXPRESS),
        is(BigDecimal.valueOf(0.03)));
    assertThat(
        configuration.getCardBrandPercentFees().get(CardBrand.MASTERCARD),
        is(BigDecimal.valueOf(0.035)));
    assertThat(
        configuration.getCardBrandPercentFees().get(CardBrand.MAESTRO),
        is(BigDecimal.valueOf(0.04)));
    assertThat(
        configuration.getCardBrandPercentFees().get(CardBrand.DISCOVER),
        is(BigDecimal.valueOf(0.045)));
    assertThat(
        configuration.getCardBrandPercentFees().get(CardBrand.JCB), is(BigDecimal.valueOf(0.05)));
    assertThat(
        configuration.getCardBrandPercentFees().get(CardBrand.DINERS_CLUB),
        is(BigDecimal.valueOf(0.055)));
    assertThat(
        configuration.getCardBrandPercentFees().get(CardBrand.UNIONPAY),
        is(BigDecimal.valueOf(0.06)));

    assertThat(configuration.getPlatformCredentialsEnabled(), is(false));

    assertEquals(2, configuration.getMinimumChargeAmounts().size());
    assertThat(
        configuration.getMinimumChargeAmounts(),
        containsInAnyOrder(
            new MoneyLike(BigInteger.valueOf(100), "USD"),
            new MoneyLike(BigInteger.valueOf(0), "JPY")));

    OnlineConfiguration onlineConfiguration = configuration.getOnlineConfiguration();
    assertThat(onlineConfiguration.getEnabled(), is(true));

    DescriptorProvidedConfiguration descriptorProvidedConfiguration =
        configuration.getDescriptorProvidedConfiguration();
    assertThat(descriptorProvidedConfiguration.getName(), is("DescriptorName"));
    assertThat(descriptorProvidedConfiguration.getPhoneNumber(), is("0000-0000"));
  }
}
