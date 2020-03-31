package com.univapay.sdk.configuration;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import com.google.gson.Gson;
import com.univapay.sdk.models.common.FlatFee;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.response.configuration.Configuration;
import com.univapay.sdk.types.*;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.types.DayOfMonth;
import com.univapay.sdk.types.DayOfWeek;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.RecurringTokenPrivilege;
import com.univapay.sdk.types.TransferPeriod;
import com.univapay.sdk.types.WeekOfMonth;
import com.univapay.sdk.utils.RequestUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.hamcrest.core.Is;
import org.joda.time.Period;
import org.junit.Test;
import org.threeten.bp.Duration;
import org.threeten.bp.ZoneId;

public class ConfigurationTest {

  @Test
  public void shouldParseConfigurationDataProperly() throws Exception {

    final String json =
        ""
            + "    {\n"
            + "        \"percent_fee\": 0.08,\n"
            + "        \"flat_fees\": [{\"amount\":3000, \"currency\":\"jpy\"}],\n"
            + "        \"logo_url\" : \"http://www.store.com/some-logo.png\",\n"
            + "        \"country\":  \"JP\",\n"
            + "        \"language\": \"de\",\n"
            + "        \"display_time_zone\": \"Asia/Tokyo\",\n"
            + "        \"min_transfer_payout\": {"
            + "           \"amount\": 100000,\n"
            + "           \"currency\": \"JPY\"\n"
            + "        },\n"
            + "        \"maximum_charge_amounts\": [{\"amount\":200000, \"currency\":\"USD\"}],\n"
            + "        \"user_transactions_configuration\": {\n"
            + "            \"enabled\": true,\n"
            + "            \"notify_customer\": true\n"
            + "        },\n"
            + "        \"card_configuration\": {\n"
            + "            \"enabled\": true,\n"
            + "            \"debit_enabled\": true,\n"
            + "            \"prepaid_enabled\": true,\n"
            + "            \"forbidden_card_brands\": [ \"jcb\", \"maestro\" ],\n"
            + "            \"allowed_countries_by_ip\": [ \"AS\", \"AR\", \"FJ\" ],\n"
            + "            \"foreign_cards_allowed\": false,\n"
            + "            \"fail_on_new_email\": false,\n"
            + "            \"card_limit\": {\"amount\":100000,\"currency\":\"jpy\",\"amount_formatted\":\"100000\",\"duration\":\"P35D\"},\n"
            + "            \"allow_empty_cvv\": true\n"
            + "        },\n"
            + "        \"qr_scan_configuration\": {\n"
            + "            \"enabled\": true,\n"
            + "            \"forbidden_qr_scan_gateways\" : [ \"qq\" ]\n"
            + "        },\n"
            + "        \"convenience_configuration\": {\n"
            + "            \"enabled\": false\n"
            + "        },\n"
            + "        \"paidy_configuration\": {\n"
            + "          \"enabled\": true\n"
            + "        },"
            + "       \"transfer_schedule\": {\n"
            + "            \"wait_period\": \"P7D\",\n"
            + "            \"period\": \"monthly\",\n"
            + "            \"full_period_required\": true,\n"
            + "            \"day_of_week\": \"thursday\",\n"
            + "            \"week_of_month\": \"fourth\",\n"
            + "            \"day_of_month\": 27,\n"
            + "            \"weekly_closing_day\": null,\n"
            + "            \"weekly_payout_day\": null\n"
            + "        },"
            + "        \"recurring_token_configuration\": {\n"
            + "            \"recurring_type\": \"bounded\",\n"
            + "            \"charge_wait_period\": \"PT72H\",\n"
            + "            \"card_charge_cvv_confirmation\": {\n"
            + "                \"enabled\": true,\n"
            + "                \"threshold\": [\n"
            + "                    {\n"
            + "                        \"amount\": 10000,\n"
            + "                        \"currency\": \"JPY\"\n"
            + "                    }\n"
            + "                ]\n"
            + "            }\n"
            + "        },\n"
            + "        \"security_configuration\": {\n"
            + "            \"inspect_suspicious_login_after\": \"P20D\",\n"
            + "            \"refund_percent_limit\": 0.75,\n"
            + "            \"limit_charge_by_card_configuration\": {\"quantity_of_charges\":30, \"duration_window\":\"PT2H\"},\n"
            + "            \"confirmation_required\": true\n"
            + "        },\n"
            + "        \"subscription_configuration\": {\n"
            + "            \"failed_charges_to_cancel\": 3,\n"
            + "            \"suspend_on_cancel\": true\n"
            + "        },\n"
            + "        \"installments_configuration\": {\n"
            + "            \"enabled\": true,\n"
            + "            \"failed_cycles_to_cancel\": 2,\n"
            + "            \"min_charge_amount\": {"
            + "            \"amount\": 1000,"
            + "            \"currency\": \"jpy\""
            + "       },\n"
            + "            \"max_payout_period\": \"P50D\",\n"
            + "            \"only_with_processor\": true,\n"
            + "            \"supported_payment_types\": [\"card\", \"qr_scan\"]\n"
            + "        },\n"
            + "        \"card_brand_percent_fees\": {\n"
            + "            \"visa\": 0.025,\n"
            + "            \"american_express\": 0.03,\n"
            + "            \"mastercard\": 0.035,\n"
            + "            \"maestro\": 0.04,\n"
            + "            \"discover\": 0.045,\n"
            + "            \"jcb\": 0.05,\n"
            + "            \"diners_club\": 0.055,\n"
            + "            \"unionpay\": 0.06\n"
            + "        }\n"
            + "    }";

    final Gson gson = RequestUtils.getGson();

    Configuration configuration = gson.fromJson(json, Configuration.class);

    assertThat(configuration.getPercentFee(), is(BigDecimal.valueOf(0.08)));
    assertThat(
        configuration.getFlatFees().get(0), is(new FlatFee(BigInteger.valueOf(3000), "JPY")));
    assertThat(configuration.getLogoUrl(), is(new URL("http://www.store.com/some-logo.png")));
    assertThat(configuration.getCountryEnum(), Is.is(Country.JAPAN));
    assertThat(configuration.getLanguage(), is(Locale.GERMAN));
    assertThat(configuration.getTimeZone(), is(ZoneId.of("Asia/Tokyo")));
    assertThat(
        configuration.getMinTransferPayout(), is(new MoneyLike(BigInteger.valueOf(100000), "JPY")));
    assertThat(
        configuration.getMaximumChargeAmounts().get(0),
        is(new MoneyLike(BigInteger.valueOf(200000), "USD")));
    assertTrue(configuration.getUserTransactionsConfiguration().getEnabled());
    assertTrue(configuration.getUserTransactionsConfiguration().getNotifyCustomer());
    assertTrue(configuration.getCardConfiguration().getEnabled());
    assertTrue(configuration.getCardConfiguration().getDebitEnabled());
    assertTrue(configuration.getCardConfiguration().getPrepaidEnabled());
    assertThat(
        configuration.getCardConfiguration().getForbiddenCardBrands().get(0), Is.is(CardBrand.JCB));
    assertThat(
        configuration.getCardConfiguration().getForbiddenCardBrands().get(1),
        is(CardBrand.MAESTRO));
    assertThat(
        configuration.getCardConfiguration().getAllowedCountriesByIp().get(0),
        is(Country.AMERICAN_SAMOA));
    assertThat(
        configuration.getCardConfiguration().getAllowedCountriesByIp().get(1),
        is(Country.ARGENTINA));
    assertThat(
        configuration.getCardConfiguration().getAllowedCountriesByIp().get(2), is(Country.FIJI));
    assertFalse(configuration.getCardConfiguration().getForeignCardsAllowed());
    assertFalse(configuration.getCardConfiguration().getFailOnNewEmail());
    assertThat(
        configuration.getCardConfiguration().getCardLimit().getAmount(),
        is(BigInteger.valueOf(100000)));
    assertThat(configuration.getCardConfiguration().getCardLimit().getCurrency(), is("jpy"));
    assertThat(
        configuration.getCardConfiguration().getCardLimit().getAmountFormatted(),
        is(BigDecimal.valueOf(100000)));
    assertThat(
        configuration.getCardConfiguration().getCardLimit().getDuration(), is(Period.days(35)));
    assertTrue(configuration.getCardConfiguration().getAllowEmptyCvv());
    assertTrue(configuration.getQrScanConfiguration().getEnabled());
    assertThat(
        configuration.getQrScanConfiguration().getForbiddenQrScanGateways().get(0),
        Is.is(Gateway.QQ));
    assertFalse(configuration.getConvenienceConfiguration().getEnabled());
    assertThat(configuration.getPaidyConfiguration().getEnabled(), is(true));
    assertThat(
        configuration.getTransferScheduleConfiguration().getWaitPeriod(), is(Period.days(7)));
    assertThat(
        configuration.getTransferScheduleConfiguration().getPeriod(),
        Is.is(TransferPeriod.MONTHLY));
    assertTrue(configuration.getTransferScheduleConfiguration().getFullPeriodRequired());
    assertThat(
        configuration.getTransferScheduleConfiguration().getDayOfWeek(), Is.is(DayOfWeek.THURSDAY));
    assertThat(
        configuration.getTransferScheduleConfiguration().getWeekOfMonth(),
        Is.is(WeekOfMonth.FOURTH));
    assertThat(
        configuration.getTransferScheduleConfiguration().getDayOfMonth(), is(new DayOfMonth(27)));
    assertThat(
        configuration.getRecurringConfiguration().getRecurringType(),
        Is.is(RecurringTokenPrivilege.BOUNDED));
    assertThat(
        configuration.getRecurringConfiguration().getChargeWaitPeriod(), is(Period.hours(72)));
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
        is(Period.days(20)));
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
        is(new MoneyLike(BigInteger.valueOf(1000), "jpy")));
    assertThat(configuration.getInstallmentsConfiguration().getFailedCyclesToCancel(), is(2));
    assertThat(
        configuration.getInstallmentsConfiguration().getMaxPayoutPeriod(), is(Period.days(50)));
    assertTrue(configuration.getInstallmentsConfiguration().getOnlyWithProcessor());
    List<PaymentTypeName> supportedPaymentTypes = new ArrayList();
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
  }
}
