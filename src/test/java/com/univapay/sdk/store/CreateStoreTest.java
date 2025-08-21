package com.univapay.sdk.store;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.stores.SecurityConfiguration;
import com.univapay.sdk.models.response.store.QrScanConfiguration;
import com.univapay.sdk.models.response.store.RecurringTokenConfiguration;
import com.univapay.sdk.models.response.store.StoreConfiguration;
import com.univapay.sdk.models.response.store.StoreWithConfiguration;
import com.univapay.sdk.types.*;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.builders.CardConfigurationBuilder;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

class CreateStoreTest extends GenericTest {

  private final Logger logger = Logger.getLogger(this.getClass().getName());

  // This is the expected createdOn

  final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

  @Test
  void shouldPostAndReturnNewStoreData() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/stores",
        jwt,
        200,
        StoreFakeRR.createStoreFakeResponse,
        StoreFakeRR.createStoreFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

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

    univapay
        .createStore("A New Store")
        .withCountry(Country.JAPAN)
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
                .confirmationRequired(true)
                .build())
        .withCardBrandPercentFees(percentFees)
        .withRecurringTokenConfiguration(
            new RecurringTokenConfiguration(
                RecurringTokenPrivilege.BOUNDED,
                Duration.ofDays(10),
                new RecurringTokenCVVConfirmation(
                    true,
                    Collections.singletonList(new MoneyLike(BigInteger.valueOf(10000), "JPY")))))
        .withLanguage(Locale.GERMAN)
        .withLogoUrl(logoUrl)
        .withTimeZone(timeZone)
        .withUserTransactionsConfiguration(
            UserTransactionsConfiguration.builder()
                .enabled(true)
                .notifyOnTest(true)
                .notifyCustomer(true)
                .notifyOnRecurringTokenCreation(true)
                .notifyOnRecurringTokenCvvFailed(true)
                .notifyOnWebhookFailure(true)
                .notifyOnWebhookDisabled(true)
                .notifyUserOnFailedTransactions(true)
                .notifyCustomerOnFailedTransactions(true)
                .notifyUserOnConvenienceInstructions(true)
                .notifyOnSubscriptions(true)
                .notifyOnAuthorizations(true)
                .notifyOnCvvAuthorizations(true)
                .notifyOnCancels(true)
                .customerReferLinkEnabled(true)
                .notifyOnConvenienceExpiry(true)
                .build())
        .withQrScanConfiguration(new QrScanConfiguration(true, forbiddenQrScanGateways))
        .withConvenienceConfiguration(new KonbiniConfiguration(false, null, null))
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
                .chargeRequestCanceledNotificationEnabled(true)
                .chargeExpiredNotificationEnabled(true)
                .depositReceivedNotificationEnabled(true)
                .depositInsufficientNotificationEnabled(true)
                .depositExceededNotificationEnabled(true)
                .extensionNotificationEnabled(true)
                .build())
        .build()
        .dispatch(
            new UnivapayCallback<StoreWithConfiguration>() {
              @Override
              public void getResponse(StoreWithConfiguration response) {
                assertEquals("11e751a6-15b1-169c-8d58-47c3d241a399", response.getId().toString());
                assertEquals("A New Store", response.getName());
                assertEquals(response.getCreatedOn(), parsedDate);
                StoreConfiguration configuration = response.getConfiguration();

                assertTrue(configuration.getCardConfiguration().getDebitEnabled());
                assertTrue(configuration.getCardConfiguration().getPrepaidEnabled());
                assertTrue(configuration.getCardConfiguration().getOnlyDirectCurrency());
                assertEquals(configuration.getLogoUrl(), logoUrl);
                assertThat(configuration.getTimeZone().getId(), is(timeZone.getId()));
                assertThat(configuration.getCountry(), is(Country.JAPAN));
                assertEquals(
                    RecurringTokenPrivilege.BOUNDED,
                    configuration.getRecurringConfiguration().getRecurringType());
                assertThat(
                    configuration
                        .getRecurringConfiguration()
                        .getRecurringTokenCVVConfirmation()
                        .getEnabled(),
                    is(true));
                assertThat(
                    configuration
                        .getRecurringConfiguration()
                        .getRecurringTokenCVVConfirmation()
                        .getThreshold()
                        .get(0)
                        .getAmount(),
                    is(BigInteger.valueOf(10000)));
                assertThat(
                    configuration
                        .getRecurringConfiguration()
                        .getRecurringTokenCVVConfirmation()
                        .getThreshold()
                        .get(0)
                        .getCurrency(),
                    is("JPY"));
                assertEquals(
                    configuration.getSecurityConfiguration().getInspectSuspiciousLoginAfter(),
                    Period.ofDays(20));
                assertThat(
                    configuration.getSecurityConfiguration().getConfirmationRequired(), is(true));
                assertEquals(
                    configuration.getCardBrandPercentFees().get(CardBrand.VISA),
                    BigDecimal.valueOf(0.025));
                assertTrue(configuration.getUserTransactionsConfiguration().getEnabled());
                assertTrue(configuration.getUserTransactionsConfiguration().getNotifyOnTest());
                assertTrue(configuration.getUserTransactionsConfiguration().getNotifyCustomer());
                assertTrue(
                    configuration
                        .getUserTransactionsConfiguration()
                        .getNotifyOnRecurringTokenCreation());
                assertTrue(
                    configuration
                        .getUserTransactionsConfiguration()
                        .getNotifyOnRecurringTokenCvvFailed());
                assertTrue(
                    configuration.getUserTransactionsConfiguration().getNotifyOnWebhookFailure());
                assertTrue(
                    configuration.getUserTransactionsConfiguration().getNotifyOnWebhookDisabled());
                assertTrue(
                    configuration
                        .getUserTransactionsConfiguration()
                        .getNotifyUserOnFailedTransactions());
                assertTrue(
                    configuration
                        .getUserTransactionsConfiguration()
                        .getNotifyCustomerOnFailedTransactions());
                assertTrue(
                    configuration
                        .getUserTransactionsConfiguration()
                        .getNotifyUserOnConvenienceInstructions());
                assertTrue(
                    configuration.getUserTransactionsConfiguration().getNotifyOnSubscriptions());
                assertTrue(
                    configuration.getUserTransactionsConfiguration().getNotifyOnAuthorizations());
                assertTrue(
                    configuration
                        .getUserTransactionsConfiguration()
                        .getNotifyOnCvvAuthorizations());
                assertTrue(configuration.getUserTransactionsConfiguration().getNotifyOnCancels());
                assertTrue(
                    configuration.getUserTransactionsConfiguration().getCustomerReferLinkEnabled());
                assertTrue(
                    configuration
                        .getUserTransactionsConfiguration()
                        .getNotifyOnConvenienceExpiry());
                assertThat(configuration.getPaidyConfiguration().getEnabled(), is(true));
                assertThat(configuration.getQrMerchantConfiguration().getEnabled(), is(false));

                OnlineConfiguration onlineConfiguration = configuration.getOnlineConfiguration();
                assertThat(onlineConfiguration.getEnabled(), is(false));

                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();

                logger.log(Level.SEVERE, "Test failed", error);

                fail();
              }
            });

    waitCall();
  }

  @Test
  void shouldPostAndReturnNewStoreDataWithNoConfiguration() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/stores",
        jwt,
        200,
        StoreFakeRR.createStoreFakeEmptyResponse,
        StoreFakeRR.createStoreFakeEmptyRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    StoreWithConfiguration response = univapay.createStore("A New Store").build().dispatch();

    assertEquals("11e751a6-15b1-169c-8d58-47c3d241a399", response.getId().toString());
    assertEquals("A New Store", response.getName());
    assertEquals(response.getCreatedOn(), parsedDate);
  }
}
