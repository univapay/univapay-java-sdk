package com.univapay.sdk.transactiontoken;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.CallMethod;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.charge.CvvAuthorization;
import com.univapay.sdk.models.common.charge.CvvAuthorizationStatus;
import com.univapay.sdk.models.response.transactiontoken.OnlinePaymentData;
import com.univapay.sdk.models.response.transactiontoken.QrMerchantPaymentData;
import com.univapay.sdk.models.response.transactiontoken.QrScanPaymentData;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenWithData;
import com.univapay.sdk.types.*;
import com.univapay.sdk.types.brand.OnlineBrand;
import com.univapay.sdk.types.brand.QrCpmBrand;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.JsonLoader;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class GetTransactionTokenTest extends GenericTest {

  private final UnivapaySDK univapay = createTestInstance(AuthType.JWT);

  @Test
  void shouldRequestAndReturnTransactionTokenInfo() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00",
        jwt,
        200,
        StoreFakeRR.getTransactionTokenFakeResponse);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    univapay
        .getTransactionToken(
            new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"),
            new TransactionTokenId("004b391f-1c98-43f8-87de-28b21aaaca00"))
        .build()
        .dispatch(
            new UnivapayCallback<TransactionTokenWithData>() {
              @Override
              public void getResponse(TransactionTokenWithData response) {
                assertEquals("004b391f-1c98-43f8-87de-28b21aaaca00", response.getId().toString());
                assertEquals(
                    "bf75472e-7f2d-4745-a66d-9b96ae031c7a", response.getStoreId().toString());
                assertEquals(ProcessingMode.LIVE, response.getMode());
                assertEquals(response.getCreatedOn(), parsedDate);
                assertNull(response.getLastUsedOn());
                assertEquals(PaymentTypeName.CARD, response.getPaymentTypeName());
                assertEquals("full name", response.getData().getCard().getCardholder());
                assertEquals(12, response.getData().getCard().getExpMonth());
                assertEquals(2018, response.getData().getCard().getExpYear());
                assertEquals(5276, response.getData().getCard().getLastFour());
                assertEquals(CardBrand.VISA, response.getData().getCard().getBrandEnum());
                assertThat(response.getData().getCard().getCategory(), is(CardCategory.CLASSIC));
                assertThat(response.getData().getCard().getIssuer(), is("test issuer"));
                assertThat(response.getData().getCard().getSubBrand(), is(CardSubBrand.NONE));
                assertEquals("somewhere", response.getData().getBilling().getLine1());
                assertNull(response.getData().getBilling().getLine2());
                assertNull(response.getData().getBilling().getState());
                assertEquals("TYO", response.getData().getBilling().getCity());
                assertEquals("JP", response.getData().getBilling().getCountry());
                assertEquals("111-1111", response.getData().getBilling().getZip());
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

  @Test
  void shouldBeAbleToReadThePrivateBrandLabel() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00",
        jwt,
        200,
        JsonLoader.loadJson("responses/transactiontoken/get-card-private-label.json"));

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    TransactionTokenWithData response =
        univapay
            .getTransactionToken(
                new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"),
                new TransactionTokenId("004b391f-1c98-43f8-87de-28b21aaaca00"))
            .dispatch();

    assertEquals("004b391f-1c98-43f8-87de-28b21aaaca00", response.getId().toString());
    assertEquals("bf75472e-7f2d-4745-a66d-9b96ae031c7a", response.getStoreId().toString());
    assertEquals(ProcessingMode.LIVE, response.getMode());
    assertEquals(response.getCreatedOn(), parsedDate);
    assertNull(response.getLastUsedOn());
    assertEquals(PaymentTypeName.CARD, response.getPaymentTypeName());
    assertEquals("UNIVAPAY TEST", response.getData().getCard().getCardholder());
    assertEquals(12, response.getData().getCard().getExpMonth());
    assertEquals(2025, response.getData().getCard().getExpYear());
    assertEquals(6020, response.getData().getCard().getLastFour());
    assertEquals(CardBrand.PRIVATE_LABEL, response.getData().getCard().getBrandEnum());
    assertNull(response.getData().getCard().getCategory());
    assertThat(response.getData().getCard().getIssuer(), is("JEONBUK BANK"));
    assertThat(response.getData().getCard().getSubBrand(), is(CardSubBrand.NONE));
    assertEquals("somewhere", response.getData().getBilling().getLine1());
    assertNull(response.getData().getBilling().getLine2());
    assertNull(response.getData().getBilling().getState());
    assertEquals("TYO", response.getData().getBilling().getCity());
    assertEquals("JP", response.getData().getBilling().getCountry());
    assertEquals("111-1111", response.getData().getBilling().getZip());
  }

  @Test
  void shouldBeAbleToReadTheCvvAuthorizationData() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00",
        jwt,
        200,
        StoreFakeRR.getTransactionTokenCvvAuthPendingResponse);

    TransactionTokenWithData transactionToken =
        univapay
            .getTransactionToken(
                new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"),
                new TransactionTokenId("004b391f-1c98-43f8-87de-28b21aaaca00"))
            .dispatch();

    CvvAuthorization authorization =
        transactionToken.getData().asCardPaymentData().getCvvAuthorization();
    assertTrue(authorization.getEnabled());
    assertEquals(CvvAuthorizationStatus.PENDING, authorization.getStatus());
  }

  @Test
  void shouldBeAbleToReadTheCvvAuthorizationDataResourceMonitor() throws Exception {

    // This uses directly wiremock since I want to simulate scenarios

    stubFor(
        get(urlPathEqualTo(
                "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00"))
            .inScenario("CVV Auth")
            .whenScenarioStateIs(STARTED)
            .willReturn(okJson(StoreFakeRR.getTransactionTokenCvvAuthPendingResponse))
            .willSetStateTo("001"));
    stubFor(
        get(urlPathEqualTo(
                "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00"))
            .inScenario("CVV Auth")
            .whenScenarioStateIs("001")
            .willReturn(okJson(StoreFakeRR.getTransactionTokenCvvAuthPendingResponse))
            .willSetStateTo("002"));
    stubFor(
        get(urlPathEqualTo(
                "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00"))
            .inScenario("CVV Auth")
            .whenScenarioStateIs("002")
            .willReturn(okJson(StoreFakeRR.getTransactionTokenCvvAuthCurrentResponse))
            .willSetStateTo("FINISHED"));

    TransactionTokenWithData transactionToken =
        univapay
            .cvvAuthorizationCompletionMonitor(
                new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"),
                new TransactionTokenId("004b391f-1c98-43f8-87de-28b21aaaca00"))
            .await();

    CvvAuthorization authorization =
        transactionToken.getData().asCardPaymentData().getCvvAuthorization();
    assertTrue(authorization.getEnabled());
    assertEquals(CvvAuthorizationStatus.CURRENT, authorization.getStatus());
    assertEquals(
        UUID.fromString("11ed7d43-284a-afe2-bbe4-f7e019497fb1"), authorization.getChargeId());
  }

  @Test
  void shouldBeAbleToReadCvvAuthorizationDataCurrent() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00",
        jwt,
        200,
        StoreFakeRR.getTransactionTokenCvvAuthCurrentResponse);

    TransactionTokenWithData transactionToken =
        univapay
            .getTransactionToken(
                new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"),
                new TransactionTokenId("004b391f-1c98-43f8-87de-28b21aaaca00"))
            .dispatch();

    CvvAuthorization authorization =
        transactionToken.getData().asCardPaymentData().getCvvAuthorization();
    assertTrue(authorization.getEnabled());
    assertEquals(CvvAuthorizationStatus.CURRENT, authorization.getStatus());
    assertEquals(
        UUID.fromString("11ed7d43-284a-afe2-bbe4-f7e019497fb1"), authorization.getChargeId());
  }

  @Test
  void resourceMonitorForCvvAuthMustIgnoreEnableAsNullOrFalse() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00",
        jwt,
        200,
        StoreFakeRR.getTransactionTokenFakeResponse);

    TransactionTokenWithData transactionToken =
        univapay
            .cvvAuthorizationCompletionMonitor(
                new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"),
                new TransactionTokenId("004b391f-1c98-43f8-87de-28b21aaaca00"))
            .await();

    assertNull(transactionToken.getData().asCardPaymentData().getCvvAuthorization());
  }

  @Test
  void shouldConvertUnknownCardBrand() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00",
        jwt,
        200,
        StoreFakeRR.getTransactionTokenFakeResponseCardBrand);

    TransactionTokenWithData response =
        univapay
            .getTransactionToken(
                new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"),
                new TransactionTokenId("004b391f-1c98-43f8-87de-28b21aaaca00"))
            .build()
            .dispatch();
    assertThat(response.getData().getCard().getBrandEnum(), is(CardBrand.UNKNOWN));
  }

  @Test
  void shouldReturnQRBrandInformationIfPresent() throws Exception {
    String fakeResponse = JsonLoader.loadJson("responses/transactiontoken/get-qrbrand.json");
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00",
        jwt,
        200,
        fakeResponse);

    TransactionTokenWithData response =
        univapay
            .getTransactionToken(
                new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"),
                new TransactionTokenId("004b391f-1c98-43f8-87de-28b21aaaca00"))
            .build()
            .dispatch();

    QrScanPaymentData data = response.getData().asQrScanData();
    assertThat(data.getGateway(), is(Gateway.ORIGAMI));
    assertThat(data.getBrand(), is(QrCpmBrand.Origami));
  }

  @Test
  void shouldIgnoreQRBrandInformationIfNull() throws Exception {
    String fakeResponse = JsonLoader.loadJson("responses/transactiontoken/get-qrbrand-null.json");
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00",
        jwt,
        200,
        fakeResponse);

    TransactionTokenWithData response =
        univapay
            .getTransactionToken(
                new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"),
                new TransactionTokenId("004b391f-1c98-43f8-87de-28b21aaaca00"))
            .build()
            .dispatch();

    QrScanPaymentData data = response.getData().asQrScanData();
    assertThat(data.getGateway(), is(Gateway.ORIGAMI));
    assertNull(data.getBrand());
  }

  @Test
  void shouldIgnoreQRBrandInformationIfUnknown() throws Exception {
    String fakeResponse =
        JsonLoader.loadJson("responses/transactiontoken/get-qrbrand-mpm-unknown.json");
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00",
        jwt,
        200,
        fakeResponse);

    TransactionTokenWithData response =
        univapay
            .getTransactionToken(
                new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"),
                new TransactionTokenId("004b391f-1c98-43f8-87de-28b21aaaca00"))
            .build()
            .dispatch();

    QrMerchantPaymentData data = response.getData().asQrMerchantPaymentData();
    assertThat(data.getQrImageUrl(), is("http://qr-image.png"));
    assertNull(data.getBrand());
  }

  @Test
  void shouldReturnIssuerTokenAndCallMethodIfAvailable() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00",
        jwt,
        200,
        StoreFakeRR.getNoEmailTransactionTokenWithOnlinePaymentFakeResponse);

    TransactionTokenWithData response =
        univapay
            .getTransactionToken(
                new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"),
                new TransactionTokenId("004b391f-1c98-43f8-87de-28b21aaaca00"))
            .build()
            .dispatch();

    OnlinePaymentData data = response.getData().asOnlinePaymentData();
    assertThat(data.getBrand(), is(OnlineBrand.TEST));
    assertThat(data.getCallMethod(), is(CallMethod.SDK));
    assertThat(data.getIssuerToken(), is("TOKEN"));
    assertThat(data.getUserIdentifier(), is("1234"));
  }
}
