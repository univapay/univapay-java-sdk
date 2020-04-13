package com.univapay.sdk.merchant;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.FlatFee;
import com.univapay.sdk.models.common.MoneyLike;
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
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.MerchantsFakeRR;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.time.Duration;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.hamcrest.core.Is;
import org.joda.time.Period;
import org.junit.Test;

public class GetMeTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnMerchantInfo() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET", "/me", token, 200, MerchantsFakeRR.getMeFakeResponse);
    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final java.util.Date parsedDate =
        dateParser.parseDateTime("2017-06-22T16:00:55.436116+09:00").toDate();

    final List forbiddenBrands = new ArrayList<CardBrand>();
    forbiddenBrands.add(CardBrand.DINERS_CLUB);
    forbiddenBrands.add(CardBrand.MASTERCARD);
    forbiddenBrands.add(CardBrand.JCB);

    final URL expectedLogoURL = new URL("http://www.store.com/some-logo.png");

    univapay
        .getMe()
        .build()
        .dispatch(
            new UnivapayCallback<MerchantWithConfiguration>() {
              @Override
              public void getResponse(MerchantWithConfiguration response) {
                assertEquals(
                    response.getMerchantId().toString(), "51b26a3e-e90e-11e6-bb73-eb35a317b43b");
                assertEquals(
                    response.getVerificationDataId().toString(),
                    "11e77594-7419-5606-937e-9756decfe262");
                assertEquals(response.getName(), "newaccount1");
                assertEquals(response.getEmail(), "new@account1.com");
                assertFalse(response.getVerified());
                assertEquals(response.getCreatedOn(), parsedDate);
                Configuration configuration = response.getConfiguration();

                assertThat(configuration.getPercentFee(), is(BigDecimal.valueOf(0.08)));
                assertThat(
                    configuration.getFlatFees().get(0),
                    is(new FlatFee(BigInteger.valueOf(3000), "JPY")));
                assertThat(configuration.getLogoUrl(), is(expectedLogoURL));
                assertThat(configuration.getCountryEnum(), Is.is(Country.JAPAN));
                assertThat(configuration.getLanguage(), is(Locale.GERMAN));
                assertThat(configuration.getTimeZone(), is(ZoneId.of("Asia/Tokyo")));
                assertThat(
                    configuration.getMinTransferPayout(),
                    is(new MoneyLike(BigInteger.valueOf(100000), "JPY")));
                assertThat(
                    configuration.getMaximumChargeAmounts().get(0),
                    is(new MoneyLike(BigInteger.valueOf(200000), "USD")));
                assertTrue(configuration.getUserTransactionsConfiguration().getEnabled());
                assertTrue(configuration.getUserTransactionsConfiguration().getNotifyCustomer());
                assertTrue(configuration.getCardConfiguration().getEnabled());
                assertTrue(configuration.getCardConfiguration().getDebitEnabled());
                assertTrue(configuration.getCardConfiguration().getPrepaidEnabled());
                assertThat(
                    configuration.getCardConfiguration().getForbiddenCardBrands().get(0),
                    is(CardBrand.JCB));
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
                    configuration.getCardConfiguration().getAllowedCountriesByIp().get(2),
                    is(Country.FIJI));
                assertFalse(configuration.getCardConfiguration().getForeignCardsAllowed());
                assertFalse(configuration.getCardConfiguration().getFailOnNewEmail());
                assertThat(
                    configuration.getCardConfiguration().getCardLimit().getAmount(),
                    is(BigInteger.valueOf(100000)));
                assertThat(
                    configuration.getCardConfiguration().getCardLimit().getCurrency(), is("jpy"));
                assertThat(
                    configuration.getCardConfiguration().getCardLimit().getAmountFormatted(),
                    is(BigDecimal.valueOf(100000)));
                assertThat(
                    configuration.getCardConfiguration().getCardLimit().getDuration(),
                    is(Period.days(35)));
                assertTrue(configuration.getCardConfiguration().getAllowEmptyCvv());
                assertTrue(configuration.getQrScanConfiguration().getEnabled());
                assertThat(
                    configuration.getQrScanConfiguration().getForbiddenQrScanGateways().get(0),
                    Is.is(Gateway.QQ));
                assertFalse(configuration.getConvenienceConfiguration().getEnabled());
                assertThat(
                    configuration.getTransferScheduleConfiguration().getWaitPeriod(),
                    is(Period.days(7)));
                assertThat(
                    configuration.getTransferScheduleConfiguration().getPeriod(),
                    Is.is(TransferPeriod.MONTHLY));
                assertTrue(
                    configuration.getTransferScheduleConfiguration().getFullPeriodRequired());
                assertThat(
                    configuration.getTransferScheduleConfiguration().getDayOfWeek(),
                    Is.is(DayOfWeek.THURSDAY));
                assertThat(
                    configuration.getTransferScheduleConfiguration().getWeekOfMonth(),
                    Is.is(WeekOfMonth.FOURTH));
                assertThat(
                    configuration.getTransferScheduleConfiguration().getDayOfMonth(),
                    is(new DayOfMonth(27)));
                assertThat(
                    configuration.getRecurringConfiguration().getRecurringType(),
                    Is.is(RecurringTokenPrivilege.BOUNDED));
                assertThat(
                    configuration.getRecurringConfiguration().getChargeWaitPeriod(),
                    is(Period.hours(72)));
                assertThat(
                    configuration.getRecurringConfiguration().getRecurringType(),
                    is(RecurringTokenPrivilege.BOUNDED));
                assertTrue(
                    configuration
                        .getRecurringConfiguration()
                        .getRecurringTokenCVVConfirmation()
                        .getEnabled());
                assertThat(
                    configuration
                        .getRecurringConfiguration()
                        .getRecurringTokenCVVConfirmation()
                        .getThreshold(),
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
                assertThat(
                    configuration.getSubscriptionConfiguration().getFailedChargesToCancel(), is(3));
                assertTrue(configuration.getSubscriptionConfiguration().getSuspendOnCancel());
                assertTrue(configuration.getInstallmentsConfiguration().getEnabled());
                assertThat(
                    configuration.getInstallmentsConfiguration().getMinChargeAmount(),
                    is(new MoneyLike(BigInteger.valueOf(1000), "jpy")));
                assertThat(
                    configuration.getInstallmentsConfiguration().getFailedCyclesToCancel(), is(2));
                assertThat(
                    configuration.getInstallmentsConfiguration().getMaxPayoutPeriod(),
                    is(Period.days(50)));
                assertTrue(configuration.getInstallmentsConfiguration().getOnlyWithProcessor());
                List<PaymentTypeName> supportedPaymentTypes = new ArrayList();
                supportedPaymentTypes.add(PaymentTypeName.CARD);
                supportedPaymentTypes.add(PaymentTypeName.QR_SCAN);
                assertThat(
                    configuration.getInstallmentsConfiguration().getSupportedPaymentTypes(),
                    is(supportedPaymentTypes));
                assertThat(
                    configuration.getCardBrandPercentFees().get(CardBrand.VISA),
                    is(BigDecimal.valueOf(0.025)));
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
                    configuration.getCardBrandPercentFees().get(CardBrand.JCB),
                    is(BigDecimal.valueOf(0.05)));
                assertThat(
                    configuration.getCardBrandPercentFees().get(CardBrand.DINERS_CLUB),
                    is(BigDecimal.valueOf(0.055)));
                assertThat(
                    configuration.getCardBrandPercentFees().get(CardBrand.UNIONPAY),
                    is(BigDecimal.valueOf(0.06)));
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                fail(error.getMessage());
                notifyCall();
              }
            });

    waitCall();
  }
}
