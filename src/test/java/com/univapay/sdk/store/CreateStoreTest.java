package com.univapay.sdk.store;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.KonbiniConfiguration;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.PaidyConfiguration;
import com.univapay.sdk.models.common.QrMerchantConfiguration;
import com.univapay.sdk.models.common.RecurringTokenCVVConfirmation;
import com.univapay.sdk.models.common.UserTransactionsConfiguration;
import com.univapay.sdk.models.common.stores.SecurityConfiguration;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.store.QrScanConfiguration;
import com.univapay.sdk.models.response.store.RecurringTokenConfiguration;
import com.univapay.sdk.models.response.store.StoreWithConfiguration;
import com.univapay.sdk.types.*;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.RecurringTokenPrivilege;
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
import java.text.ParseException;
import java.util.*;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Test;
import org.threeten.bp.ZoneId;

public class CreateStoreTest extends GenericTest {

  @Test
  public void shouldPostAndReturnNewStoreData()
      throws InterruptedException, ParseException, MalformedURLException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/stores",
        token,
        200,
        StoreFakeRR.createStoreFakeResponse,
        StoreFakeRR.createStoreFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final Date parsedDate = dateParser.parseDateTime("2017-06-22T16:00:55.436116+09:00").toDate();

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
                .build())
        .withSecurityConfiguration(
            new SecurityConfiguration(Period.days(20)).withConfirmationRequired(true))
        .withCardBrandPercentFees(percentFees)
        .withRecurringTokenConfiguration(
            new RecurringTokenConfiguration(
                RecurringTokenPrivilege.BOUNDED,
                Period.days(10),
                new RecurringTokenCVVConfirmation(
                    true,
                    Collections.singletonList(new MoneyLike(BigInteger.valueOf(10000), "JPY")))))
        .withLanguage(Locale.GERMAN)
        .withLogoUrl(logoUrl)
        .withTimeZone(timeZone)
        .withUserTransactionsConfiguration(new UserTransactionsConfiguration(true, true))
        .withQrScanConfiguration(new QrScanConfiguration(true, forbiddenQrScanGateways))
        .withConvenienceConfiguration(new KonbiniConfiguration(false))
        .withPaidyConfiguration(new PaidyConfiguration(true))
        .withQrMerchantConfiguration(new QrMerchantConfiguration(false))
        .build()
        .dispatch(
            new UnivapayCallback<StoreWithConfiguration>() {
              @Override
              public void getResponse(StoreWithConfiguration response) {
                Assert.assertEquals(
                    response.getId().toString(), "11e751a6-15b1-169c-8d58-47c3d241a399");
                assertEquals(response.getName(), "A New Store");
                assertEquals(response.getCreatedOn(), parsedDate);
                assertTrue(response.getConfiguration().getCardConfiguration().getDebitEnabled());
                assertTrue(response.getConfiguration().getCardConfiguration().getPrepaidEnabled());
                assertTrue(response.getConfiguration().getLogoUrl().equals(logoUrl));
                assertThat(response.getConfiguration().getTimeZone().getId(), is(timeZone.getId()));
                assertThat(response.getConfiguration().getCountryEnum(), is(Country.JAPAN));
                Assert.assertEquals(
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
                        .getAmount(),
                    is(BigInteger.valueOf(10000)));
                assertThat(
                    response
                        .getConfiguration()
                        .getRecurringConfiguration()
                        .getRecurringTokenCVVConfirmation()
                        .getThreshold()
                        .get(0)
                        .getCurrency(),
                    is("JPY"));
                assertEquals(
                    response
                        .getConfiguration()
                        .getSecurityConfiguration()
                        .getInspectSuspiciousLoginAfter(),
                    Period.days(20));
                assertThat(
                    response
                        .getConfiguration()
                        .getSecurityConfiguration()
                        .getConfirmationRequired(),
                    is(true));
                assertEquals(
                    response.getConfiguration().getCardBrandPercentFees().get(CardBrand.VISA),
                    BigDecimal.valueOf(0.025));
                assertTrue(
                    response.getConfiguration().getUserTransactionsConfiguration().getEnabled());
                assertTrue(
                    response
                        .getConfiguration()
                        .getUserTransactionsConfiguration()
                        .getNotifyCustomer());
                assertThat(
                    response.getConfiguration().getPaidyConfiguration().getEnabled(), is(true));
                assertThat(
                    response.getConfiguration().getQrMerchantConfiguration().getEnabled(),
                    is(false));
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                System.out.println(error.getMessage());
                fail();
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  public void shouldPostLegacyCountry() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/stores",
        token,
        200,
        StoreFakeRR.createStoreFakeResponse,
        StoreFakeRR.createStoreFakeRequest2);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);
    final String country = "JP";
    univapay.createStore("A New Store").withCountry(country).build().dispatch();
  }
}
