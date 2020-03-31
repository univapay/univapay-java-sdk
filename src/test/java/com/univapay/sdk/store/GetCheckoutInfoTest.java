package com.univapay.sdk.store;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.Domain;
import com.univapay.sdk.models.response.store.CheckoutInfo;
import com.univapay.sdk.types.*;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.RecurringTokenPrivilege;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGeneratorWithAppTokenSecret;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.math.BigInteger;
import org.hamcrest.core.Is;
import org.junit.Assert;
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

    Assert.assertEquals(response.getMode(), ProcessingMode.LIVE_TEST);
    Assert.assertEquals(response.getRecurringTokenPrivilege(), RecurringTokenPrivilege.INFINITE);
    assertEquals(response.getName(), "Test Store");
    assertEquals(response.getLogoImage().toString(), "http://www.images.com/image01");
    assertEquals(response.getTheme().getColors().getMainBackground(), "#fafafa");
    assertEquals(response.getTheme().getColors().getSecondaryBackground(), "#ee7a00");
    assertEquals(response.getTheme().getColors().getMainColor(), "#fafafa");
    assertEquals(response.getTheme().getColors().getMainText(), "#838383");
    assertEquals(response.getTheme().getColors().getPrimaryText(), "#fafafa");
    assertEquals(response.getTheme().getColors().getSecondaryText(), "#222222");
    assertEquals(response.getTheme().getColors().getBaseText(), "#000000");
    assertNotNull(response.getCardConfiguration());
    assertNotNull(response.getKonbiniConfiguration());
    assertNotNull(response.getQrScanConfiguration());
    assertThat(response.getRecurringTokenCVVConfirmation().getEnabled(), is(true));
    assertThat(
        response.getRecurringTokenCVVConfirmation().getThreshold().get(0).getCurrency(), is("JPY"));
    assertThat(
        response.getRecurringTokenCVVConfirmation().getThreshold().get(0).getAmount(),
        is(BigInteger.valueOf(10000)));
    assertThat(response.getPaidyConfiguration().getEnabled(), is(true));
    assertThat(response.getPaidyPublicKey(), is("pk_test_9bta9fm2cbvpcscddhr7qrnkkb"));
    assertThat(response.getSupportedBrands().get(0).getCardBrand(), Is.is(CardBrand.MAESTRO));
    assertThat(response.getSupportedBrands().get(1).getCardBrand(), is(CardBrand.AMERICAN_EXPRESS));
    assertThat(
        response.getSupportedBrands().get(0).getCountriesAllowed().contains(Country.TAIWAN),
        is(true));
    assertThat(response.getSupportedBrands().get(0).getRequiresCVV(), is(true));
    assertThat(response.getSupportedBrands().get(0).getRequiresFullName(), is(true));
    assertThat(response.getSupportedBrands().get(0).getSupportAuthCapture(), is(true));
    assertThat(response.getSupportedBrands().get(0).getSupportsDynamicDescriptor(), is(true));
    assertThat(
        response.getSupportedBrands().get(0).getSupportedCurrencies().contains("TWD"), is(true));
  }

  @Test
  public void shouldRequestAndReturnCheckoutInfoWithNoDomainOnRequest() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET", "/checkout_info", appToken, secret, 200, StoreFakeRR.getCheckoutInfoFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    CheckoutInfo response = univapay.getCheckoutInfo().build().dispatch();

    Assert.assertEquals(response.getMode(), ProcessingMode.LIVE_TEST);
    Assert.assertEquals(response.getRecurringTokenPrivilege(), RecurringTokenPrivilege.INFINITE);
    assertEquals(response.getName(), "Test Store");
    assertEquals(response.getLogoImage().toString(), "http://www.images.com/image01");
    assertEquals(response.getTheme().getColors().getMainBackground(), "#fafafa");
    assertEquals(response.getTheme().getColors().getSecondaryBackground(), "#ee7a00");
    assertEquals(response.getTheme().getColors().getMainColor(), "#fafafa");
    assertEquals(response.getTheme().getColors().getMainText(), "#838383");
    assertEquals(response.getTheme().getColors().getPrimaryText(), "#fafafa");
    assertEquals(response.getTheme().getColors().getSecondaryText(), "#222222");
    assertEquals(response.getTheme().getColors().getBaseText(), "#000000");
    assertNotNull(response.getCardConfiguration());
    assertNotNull(response.getKonbiniConfiguration());
    assertNotNull(response.getQrScanConfiguration());
  }
}
