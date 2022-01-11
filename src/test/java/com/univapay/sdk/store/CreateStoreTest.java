package com.univapay.sdk.store;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.stores.SecurityConfiguration;
import com.univapay.sdk.models.errors.UnivapayException;
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
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import org.junit.Test;

public class CreateStoreTest extends GenericTest {

  // This is the expected createdOn
  final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

  @Test
  public void shouldPostAndReturnNewStoreData() throws InterruptedException, MalformedURLException {
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
            new SecurityConfiguration(Period.ofDays(20)).withConfirmationRequired(true))
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
        .withUserTransactionsConfiguration(new UserTransactionsConfiguration(true, true, true))
        .withQrScanConfiguration(new QrScanConfiguration(true, forbiddenQrScanGateways))
        .withConvenienceConfiguration(new KonbiniConfiguration(false))
        .withPaidyConfiguration(new PaidyConfiguration(true))
        .withQrMerchantConfiguration(new QrMerchantConfiguration(false))
        .withOnlineConfiguration(new OnlineConfiguration(false))
        .build()
        .dispatch(
            new UnivapayCallback<StoreWithConfiguration>() {
              @Override
              public void getResponse(StoreWithConfiguration response) {
                assertEquals(response.getId().toString(), "11e751a6-15b1-169c-8d58-47c3d241a399");
                assertEquals(response.getName(), "A New Store");
                assertEquals(response.getCreatedOn(), parsedDate);
                StoreConfiguration configuration = response.getConfiguration();

                assertTrue(configuration.getCardConfiguration().getDebitEnabled());
                assertTrue(configuration.getCardConfiguration().getPrepaidEnabled());
                assertTrue(configuration.getCardConfiguration().getOnlyDirectCurrency());
                assertEquals(configuration.getLogoUrl(), logoUrl);
                assertThat(configuration.getTimeZone().getId(), is(timeZone.getId()));
                assertThat(configuration.getCountry(), is(Country.JAPAN));
                assertEquals(
                    configuration.getRecurringConfiguration().getRecurringType(),
                    RecurringTokenPrivilege.BOUNDED);
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
                assertTrue(configuration.getUserTransactionsConfiguration().getNotifyCustomer());
                assertThat(configuration.getPaidyConfiguration().getEnabled(), is(true));
                assertThat(configuration.getQrMerchantConfiguration().getEnabled(), is(false));

                OnlineConfiguration onlineConfiguration = configuration.getOnlineConfiguration();
                assertThat(onlineConfiguration.getEnabled(), is(false));

                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
                fail();
              }
            });

    waitCall();
  }

  @Test
  public void shouldPostAndReturnNewStoreDataWithNoConfiguration()
      throws IOException, UnivapayException {
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

    assertEquals(response.getId().toString(), "11e751a6-15b1-169c-8d58-47c3d241a399");
    assertEquals(response.getName(), "A New Store");
    assertEquals(response.getCreatedOn(), parsedDate);
  }
}
