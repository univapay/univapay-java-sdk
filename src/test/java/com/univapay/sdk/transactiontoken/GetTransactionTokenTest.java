package com.univapay.sdk.transactiontoken;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.CallMethod;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.charge.CvvAuthorization;
import com.univapay.sdk.models.common.charge.CvvAuthorizationStatus;
import com.univapay.sdk.models.errors.UnivapayException;
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
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import org.junit.Test;

public class GetTransactionTokenTest extends GenericTest {

  private final UnivapaySDK univapay = createTestInstance(AuthType.JWT);

  @Test
  public void shouldRequestAndReturnTransactionTokenInfo() throws InterruptedException {
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
                assertEquals(response.getId().toString(), "004b391f-1c98-43f8-87de-28b21aaaca00");
                assertEquals(
                    response.getStoreId().toString(), "bf75472e-7f2d-4745-a66d-9b96ae031c7a");
                assertEquals(response.getMode(), ProcessingMode.LIVE);
                assertEquals(response.getCreatedOn(), parsedDate);
                assertNull(response.getLastUsedOn());
                assertEquals(response.getPaymentTypeName(), PaymentTypeName.CARD);
                assertEquals(response.getData().getCard().getCardholder(), "full name");
                assertEquals(response.getData().getCard().getExpMonth(), 12);
                assertEquals(response.getData().getCard().getExpYear(), 2018);
                assertEquals(response.getData().getCard().getLastFour(), 5276);
                assertEquals(response.getData().getCard().getBrandEnum(), CardBrand.VISA);
                assertThat(response.getData().getCard().getCategory(), is(CardCategory.CLASSIC));
                assertThat(response.getData().getCard().getIssuer(), is("test issuer"));
                assertThat(response.getData().getCard().getSubBrand(), is(CardSubBrand.NONE));
                assertEquals(response.getData().getBilling().getLine1(), "somewhere");
                assertNull(response.getData().getBilling().getLine2());
                assertNull(response.getData().getBilling().getState());
                assertEquals(response.getData().getBilling().getCity(), "TYO");
                assertEquals(response.getData().getBilling().getCountry(), "JP");
                assertEquals(response.getData().getBilling().getZip(), "111-1111");
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
  public void shouldBeAbleToReadThePrivateBrandLabel() throws UnivapayException, IOException {
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

    assertEquals(response.getId().toString(), "004b391f-1c98-43f8-87de-28b21aaaca00");
    assertEquals(response.getStoreId().toString(), "bf75472e-7f2d-4745-a66d-9b96ae031c7a");
    assertEquals(response.getMode(), ProcessingMode.LIVE);
    assertEquals(response.getCreatedOn(), parsedDate);
    assertNull(response.getLastUsedOn());
    assertEquals(response.getPaymentTypeName(), PaymentTypeName.CARD);
    assertEquals(response.getData().getCard().getCardholder(), "UNIVAPAY TEST");
    assertEquals(response.getData().getCard().getExpMonth(), 12);
    assertEquals(response.getData().getCard().getExpYear(), 2025);
    assertEquals(response.getData().getCard().getLastFour(), 6020);
    assertEquals(response.getData().getCard().getBrandEnum(), CardBrand.PRIVATE_LABEL);
    assertNull(response.getData().getCard().getCategory());
    assertThat(response.getData().getCard().getIssuer(), is("JEONBUK BANK"));
    assertThat(response.getData().getCard().getSubBrand(), is(CardSubBrand.NONE));
    assertEquals(response.getData().getBilling().getLine1(), "somewhere");
    assertNull(response.getData().getBilling().getLine2());
    assertNull(response.getData().getBilling().getState());
    assertEquals(response.getData().getBilling().getCity(), "TYO");
    assertEquals(response.getData().getBilling().getCountry(), "JP");
    assertEquals(response.getData().getBilling().getZip(), "111-1111");
  }

  @Test
  public void shouldBeAbleToReadTheCvvAuthorizationData() throws UnivapayException, IOException {

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
  public void shouldBeAbleToReadTheCvvAuthorizationDataResourceMonitor()
      throws UnivapayException, IOException, InterruptedException, TimeoutException {

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
  public void shouldBeAbleToReadCvvAuthorizationDataCurrent()
      throws UnivapayException, IOException {
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
  public void resourceMonitorForCvvAuthMustIgnoreEnableAsNullOrFalse()
      throws UnivapayException, IOException, InterruptedException, TimeoutException {
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
  public void shouldConvertUnknownCardBrand() throws IOException, UnivapayException {
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
  public void shouldReturnQRBrandInformationIfPresent() throws IOException, UnivapayException {
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
  public void shouldIgnoreQRBrandInformationIfNull() throws IOException, UnivapayException {
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
    assertThat(data.getBrand(), is(nullValue()));
  }

  @Test
  public void shouldIgnoreQRBrandInformationIfUnknown() throws IOException, UnivapayException {
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
    assertThat(data.getBrand(), is(nullValue()));
  }

  @Test
  public void shouldReturnIssuerTokenAndCallMethodIfAvailable()
      throws IOException, UnivapayException {
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
