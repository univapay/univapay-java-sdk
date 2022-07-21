package com.univapay.sdk.store;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.stores.SecurityConfiguration;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.store.QrScanConfiguration;
import com.univapay.sdk.models.response.store.RecurringTokenConfiguration;
import com.univapay.sdk.models.response.store.StoreWithConfiguration;
import com.univapay.sdk.types.*;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.builders.CardConfigurationBuilder;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import org.hamcrest.Matcher;
import org.junit.Test;

public class UpdateStoreTest extends GenericTest {

  final OffsetDateTime expectedCreatedOn = parseDate("2017-06-22T16:00:55.436116+09:00");

  @Test
  public void shouldPostAndReturnUpdatedStoreInfo()
      throws InterruptedException, IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/stores/11e751a6-15b1-169c-8d58-47c3d241a399",
        jwt,
        200,
        StoreFakeRR.updateStoreFakeResponse,
        StoreFakeRR.updateStoreFakeRequest);

    UnivapaySDK sdk = createTestInstance(AuthType.JWT);

    final List<CardBrand> forbiddenCardBrands = new ArrayList<>();
    forbiddenCardBrands.add(CardBrand.JCB);
    forbiddenCardBrands.add(CardBrand.MAESTRO);

    final List<Country> allowedCountriesByIp = new ArrayList<>();
    allowedCountriesByIp.add(Country.AMERICAN_SAMOA);
    allowedCountriesByIp.add(Country.ARGENTINA);
    allowedCountriesByIp.add(Country.FIJI);

    final Map<CardBrand, BigDecimal> percentFees = new HashMap<>();
    percentFees.put(CardBrand.VISA, BigDecimal.valueOf(0.025));

    final List<Gateway> forbiddenQrScanGateways = new ArrayList<>();
    forbiddenQrScanGateways.add(Gateway.QQ);

    final URL logoUrl = new URL("http://www.store.com/some-logo.png");
    final ZoneId timeZone = ZoneId.of("Asia/Tokyo");
    final Country country = Country.JAPAN;

    StoreWithConfiguration response =
        sdk.updateStore(new StoreId("11e751a6-15b1-169c-8d58-47c3d241a399"))
            .withName("Modified Store")
            .withCardConfiguration(
                new CardConfigurationBuilder()
                    .withEnabled(true)
                    .withPrepaidEnabled(true)
                    .withDebitEnabled(true)
                    .withForbiddenCardBrands(forbiddenCardBrands)
                    .withFailOnNewEmail(false)
                    .withAllowedCountriesByIp(allowedCountriesByIp)
                    .withForeignCardsAllowed(false)
                    .withOnlyDirectCurrency(true)
                    .build())
            .withSecurityConfiguration(
                SecurityConfiguration.builder()
                    .inspectSuspiciousLoginAfter(Period.ofDays(20))
                    .build())
            .withCardBrandPercentFees(percentFees)
            .withRecurringTokenConfiguration(
                new RecurringTokenConfiguration(
                    RecurringTokenPrivilege.BOUNDED,
                    Duration.ofDays(10),
                    new RecurringTokenCVVConfirmation(
                        true,
                        Collections.singletonList(
                            new MoneyLike(BigInteger.valueOf(10000), "JPY")))))
            .withLanguage(Locale.GERMAN)
            .withCountry(country)
            .withLogoUrl(logoUrl)
            .withTimeZone(timeZone)
            .withUserTransactionsConfiguration(
                UserTransactionsConfiguration.builder()
                    .enabled(true)
                    .notifyOnTest(true)
                    .notifyCustomer(true)
                    .notifyOnRecurringTokenCreation(true)
                    .build())
            .withQrScanConfiguration(new QrScanConfiguration(true, forbiddenQrScanGateways))
            .withConvenienceConfiguration(
                new KonbiniConfiguration(
                    false, Duration.ofDays(31), KonbiniExpirationTimeShift.builder().build()))
            .withPaidyConfiguration(new PaidyConfiguration(true))
            .withQrMerchantConfiguration(new QrMerchantConfiguration(false))
            .withOnlineConfiguration(new OnlineConfiguration(false))
            .withBankTransferConfiguration(
                BankTransferConfiguration.builder()
                    .enabled(false)
                    .matchAmount(VirtualBankMatchAmount.Exact)
                    .expirationPeriod(Duration.ofDays(7))
                    .expirationTimeShift(
                        BankTransferExpirationTimeShift.builder().enabled(null).build())
                    .virtualBankAccountThreshold(3)
                    .virtualBankAccountFetchCount(5)
                    .defaultExtensionPeriod(Duration.ofDays(7))
                    .maximumExtensionPeriod(Duration.ofDays(7))
                    .chargeRequestNotificationEnabled(true)
                    .depositReceivedNotificationEnabled(true)
                    .depositInsufficientNotificationEnabled(true)
                    .depositExceededNotificationEnabled(true)
                    .extensionNotificationEnabled(true)
                    .build())
            .dispatch();

    assertEquals(response.getId().toString(), "11e751a6-15b1-169c-8d58-47c3d241a399");
    assertEquals(response.getName(), "Modified Store");
    assertEquals(response.getCreatedOn(), expectedCreatedOn);
    assertTrue(response.getConfiguration().getCardConfiguration().getDebitEnabled());
    assertTrue(response.getConfiguration().getCardConfiguration().getPrepaidEnabled());
    assertEquals(response.getConfiguration().getLogoUrl(), logoUrl);
    assertThat(response.getConfiguration().getTimeZone().getId(), is(timeZone.getId()));
    assertThat(response.getConfiguration().getCountry(), is(country));
    assertEquals(
        response.getConfiguration().getRecurringConfiguration().getRecurringType(),
        RecurringTokenPrivilege.BOUNDED);
    assertThat(
        response
            .getConfiguration()
            .getRecurringConfiguration()
            .getRecurringTokenCVVConfirmation()
            .getEnabled(),
        is(true));
    assertThat(
        response
            .getConfiguration()
            .getRecurringConfiguration()
            .getRecurringTokenCVVConfirmation()
            .getThreshold()
            .get(0)
            .getCurrency(),
        is("JPY"));
    assertThat(
        response
            .getConfiguration()
            .getRecurringConfiguration()
            .getRecurringTokenCVVConfirmation()
            .getThreshold()
            .get(0)
            .getAmount(),
        is(BigInteger.valueOf(10000)));
    assertEquals(
        response.getConfiguration().getSecurityConfiguration().getInspectSuspiciousLoginAfter(),
        Period.ofDays(20));
    assertEquals(
        response.getConfiguration().getCardBrandPercentFees().get(CardBrand.VISA),
        BigDecimal.valueOf(0.025));
    assertEquals(
        response.getConfiguration().getCardConfiguration().getAllowedCountriesByIp(),
        allowedCountriesByIp);
    assertTrue(response.getConfiguration().getUserTransactionsConfiguration().getEnabled());
    assertTrue(response.getConfiguration().getUserTransactionsConfiguration().getNotifyCustomer());
    assertTrue(response.getConfiguration().getInstallmentsConfiguration().getEnabled());

    assertEquals(
        response
            .getConfiguration()
            .getInstallmentsConfiguration()
            .getMinChargeAmount()
            .getMinChargeAmount(),
        BigInteger.valueOf(10000));
    assertEquals(
        response
            .getConfiguration()
            .getInstallmentsConfiguration()
            .getMinChargeAmount()
            .getMinChargeCurrency(),
        "jpy");
    assertThat(response.getConfiguration().getPaidyConfiguration().getEnabled(), is(true));
    assertThat(response.getConfiguration().getQrMerchantConfiguration().getEnabled(), is(false));

    OnlineConfiguration onlineConfiguration = response.getConfiguration().getOnlineConfiguration();
    assertThat(onlineConfiguration.getEnabled(), is(false));

    Matcher<BankTransferConfiguration> matcher =
        allOf(
            hasProperty("enabled", is(false)),
            hasProperty("matchAmount", is(VirtualBankMatchAmount.Exact)),
            hasProperty("expirationPeriod", is(Duration.ofDays(7))),
            hasProperty(
                "expirationTimeShift",
                is(BankTransferExpirationTimeShift.builder().enabled(null).build())),
            hasProperty("virtualBankAccountThreshold", is(3)),
            hasProperty("virtualBankAccountFetchCount", is(5)),
            hasProperty("defaultExtensionPeriod", is(Duration.ofDays(7))),
            hasProperty("maximumExtensionPeriod", is(Duration.ofDays(7))),
            hasProperty("chargeRequestNotificationEnabled", is(true)),
            hasProperty("depositReceivedNotificationEnabled", is(true)),
            hasProperty("depositInsufficientNotificationEnabled", is(true)),
            hasProperty("depositExceededNotificationEnabled", is(true)),
            hasProperty("extensionNotificationEnabled", is(true)));

    assertThat(response.getConfiguration().getBankTransferConfiguration(), matcher);
  }

  @Test
  public void shouldPostAndReturnUpdatedStoreInfoWithNoConfiguration()
      throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/stores/11e751a6-15b1-169c-8d58-47c3d241a399",
        jwt,
        200,
        StoreFakeRR.updateStoreFakeEmptyResponse,
        StoreFakeRR.updateStoreFakeEmptyRequest);

    UnivapaySDK sdk = createTestInstance(AuthType.JWT);

    StoreWithConfiguration response =
        sdk.updateStore(new StoreId("11e751a6-15b1-169c-8d58-47c3d241a399"))
            .withName("Modified Store")
            .build()
            .dispatch();

    assertEquals(response.getId().toString(), "11e751a6-15b1-169c-8d58-47c3d241a399");
    assertEquals(response.getName(), "Modified Store");
    assertEquals(response.getCreatedOn(), expectedCreatedOn);
  }
}
