package com.univapay.sdk.merchant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.BankTransferConfiguration.VirtualBankMatchAmount;
import com.univapay.sdk.models.response.configuration.CheckoutConfiguration;
import com.univapay.sdk.models.response.configuration.Configuration;
import com.univapay.sdk.models.response.merchant.MerchantWithConfiguration;
import com.univapay.sdk.types.*;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.types.DayOfMonth;
import com.univapay.sdk.types.DayOfWeek;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.RecurringTokenPrivilege;
import com.univapay.sdk.types.TransferPeriod;
import com.univapay.sdk.types.WeekOfMonth;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.JsonLoader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.junit.Test;

public class GetMeTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnMerchantInfo() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET", "/me", jwt, 200, JsonLoader.loadJson("responses/me/sample-me.json"));
    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    final List<CardBrand> forbiddenBrands = new ArrayList<>();
    forbiddenBrands.add(CardBrand.MAESTRO);
    forbiddenBrands.add(CardBrand.JCB);

    List<PaymentTypeName> supportedPaymentTypes = new ArrayList<>();
    supportedPaymentTypes.add(PaymentTypeName.CARD);
    supportedPaymentTypes.add(PaymentTypeName.QR_SCAN);

    final URL expectedLogoURL = new URL("http://www.store.com/some-logo.png");

    MerchantWithConfiguration response = univapay.getMe().build().dispatch();

    assertEquals(response.getMerchantId().toString(), "51b26a3e-e90e-11e6-bb73-eb35a317b43b");
    assertEquals(
        response.getVerificationDataId().toString(), "11e77594-7419-5606-937e-9756decfe262");
    assertEquals(response.getName(), "newaccount1");
    assertEquals(response.getEmail(), "new@account1.com");
    assertFalse(response.getVerified());
    assertEquals(response.getCreatedOn(), parsedDate);
    Configuration configuration = response.getConfiguration();

    assertThat(configuration.getPercentFee(), is(BigDecimal.valueOf(0.08)));
    assertThat(
        configuration.getFlatFees().get(0), is(new FlatFee(BigInteger.valueOf(3000), "JPY")));
    assertThat(configuration.getLogoUrl(), is(expectedLogoURL));
    assertThat(configuration.getCountry(), is(Country.JAPAN));
    assertThat(configuration.getLanguage(), is(Locale.GERMAN));
    assertThat(configuration.getTimeZone(), is(ZoneId.of("Asia/Tokyo")));
    assertThat(
        configuration.getMinTransferPayout(), is(new MoneyLike(BigInteger.valueOf(100000), "JPY")));
    assertThat(
        configuration.getMaximumChargeAmounts().get(0),
        is(new MoneyLike(BigInteger.valueOf(200000), "USD")));
    assertTrue(configuration.getUserTransactionsConfiguration().getEnabled());
    assertTrue(configuration.getUserTransactionsConfiguration().getNotifyCustomer());
    assertTrue(configuration.getUserTransactionsConfiguration().getNotifyOnTest());
    assertTrue(configuration.getCardConfiguration().getEnabled());
    assertTrue(configuration.getCardConfiguration().getDebitEnabled());
    assertTrue(configuration.getCardConfiguration().getPrepaidEnabled());
    assertThat(
        configuration.getCardConfiguration().getForbiddenCardBrands(),
        containsInAnyOrder(forbiddenBrands.toArray()));

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
        configuration.getCardConfiguration().getCardLimit().getDuration(), is(Period.ofDays(35)));
    assertTrue(configuration.getCardConfiguration().getAllowEmptyCvv());
    assertTrue(configuration.getCardConfiguration().getPrepaidAuthorizationEnabled());
    assertTrue(configuration.getCardConfiguration().getDebitAuthorizationEnabled());
    assertTrue(configuration.getQrScanConfiguration().getEnabled());
    assertThat(
        configuration.getQrScanConfiguration().getForbiddenQrScanGateways().get(0), is(Gateway.QQ));
    assertFalse(configuration.getConvenienceConfiguration().getEnabled());
    assertThat(
        configuration.getTransferScheduleConfiguration().getWaitPeriod(), is(Period.ofDays(7)));
    assertThat(
        configuration.getTransferScheduleConfiguration().getPeriod(), is(TransferPeriod.MONTHLY));
    assertTrue(configuration.getTransferScheduleConfiguration().getFullPeriodRequired());
    assertThat(
        configuration.getTransferScheduleConfiguration().getDayOfWeek(), is(DayOfWeek.THURSDAY));
    assertThat(
        configuration.getTransferScheduleConfiguration().getWeekOfMonth(), is(WeekOfMonth.FOURTH));
    assertThat(
        configuration.getTransferScheduleConfiguration().getDayOfMonth(), is(new DayOfMonth(27)));
    assertThat(
        configuration.getRecurringConfiguration().getRecurringType(),
        is(RecurringTokenPrivilege.BOUNDED));
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
    assertThat(configuration.getSubscriptionConfiguration().getFailedChargesToCancel(), is(3));
    assertTrue(configuration.getSubscriptionConfiguration().getSuspendOnCancel());
    assertTrue(configuration.getSubscriptionConfiguration().getEnabled());
    assertTrue(configuration.getInstallmentsConfiguration().getEnabled());
    assertThat(
        configuration.getInstallmentsConfiguration().getMinChargeAmount(),
        is(new MoneyLike(BigInteger.valueOf(1000), "jpy")));
    assertThat(configuration.getInstallmentsConfiguration().getFailedCyclesToCancel(), is(2));
    assertThat(
        configuration.getInstallmentsConfiguration().getMaxPayoutPeriod(), is(Period.ofDays(50)));
    assertTrue(configuration.getInstallmentsConfiguration().getOnlyWithProcessor());

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

    assertThat(configuration.getMinimumChargeAmounts(), hasSize(2));
    assertThat(
        configuration.getMinimumChargeAmounts(),
        containsInAnyOrder(
            new MoneyLike(BigInteger.valueOf(100), "USD"),
            new MoneyLike(BigInteger.valueOf(0), "JPY")));

    OnlineConfiguration onlineConfiguration = configuration.getOnlineConfiguration();
    assertThat(onlineConfiguration.getEnabled(), is(true));

    BankTransferConfiguration bankTransferConfiguration =
        configuration.getBankTransferConfiguration();

    assertThat(
        bankTransferConfiguration,
        allOf(
            hasProperty("enabled", is(true)),
            hasProperty("matchAmount", is(VirtualBankMatchAmount.Exact)),
            hasProperty("expirationPeriod", is(Period.ofDays(7)))));

    CheckoutConfiguration checkoutConfiguration = configuration.getCheckoutConfiguration();
    assertThat(checkoutConfiguration.getEcMail(), allOf(hasProperty("enabled", is(true))));

    assertThat(checkoutConfiguration.getEcProducts(), allOf(hasProperty("enabled", is(true))));

    assertTrue(configuration.getTaggedPlatformCredentialsEnabled());

    DescriptorProvidedConfiguration descriptorProvidedConfiguration =
        configuration.getDescriptorProvidedConfiguration();
    assertThat(descriptorProvidedConfiguration.getName(), is("DescriptorName"));
    assertThat(descriptorProvidedConfiguration.getPhoneNumber(), is("0000-0000"));
  }
}
