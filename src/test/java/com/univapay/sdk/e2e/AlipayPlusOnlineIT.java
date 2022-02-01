package com.univapay.sdk.e2e;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.IssuerToken;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.models.response.store.CheckoutInfo;
import com.univapay.sdk.models.response.store.DynamicBrandInfo;
import com.univapay.sdk.models.response.store.DynamicBrandInfo.DynamicBrand;
import com.univapay.sdk.models.response.transactiontoken.OnlinePaymentData;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenWithData;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ChargeStatus;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.TransactionTokenType;
import com.univapay.sdk.types.brand.OnlineBrand;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGeneratorWithAppTokenSecret;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import com.univapay.sdk.utils.mockcontent.IssuerTokensFakeRR;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import org.junit.Test;

public class AlipayPlusOnlineIT extends GenericTest {

  // This is a test that showcases the SDK/API usage when creating & processing the Alipay+ Payment

  @Test
  public void shouldProcessOnlinePaymentsWithDynamicBrands() throws UnivapayException, IOException {
    mockRequestsForSuccessfulPathWithDynamicBrands();

    // Assuming that menu is presented to the user to choose the payment method
    // 1 - System must query the supported brands for a Store level ApplicationToken
    // 2 - If one of the brands is ConnectWallet & dynamicInfo is true, query the DynamicInfo route
    // 3 - Render said menu with Alipay+ & SubBrands as options for Alipay+ Online payments
    // 4 - User will choose Alipay+ & Brand
    // 5 - System will use SDK to create a TransactionToken & Charge with said sub-brand
    // 6 - As any online payment, API will generate the required data and return to front end
    // ----
    // At asynchronous fashion & using webhook, await for charge processing to be completed
    //  ChargeStatus will become terminal (successful, error ,failed etc)

    //
    //
    // 1 - System must query the supported brands for a Store level ApplicationToken

    // Given the stored token, create or fetch previous created sdk instance

    UnivapaySDK sdk = createTestInstance(AuthType.APP_TOKEN);

    // Then query the checkout info route
    CheckoutInfo checkoutInfo = sdk.getCheckoutInfo().dispatch();

    // 2 - If one of the brands is ConnectWallet & dynamicInfo is true, query the DynamicInfo route
    boolean supportDynamicAlipayPlus = checkIfAlipayPlusIsSupported(checkoutInfo);

    assertThat(
        "Is this example, we expect that the store can support Alipay+ payments with Dynamic rendering of the sub brands",
        supportDynamicAlipayPlus,
        is(equalTo(true)));

    DynamicBrandInfo dynamicBrandInfo =
        sdk.getDynamicBrandInfo(Gateway.ALIPAY_PLUS_ONLINE)
            .withRequestedMoney(MoneyLike.of("JPY", 1000))
            .withCallMethod(CallMethod.APP)
            .withOsType(OsType.iOS)
            .dispatch();

    // Alipay+ dynamic brands will be grouped as "CONNECT_WALLET" key (brand name)
    List<DynamicBrand> alipayPlusSubBrands =
        dynamicBrandInfo.getBrands().stream()
            .filter(value -> "CONNECT_WALLET".equals(value.getBrandName()))
            .collect(Collectors.toList());

    assertThat(
        "Must have at least one to process with Alipay+, is this example, is expected 7",
        alipayPlusSubBrands,
        hasSize(7));

    // Organize the data in any shape that is preferred
    AlipayPlusInformation information = generateInformation(alipayPlusSubBrands);

    // 3 - Render said menu with Alipay+ & SubBrands as options for Alipay+ Online payments
    render(information);
    // 4 - User will choose Alipay+ & Brand

    // At this point the TransactionToken and Charge will be created

    // 5 - System will use SDK to create a TransactionToken & Charge with said sub-brand as
    // online_brand
    OnlinePayment dynamicTokenCreation =
        new OnlinePayment("KAKAOPAY").withCallMethod(CallMethod.APP).withOsType(OsType.iOS);

    TransactionTokenWithData transactionTokenForDynamicBrand =
        sdk.createTransactionToken(/*email ,*/ dynamicTokenCreation, TransactionTokenType.ONE_TIME)
            .dispatch();

    OnlinePaymentData onlinePaymentData =
        transactionTokenForDynamicBrand.getData().asOnlinePaymentData();

    // Java enum are really static, so I can't model a "ConnectWallet(sub-brand)"
    assertThat(onlinePaymentData.couldBeDynamicBrand(), is(true));
    // If the brand is dynamic, the OnlineBrand will be null...
    assertThat(onlinePaymentData.getOnlineBrand(), is(nullValue()));

    // Then create a Charge
    TransactionTokenId id = transactionTokenForDynamicBrand.getId();

    Charge createdCharge =
        sdk.createCharge(id, BigInteger.valueOf(100), "JPY")
            .withIdempotencyKey(new IdempotencyKey("Recommended"))
            .dispatch();

    assertThat(createdCharge.getStatus(), is(ChargeStatus.PENDING));

    Charge charge = null;

    try {
      charge =
          sdk.chargeCompletionMonitor(createdCharge.getStoreId(), createdCharge.getId()).await();
    } catch (InterruptedException | TimeoutException e) {
      fail(
          "Charge status usually goes from PENDING to AWAITING really quickly.. but is important to handle TimeoutException");
    }

    // After charge becomes AWAITING, the token information is available
    IssuerToken issuerToken = sdk.getIssuerToken(charge.getStoreId(), charge.getId()).dispatch();

    assertThat(issuerToken.getIssuerToken(), is("alipayconnect://schemeUrl"));
    assertThat(issuerToken.getCallMethod(), is(CallMethod.APP));

    // Return to the front end the  issuerToken.getIssuerToken() && issuerToken.getCallMethod()
    // values
    // Then this is ready to wait until the charge is Successful or Failed

    // TODO: Add a Successful Charge Response
    // TODO: Add a Successful Transaction Token Response

  }

  private void mockRequestsForSuccessfulPathWithDynamicBrands() {

    // TODO: Check if all these routes require a secret or not

    MockRRGeneratorWithAppTokenSecret mock = new MockRRGeneratorWithAppTokenSecret();

    mock.GenerateMockRequestResponse(
        "GET", "/checkout_info", appToken, secret, 200, StoreFakeRR.getCheckoutInfoFakeResponse);

    mock.GenerateMockRequestResponse(
        "POST",
        "/checkout_info/gateway/alipay_plus_online",
        appToken,
        secret,
        200,
        StoreFakeRR.getDynamicInfoAlipayPlusResponse,
        StoreFakeRR.getDynamicInfoAlipayPlusRequest);

    mock.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createTransactionTokenAlipayPlusDynamicBrandResponse,
        StoreFakeRR.createTransactionTokenAlipayPlusDynamicBrandRequest);

    mock.GenerateMockRequestResponse(
        "POST",
        "/charges",
        appToken,
        secret,
        200,
        ChargesFakeRR.createStoreChargeOnlineResponse,
        ChargesFakeRR.createStoreChargeOnlineRequest);

    mock.GenerateMockQueuedRequestResponse(
        "GET",
        "/stores/11ec8336-dcea-f374-80a8-174b9ae64898/charges/11ec8336-de56-9a4c-935e-6f39db819b28?polling=true",
        appToken,
        secret,
        200,
        /*request body = */ null,
        ChargesFakeRR.createStoreChargeOnlineResponse,
        ChargesFakeRR.createStoreChargeOnlineResponse,
        ChargesFakeRR.createStoreChargeOnlineDeferredResponse);

    mock.GenerateMockRequestResponse(
        "GET",
        "/stores/11ec8336-dcea-f374-80a8-174b9ae64898/charges/11ec8336-de56-9a4c-935e-6f39db819b28/issuerToken",
        appToken,
        secret,
        200,
        IssuerTokensFakeRR.getIssuerTokenForAlipayPlusAppResponse);
  }

  private boolean checkIfAlipayPlusIsSupported(CheckoutInfo checkoutInfo) {
    return checkoutInfo.getSupportedBrands().stream()
        .anyMatch(
            value ->
                OnlineBrand.CONNECT_WALLET.equals(value.getOnlineBrand())
                    && Boolean.TRUE.equals(value.getDynamicInfo()));
  }

  // This is just a marker to note that this will be returned to the front ent
  private void render(AlipayPlusInformation information) {
    assertThat(information.getLogoUri(), is("http://localhost/CONNECT_WALLET.svg"));
  }

  private AlipayPlusInformation generateInformation(List<DynamicBrand> alipayPlusSubBrands) {
    if (alipayPlusSubBrands.isEmpty()) {
      throw new IllegalArgumentException("alipayPlusSubBrands must be non empty");
    }

    DynamicBrand brand = alipayPlusSubBrands.get(0);
    Set<AlipayPlusSubBrand> setOfSubBrands =
        alipayPlusSubBrands.stream()
            .map(value -> new AlipayPlusSubBrand(value.getSubBrandName(), value.getSubBrandLogo()))
            .collect(Collectors.toSet());

    return new AlipayPlusInformation(brand.getBrandLogo(), setOfSubBrands);
  }

  // This is just an example how to organize the DynamicBrandInfo
  static class AlipayPlusInformation {

    private final String logoUri;
    private Set<AlipayPlusSubBrand> subBrands;

    AlipayPlusInformation(String logoUri, Set<AlipayPlusSubBrand> subBrands) {
      this.logoUri = logoUri;
      this.subBrands = subBrands;
    }

    public Set<AlipayPlusSubBrand> getSubBrands() {
      return subBrands;
    }

    public String getLogoUri() {
      return logoUri;
    }
  }

  static class AlipayPlusSubBrand {

    private final String logoUri;
    // Need to be used to create the TransactionToken later
    private final String brandName;

    AlipayPlusSubBrand(String logoUri, String brandName) {
      this.logoUri = logoUri;
      this.brandName = brandName;
    }

    public String getBrandName() {
      return brandName;
    }

    public String getLogoUri() {
      return logoUri;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      AlipayPlusSubBrand that = (AlipayPlusSubBrand) o;
      return Objects.equals(logoUri, that.logoUri) && Objects.equals(brandName, that.brandName);
    }

    @Override
    public int hashCode() {
      return Objects.hash(logoUri, brandName);
    }
  }
}
