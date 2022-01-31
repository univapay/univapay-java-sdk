package com.univapay.sdk.store;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.response.store.*;
import com.univapay.sdk.models.response.store.BankTransferConfiguration;
import com.univapay.sdk.models.response.store.InstallmentsConfiguration;
import com.univapay.sdk.models.response.store.OnlineConfiguration;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.RecurringTokenPrivilege;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGeneratorWithAppTokenSecret;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;

import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class GetCheckoutInfoTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnCheckoutInfo() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/checkout_info?origin=test.com",
        appToken,
        secret,
        200,
        StoreFakeRR.getCheckoutInfoFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    CheckoutInfo response = univapay.getCheckoutInfo(new Domain("test.com")).build().dispatch();

    assertResponse(response);
  }

  @Test
  public void shouldRequestAndReturnCheckoutInfoWithNoDomainOnRequest() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET", "/checkout_info", appToken, secret, 200, StoreFakeRR.getCheckoutInfoFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    CheckoutInfo response = univapay.getCheckoutInfo().build().dispatch();

    assertResponse(response);
  }

  // This test assertions is shared with both tests
  private void assertResponse(CheckoutInfo response) {
    assertEquals(response.getMode(), ProcessingMode.LIVE_TEST);
    assertEquals(response.getRecurringTokenPrivilege(), RecurringTokenPrivilege.INFINITE);
    assertEquals(response.getName(), "Test Store");
    assertEquals(response.getLogoImage().toString(), "http://localhost:8080/fake_url");

    WidgetColors expectedColors =
        new WidgetColors(
            "#FFFFFF", "#F5F8FC", "#4C5F85", "#FFFFFF", "#4C5F85", "#4C5F85", "#4C5F85");

    assertThat(response.getTheme().getColors(), samePropertyValuesAs(expectedColors));

    // CardConfiguration
    CardConfiguration expectedCardConfiguration =
        new CardConfiguration(
            true, true, true, null, null, null, null, null, null, false, false, false);

    assertThat(response.getCardConfiguration(), samePropertyValuesAs(expectedCardConfiguration));

    // KonbiniConfiguration
    KonbiniConfiguration expectedConvenienceConfiguration = new KonbiniConfiguration(true);
    assertThat(
        response.getKonbiniConfiguration(), samePropertyValuesAs(expectedConvenienceConfiguration));

    // QrScanConfiguration

    QrScanConfiguration expectedQrScanConfiguration = new QrScanConfiguration(true, null);
    assertThat(
        response.getQrScanConfiguration(), samePropertyValuesAs(expectedQrScanConfiguration));

    //  RecurringTokenCVVConfirmation
    RecurringTokenCVVConfirmation expectedRecurringTokenCVVConfirmation =
        new RecurringTokenCVVConfirmation(true, singletonList(MoneyLike.of("JPY", 10000)));

    assertThat(
        response.getRecurringTokenCVVConfirmation(),
        samePropertyValuesAs(expectedRecurringTokenCVVConfirmation));

    // Paidy Configuration
    PaidyConfiguration expectedPaidyConfiguration = new PaidyConfiguration(true);

    assertThat(response.getPaidyConfiguration(), samePropertyValuesAs(expectedPaidyConfiguration));
    assertThat(response.getPaidyPublicKey(), is("fake_key"));

    SubscriptionConfiguration subscriptionConfiguration = new SubscriptionConfiguration(true);

    assertThat(
        response.getSubscriptionConfiguration(), samePropertyValuesAs(subscriptionConfiguration));

    CheckoutConfiguration expectedCheckoutConfiguration =
        new CheckoutConfiguration(
            new CheckoutConfiguration.EcMailConfiguration(true),
            new CheckoutConfiguration.EcProductsConfiguration(false));

    assertThat(
        response.getCheckoutConfiguration().getEcMail(),
        samePropertyValuesAs(expectedCheckoutConfiguration.getEcMail()));
    assertThat(
        response.getCheckoutConfiguration().getEcProducts(),
        samePropertyValuesAs(expectedCheckoutConfiguration.getEcProducts()));

    InstallmentsConfiguration expectedInstallmentsConfiguration =
        new InstallmentsConfiguration(
            true, false, new InstallmentsConfiguration.CardProcessor(true, true));

    assertThat(
        response.getInstallmentsConfiguration(),
        samePropertyValuesAs(expectedInstallmentsConfiguration, "cardProcessor"));
    assertThat(
        response.getInstallmentsConfiguration().getCardProcessor(),
        samePropertyValuesAs(
            expectedInstallmentsConfiguration.getCardProcessor(), "cardProcessor"));

    // Online Configuration

      OnlineConfiguration onlineConfiguration = new OnlineConfiguration(true);
      assertThat(
              response.getOnlineConfiguration(),
              samePropertyValuesAs(onlineConfiguration));

    //  BankTransferConfiguration


    BankTransferConfiguration bankTransferConfiguration = new BankTransferConfiguration(
            true,
            VirtualBankMatchAmount.Exact,
            Period.ofDays(3)
    );

    assertThat(
            response.getBankTransferConfiguration(),
            samePropertyValuesAs(bankTransferConfiguration));


    // TODO: Implement the Online Brand and QrMpmBands for these "Supported Brands" model

    // For now try to keep the current test behaviour to avoid regressions

    List<CheckoutFeatureSupport> supportedBrands = response.getSupportedBrands();

    CheckoutFeatureSupport expectedMaestroSupport =
        new CheckoutFeatureSupport(
            CardBrand.MAESTRO, true, true, true, true, singleton(Country.TAIWAN), singleton("TWD"));

    matchExpectedSupportedBrands(supportedBrands, CardBrand.MAESTRO, expectedMaestroSupport);

    CheckoutFeatureSupport expectedAmexSupport =
        new CheckoutFeatureSupport(
            CardBrand.AMERICAN_EXPRESS, true, false, true, false, null, singleton("JPY"));
    matchExpectedSupportedBrands(supportedBrands, CardBrand.AMERICAN_EXPRESS, expectedAmexSupport);
  }

  private void matchExpectedSupportedBrands(
      List<CheckoutFeatureSupport> source,
      CardBrand cardBrand,
      CheckoutFeatureSupport expectedSupportedBrand) {

    List<CheckoutFeatureSupport> foundCardBrands =
        source.stream()
            .filter(value -> cardBrand.equals(value.getCardBrand()))
            .collect(Collectors.toList());

    assertThat(
        "There should be only one record for " + cardBrand + " at this response",
        foundCardBrands.size(),
        is(1));

    // If there is only one...
    assertThat(foundCardBrands.get(0), samePropertyValuesAs(expectedSupportedBrand));
  }
}
