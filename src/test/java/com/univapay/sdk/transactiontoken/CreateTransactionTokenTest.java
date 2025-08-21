package com.univapay.sdk.transactiontoken;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.builders.transactiontoken.TransactionTokensBuilders;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.auth.AppJWTStrategy;
import com.univapay.sdk.models.common.auth.AppTokenStrategy;
import com.univapay.sdk.models.response.transactiontoken.OnlinePaymentData;
import com.univapay.sdk.models.response.transactiontoken.PhoneNumber;
import com.univapay.sdk.models.response.transactiontoken.TransactionToken;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenWithData;
import com.univapay.sdk.types.*;
import com.univapay.sdk.types.brand.OnlineBrand;
import com.univapay.sdk.types.brand.QrMpmBrand;
import com.univapay.sdk.utils.*;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CreateTransactionTokenTest extends GenericTest {

  @Test
  void shouldPostAndReturnTransactionTokenInfoWithCreditCard() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createCardTransactionTokenFullResponse,
        StoreFakeRR.createCardTransactionTokenFullRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("float", 10.3);

    TransactionTokenWithData response =
        univapay
            .createTransactionToken(
                "some@email.com",
                new CreditCard("full name", "4556137309615276", 12, 2018, "599")
                    .addAddress(Country.JAPAN, null, "Tokyo", "somewhere", null, "111-1111"),
                TransactionTokenType.ONE_TIME)
            .withMetadata(requestMetadata)
            .withUseConfirmation(true)
            .withIpAddress("172.1.11.123")
            .build()
            .dispatch();

    assertEquals("004b391f-1c98-43f8-87de-28b21aaaca00", response.getId().toString());
    assertEquals("bf75472e-7f2d-4745-a66d-9b96ae031c7a", response.getStoreId().toString());
    assertEquals(ProcessingMode.TEST, response.getMode());
    Map<String, Object> responseMetadata = response.getMetadata();
    assertThat(responseMetadata.get("float"), is(10.3));

    assertEquals(response.getCreatedOn(), parsedDate);
    assertNull(response.getLastUsedOn());
    assertEquals(PaymentTypeName.CARD, response.getPaymentTypeName());
    assertEquals("full name", response.getData().getCard().getCardholder());
    assertEquals(12, response.getData().getCard().getExpMonth());
    assertEquals(2018, response.getData().getCard().getExpYear());
    assertEquals(5276, response.getData().getCard().getLastFour());
    assertEquals("visa", response.getData().getCard().getBrand());
    assertThat(response.getData().getCard().getBrandEnum(), is(CardBrand.VISA));
    assertEquals("somewhere", response.getData().getBilling().getLine1());
    assertNull(response.getData().getBilling().getLine2());
    assertNull(response.getData().getBilling().getState());
    assertEquals("TYO", response.getData().getBilling().getCity());
    assertEquals(Country.JAPAN, response.getData().getBilling().getCountryEnum());
    assertEquals("111-1111", response.getData().getBilling().getZip());
    assertThat(response.getConfirmed(), is(false));
  }

  private TransactionTokensBuilders.CreateTransactionTokenRequestBuilder createBuilder() {
    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);
    return univapay.createTransactionToken(
        "some@email.com",
        new CreditCard("full name", "4556137309615276", 12, 2018, "599")
            .addAddress(Country.JAPAN, null, "Tokyo", "somewhere", null, "111-1111"),
        TransactionTokenType.ONE_TIME);
  }

  @Test
  void shouldIncludeCustomerId() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createTransactionTokenForCustomerResponse,
        StoreFakeRR.createTransactionTokenForCustomerRequest);

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("float", "10.3");

    UUID customerId = UUID.fromString("7680e246-2d10-42bf-8bbb-2230e1ed712c");

    //      The request should be the same whether you set the metadata first...
    TransactionToken transactionToken =
        createBuilder()
            .withMetadata(requestMetadata)
            .withCustomerId(new UnivapayCustomerId(customerId))
            .build()
            .dispatch();

    assertEquals(customerId.toString(), transactionToken.getMetadata().get("customer_id"));

    //      Or the customer Id first.
    TransactionToken transactionToken2 =
        createBuilder()
            .withCustomerId(
                new UnivapayCustomerId(UUID.fromString("7680e246-2d10-42bf-8bbb-2230e1ed712c")))
            .withMetadata(requestMetadata)
            .build()
            .dispatch();

    assertEquals(customerId.toString(), transactionToken2.getMetadata().get("customer_id"));
  }

  @Test
  void shouldPostAndReturnTransactionTokenInfoWithCreditCardHasNoCVV() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createTransactionTokenFakeResponse,
        StoreFakeRR.createTransactionTokenWithoutCVVFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    univapay
        .createTransactionToken(
            "some@email.com",
            new CreditCard("full name", "4556137309615276", 12, 2018)
                .addAddress(Country.JAPAN, null, "Tokyo", "somewhere", null, "111-1111"),
            TransactionTokenType.ONE_TIME)
        .build()
        .dispatch(
            new UnivapayCallback<TransactionTokenWithData>() {
              @Override
              public void getResponse(TransactionTokenWithData response) {
                assertEquals("004b391f-1c98-43f8-87de-28b21aaaca00", response.getId().toString());
                assertEquals(
                    "bf75472e-7f2d-4745-a66d-9b96ae031c7a", response.getStoreId().toString());
                assertEquals(ProcessingMode.TEST, response.getMode());
                assertEquals(response.getCreatedOn(), parsedDate);
                assertNull(response.getLastUsedOn());
                assertEquals(PaymentTypeName.CARD, response.getPaymentTypeName());
                assertEquals("full name", response.getData().getCard().getCardholder());
                assertEquals(12, response.getData().getCard().getExpMonth());
                assertEquals(2018, response.getData().getCard().getExpYear());
                assertEquals(5276, response.getData().getCard().getLastFour());
                assertEquals("visa", response.getData().getCard().getBrand());
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
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  void shouldPostAndReturnTransactionTokenInfoWithQrScan() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createTransactionTokenWithQrScanFakeResponse,
        StoreFakeRR.createTransactionTokenWithQrScanFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    univapay
        .createTransactionToken(
            "some@email.com", new QrScanData("oiajsdfipojasdfipas"), TransactionTokenType.ONE_TIME)
        .build()
        .dispatch(
            new UnivapayCallback<TransactionTokenWithData>() {
              @Override
              public void getResponse(TransactionTokenWithData response) {
                assertEquals("004b391f-1c98-43f8-87de-28b21aaaca00", response.getId().toString());
                assertEquals(
                    "bf75472e-7f2d-4745-a66d-9b96ae031c7a", response.getStoreId().toString());
                assertEquals(ProcessingMode.TEST, response.getMode());
                assertEquals(response.getCreatedOn(), parsedDate);
                assertNull(response.getLastUsedOn());
                assertEquals(PaymentTypeName.QR_SCAN, response.getPaymentTypeName());
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });
    waitCall();
  }

  @Test
  void shouldPostAndReturnRecurringTransactionTokenWithUsageLimit() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createRecurringTransactionTokenFakeResponse,
        StoreFakeRR.createRecurringTransactionTokenFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    univapay
        .createTransactionToken(
            "some@email.com",
            new CreditCard("full name", "4556137309615276", 12, 2018, "599")
                .addAddress(Country.JAPAN, null, "Tokyo", "somewhere", null, "111-1111"),
            TransactionTokenType.RECURRING)
        .withUsageLimit(RecurringTokenInterval.WEEKLY)
        .build()
        .dispatch(
            new UnivapayCallback<TransactionTokenWithData>() {
              @Override
              public void getResponse(TransactionTokenWithData response) {
                assertEquals("004b391f-1c98-43f8-87de-28b21aaaca00", response.getId().toString());
                assertEquals(
                    "bf75472e-7f2d-4745-a66d-9b96ae031c7a", response.getStoreId().toString());
                assertEquals(ProcessingMode.TEST, response.getMode());
                assertEquals(response.getCreatedOn(), parsedDate);
                assertNull(response.getLastUsedOn());
                assertEquals(PaymentTypeName.CARD, response.getPaymentTypeName());
                assertEquals("full name", response.getData().getCard().getCardholder());
                assertEquals(12, response.getData().getCard().getExpMonth());
                assertEquals(2018, response.getData().getCard().getExpYear());
                assertEquals(5276, response.getData().getCard().getLastFour());
                assertEquals("visa", response.getData().getCard().getBrand());
                assertEquals("somewhere", response.getData().getBilling().getLine1());
                assertNull(response.getData().getBilling().getLine2());
                assertNull(response.getData().getBilling().getState());
                assertEquals("TYO", response.getData().getBilling().getCity());
                assertEquals("JP", response.getData().getBilling().getCountry());
                assertEquals("111-1111", response.getData().getBilling().getZip());
                assertEquals(RecurringTokenInterval.WEEKLY, response.getUsageLimit());
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  void shouldPostAndReturnTransactionTokenWithApplePayData() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createTransactionTokenWithApplePayFakeResponse,
        StoreFakeRR.createTransactionTokenWithApplePayFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    univapay
        .createTransactionToken(
            "some@email.com",
            new ApplePay("sometoken", "someperson")
                .addAddress(Country.JAPAN, null, "Tokyo", "somewhere", null, "111-1111"),
            TransactionTokenType.ONE_TIME)
        .build()
        .dispatch(
            new UnivapayCallback<TransactionTokenWithData>() {
              @Override
              public void getResponse(TransactionTokenWithData response) {
                assertEquals("004b391f-1c98-43f8-87de-28b21aaaca00", response.getId().toString());
                assertEquals(
                    "bf75472e-7f2d-4745-a66d-9b96ae031c7a", response.getStoreId().toString());
                assertEquals(ProcessingMode.TEST, response.getMode());
                assertEquals(response.getCreatedOn(), parsedDate);
                assertNull(response.getLastUsedOn());
                assertEquals(PaymentTypeName.APPLE_PAY, response.getPaymentTypeName());
                assertEquals(
                    "someperson", response.getData().asCardPaymentData().getCard().getCardholder());
                assertEquals(12, response.getData().asCardPaymentData().getCard().getExpMonth());
                assertEquals(2018, response.getData().asCardPaymentData().getCard().getExpYear());
                assertEquals(5276, response.getData().asCardPaymentData().getCard().getLastFour());
                assertEquals("visa", response.getData().asCardPaymentData().getCard().getBrand());
                assertEquals(
                    "somewhere", response.getData().asCardPaymentData().getBilling().getLine1());
                assertNull(response.getData().asCardPaymentData().getBilling().getLine2());
                assertNull(response.getData().asCardPaymentData().getBilling().getState());
                assertEquals("TYO", response.getData().asCardPaymentData().getBilling().getCity());
                assertEquals(
                    Country.JAPAN,
                    response.getData().asCardPaymentData().getBilling().getCountryEnum());
                assertEquals(
                    "111-1111", response.getData().asCardPaymentData().getBilling().getZip());
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  void shouldPostLegacyCountryWithApplePayData() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createTransactionTokenWithApplePayFakeResponse,
        StoreFakeRR.createTransactionTokenWithApplePayFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    univapay
        .createTransactionToken(
            "some@email.com",
            new ApplePay("sometoken", "someperson")
                .addAddress("JP", null, "Tokyo", "somewhere", null, "111-1111"),
            TransactionTokenType.ONE_TIME)
        .build()
        .dispatch(
            new UnivapayCallback<TransactionTokenWithData>() {
              @Override
              public void getResponse(TransactionTokenWithData response) {
                assertEquals(
                    "JP", response.getData().asCardPaymentData().getBilling().getCountry());
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  void shouldPostAndReturnTransactionTokenWithKonbiniPaymentData() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createTransactionTokenWithKonbiniPaymentFakeResponse,
        StoreFakeRR.createTransactionTokenWithKonbiniPaymentFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    final Duration expirationPeriod = Duration.ofDays(9);

    univapay
        .createTransactionToken(
            "some@email.com",
            new KonbiniPayment(
                "okyakusama",
                Konbini.FAMILY_MART,
                expirationPeriod,
                new PhoneNumber(55, "4799318900")),
            TransactionTokenType.ONE_TIME)
        .build()
        .dispatch(
            new UnivapayCallback<TransactionTokenWithData>() {
              @Override
              public void getResponse(TransactionTokenWithData response) {
                assertEquals("004b391f-1c98-43f8-87de-28b21aaaca00", response.getId().toString());
                assertEquals(
                    "bf75472e-7f2d-4745-a66d-9b96ae031c7a", response.getStoreId().toString());
                assertEquals(ProcessingMode.TEST, response.getMode());
                assertEquals(response.getCreatedOn(), parsedDate);
                assertNull(response.getLastUsedOn());
                assertEquals(PaymentTypeName.KONBINI, response.getPaymentTypeName());
                assertEquals(
                    "okyakusama", response.getData().asKonbiniPaymentData().getCustomerName());
                assertEquals(
                    Konbini.FAMILY_MART,
                    response.getData().asKonbiniPaymentData().getConvenienceStore());
                assertEquals(
                    response.getData().asKonbiniPaymentData().getExpirationPeriod(),
                    expirationPeriod);
                assertEquals(
                    55,
                    response
                        .getData()
                        .asKonbiniPaymentData()
                        .getPhoneNumber()
                        .getCountryCode()
                        .intValue());
                assertEquals(
                    "4799318900",
                    response.getData().asKonbiniPaymentData().getPhoneNumber().getLocalNumber());
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  void shouldPostAndReturnTransactionTokenWithKonbiniPaymentDataWithoutExpiration()
      throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        "{}",
        StoreFakeRR.createTransactionTokenWithKonbiniPaymentNoExpirationFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    univapay
        .createTransactionToken(
            "some@email.com",
            new KonbiniPayment(
                "okyakusama", Konbini.FAMILY_MART, new PhoneNumber(55, "4799318900")),
            TransactionTokenType.ONE_TIME)
        .build()
        .dispatch();
  }

  @Test
  void shouldPostLegacyCountry() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createTransactionTokenFakeResponse,
        StoreFakeRR.createTransactionTokenFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("float", "10.3");

    univapay
        .createTransactionToken(
            "some@email.com",
            new CreditCard("full name", "4556137309615276", 12, 2018, "599")
                .addAddress(Country.JAPAN, null, "Tokyo", "somewhere", null, "111-1111"),
            TransactionTokenType.ONE_TIME)
        .withMetadata(requestMetadata)
        .withUseConfirmation(true)
        .build()
        .dispatch(
            new UnivapayCallback<TransactionTokenWithData>() {
              @Override
              public void getResponse(TransactionTokenWithData response) {
                assertEquals("JP", response.getData().getBilling().getCountry());
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  void shouldPostTransactionTokenInfoUniqueMetadata() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createTransactionTokenFakeResponse,
        StoreFakeRR.createTransactionTokenFakeRequest);

    try (UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN)) {

      Map<String, Object> requestMetadata = new HashMap<>();
      requestMetadata.put("float", 10.3);

      TransactionTokenWithData response =
          univapay
              .createTransactionToken(
                  "some@email.com",
                  new CreditCard("full name", "4556137309615276", 12, 2018, "599")
                      .addAddress(Country.JAPAN, null, "Tokyo", "somewhere", null, "111-1111"),
                  TransactionTokenType.ONE_TIME)
              .withMetadata(requestMetadata)
              .withUseConfirmation(true)
              .build()
              .dispatch();

      assertThat(response.getMetadata(), is(requestMetadata));
    }
  }

  @Test
  void shouldPostTransactionTokenWithAppJTWWithoutSecret() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    Map<String, String> headerMap = new HashMap<>();
    final String origin = "https://www.test-origin.com";
    headerMap.put("Origin", origin);
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/tokens",
        jwt,
        200,
        StoreFakeRR.createTransactionTokenFakeResponse,
        StoreFakeRR.createTransactionTokenFakeRequest,
        headerMap);

    UnivapaySDK univapay =
        UnivapaySDK.create(
            new AppJWTStrategy(jwt),
            new UnivapayDebugSettings()
                .attachOrigin(origin)
                .withEndpoint(TEST_ENDPOINT)
                .withRequestsLogging(true));

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    final Map<String, Object> metadata = new LinkedHashMap<>();
    metadata.put("float", 10.3);

    TransactionTokenWithData response =
        univapay
            .createTransactionToken(
                "some@email.com",
                new CreditCard("full name", "4556137309615276", 12, 2018, "599")
                    .addAddress(Country.JAPAN, null, "Tokyo", "somewhere", null, "111-1111"),
                TransactionTokenType.ONE_TIME)
            .withMetadata(metadata)
            .withUseConfirmation(true)
            .build()
            .dispatch();

    assertEquals("004b391f-1c98-43f8-87de-28b21aaaca00", response.getId().toString());
    assertEquals("bf75472e-7f2d-4745-a66d-9b96ae031c7a", response.getStoreId().toString());
    assertEquals(ProcessingMode.TEST, response.getMode());
    assertThat(response.getMetadata().get("float"), is(10.3));

    assertEquals(response.getCreatedOn(), parsedDate);
    assertNull(response.getLastUsedOn());
    assertEquals(PaymentTypeName.CARD, response.getPaymentTypeName());
    assertEquals("full name", response.getData().getCard().getCardholder());
    assertEquals(12, response.getData().getCard().getExpMonth());
    assertEquals(2018, response.getData().getCard().getExpYear());
    assertEquals(5276, response.getData().getCard().getLastFour());
    assertEquals("visa", response.getData().getCard().getBrand());
    assertThat(response.getData().getCard().getBrandEnum(), is(CardBrand.VISA));
    assertEquals("somewhere", response.getData().getBilling().getLine1());
    assertNull(response.getData().getBilling().getLine2());
    assertNull(response.getData().getBilling().getState());
    assertEquals("TYO", response.getData().getBilling().getCity());
    assertEquals(Country.JAPAN, response.getData().getBilling().getCountryEnum());
    assertEquals("111-1111", response.getData().getBilling().getZip());
  }

  @Test
  void shouldPostTransactionTokenWithAppTokenWithoutSecret() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    Map<String, String> headerMap = new HashMap<>();
    final String origin = "https://www.test-origin.com";
    headerMap.put("Origin", origin);
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        null,
        200,
        StoreFakeRR.createTransactionTokenFakeResponse,
        StoreFakeRR.createTransactionTokenFakeRequest,
        headerMap);

    UnivapaySDK univapay =
        UnivapaySDK.create(
            new AppTokenStrategy(appToken),
            new UnivapayDebugSettings()
                .attachOrigin(origin)
                .withEndpoint(TEST_ENDPOINT)
                .withRequestsLogging(true));

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("float", 10.3);

    TransactionTokenWithData response =
        univapay
            .createTransactionToken(
                "some@email.com",
                new CreditCard("full name", "4556137309615276", 12, 2018, "599")
                    .addAddress(Country.JAPAN, null, "Tokyo", "somewhere", null, "111-1111"),
                TransactionTokenType.ONE_TIME)
            .withMetadata(requestMetadata)
            .withUseConfirmation(true)
            .build()
            .dispatch();
    assertEquals("004b391f-1c98-43f8-87de-28b21aaaca00", response.getId().toString());
    assertEquals("bf75472e-7f2d-4745-a66d-9b96ae031c7a", response.getStoreId().toString());
    assertEquals(ProcessingMode.TEST, response.getMode());
    assertThat(response.getMetadata().get("float"), is(10.3));

    assertEquals(response.getCreatedOn(), parsedDate);
    assertNull(response.getLastUsedOn());
    assertEquals(PaymentTypeName.CARD, response.getPaymentTypeName());
    assertEquals("full name", response.getData().getCard().getCardholder());
    assertEquals(12, response.getData().getCard().getExpMonth());
    assertEquals(2018, response.getData().getCard().getExpYear());
    assertEquals(5276, response.getData().getCard().getLastFour());
    assertEquals("visa", response.getData().getCard().getBrand());
    assertThat(response.getData().getCard().getBrandEnum(), is(CardBrand.VISA));
    assertEquals("somewhere", response.getData().getBilling().getLine1());
    assertNull(response.getData().getBilling().getLine2());
    assertNull(response.getData().getBilling().getState());
    assertEquals("TYO", response.getData().getBilling().getCity());
    assertEquals(Country.JAPAN, response.getData().getBilling().getCountryEnum());
    assertEquals("111-1111", response.getData().getBilling().getZip());
  }

  @Test
  void shouldPostAndReturnTransactionTokenWithPaidyPaymentData() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createTransactionTokenWithPaidyFakeResponse,
        StoreFakeRR.createTransactionTokenWithPaidyFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    final String email = "paidy-test@univapay.com";
    final PaidyShippingAddress shippingAddress =
        new PaidyShippingAddress("106-0032", "東京都", "港区", "六本木", "1-1-1");

    univapay
        .createTransactionToken(
            email,
            new PaidyPaymentData("test-Paidy-token", shippingAddress)
                .withPhoneNumber(new PhoneNumber(81, "12345678")),
            TransactionTokenType.ONE_TIME)
        .build()
        .dispatch(
            new UnivapayCallback<TransactionTokenWithData>() {
              @Override
              public void getResponse(TransactionTokenWithData response) {
                assertThat(response.getId().toString(), is("11e8dcf3-1c95-be98-a370-5fb11e03b325"));
                assertThat(
                    response.getStoreId().toString(), is("11e8dcdb-52a6-bf5e-b126-277449999f80"));
                assertThat(response.getEmail(), is(email));
                assertThat(response.getMode(), is(ProcessingMode.TEST));
                assertThat(response.getCreatedOn(), is(parsedDate));
                assertNull(response.getLastUsedOn());
                assertThat(
                    response.getData().asPaidyPaymentData().getPaymentType(),
                    is(PaymentTypeName.PAIDY));
                assertThat(
                    response.getData().asPaidyPaymentData().getPaidyToken(),
                    is(new PaidyToken("test-Paidy-token")));
                assertThat(
                    response.getData().asPaidyPaymentData().getPhoneNumber().getCountryCode(),
                    is(81));
                assertThat(
                    response.getData().asPaidyPaymentData().getPhoneNumber().getLocalNumber(),
                    is("12345678"));
                assertThat(
                    response.getData().asPaidyPaymentData().getShippingAddress().getState(),
                    is(shippingAddress.getState()));
                assertThat(
                    response.getData().asPaidyPaymentData().getShippingAddress().getCity(),
                    is(shippingAddress.getCity()));
                assertThat(
                    response.getData().asPaidyPaymentData().getShippingAddress().getLine1(),
                    is(shippingAddress.getLine1()));
                assertThat(
                    response.getData().asPaidyPaymentData().getShippingAddress().getLine2(),
                    is(shippingAddress.getLine2()));
                assertThat(
                    response.getData().asPaidyPaymentData().getShippingAddress().getPostalCode(),
                    is(shippingAddress.getPostalCode()));
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
  void shouldPostAndReturnNoEmailTransactionTokenWithPaidyPaymentData() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createNoEmailTransactionTokenWithPaidyFakeResponse,
        StoreFakeRR.createNoEmailTransactionTokenWithPaidyFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    final PaidyShippingAddress shippingAddress =
        new PaidyShippingAddress("106-0032")
            .addState("東京都")
            .addCity("港区")
            .addAddressDetails("六本木", "1-1-1");

    TransactionTokenWithData token =
        univapay
            .createTransactionToken(
                new PaidyPaymentData("test-Paidy-token", shippingAddress)
                    .withPhoneNumber(new PhoneNumber(81, "12345678")),
                TransactionTokenType.ONE_TIME)
            .build()
            .dispatch();

    assertThat(token.getId().toString(), is("11e8dcf3-1c95-be98-a370-5fb11e03b325"));
    assertThat(token.getStoreId().toString(), is("11e8dcdb-52a6-bf5e-b126-277449999f80"));
    assertNull(token.getEmail());
    assertThat(token.getMode(), is(ProcessingMode.TEST));
    assertThat(token.getCreatedOn(), is(parsedDate));
    assertNull(token.getLastUsedOn());
    assertThat(token.getData().asPaidyPaymentData().getPaymentType(), is(PaymentTypeName.PAIDY));
    assertThat(
        token.getData().asPaidyPaymentData().getPaidyToken(),
        is(new PaidyToken("test-Paidy-token")));
    assertThat(token.getData().asPaidyPaymentData().getPhoneNumber().getCountryCode(), is(81));
    assertThat(
        token.getData().asPaidyPaymentData().getPhoneNumber().getLocalNumber(), is("12345678"));
    assertThat(
        token.getData().asPaidyPaymentData().getShippingAddress().getState(),
        is(shippingAddress.getState()));
    assertThat(
        token.getData().asPaidyPaymentData().getShippingAddress().getCity(),
        is(shippingAddress.getCity()));
    assertThat(
        token.getData().asPaidyPaymentData().getShippingAddress().getLine1(),
        is(shippingAddress.getLine1()));
    assertThat(
        token.getData().asPaidyPaymentData().getShippingAddress().getLine2(),
        is(shippingAddress.getLine2()));
    assertThat(
        token.getData().asPaidyPaymentData().getShippingAddress().getPostalCode(),
        is(shippingAddress.getPostalCode()));
  }

  @Test
  void shouldPostAndReturnNoEmailTransactionTokenWithQrScanData() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createNoEmailTransactionTokenWithQrFakeResponse,
        StoreFakeRR.createNoEmailTransactionTokenWithQrFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    TransactionTokenWithData token =
        univapay
            .createTransactionToken(
                new QrScanData("2088234789789462"), TransactionTokenType.ONE_TIME)
            .build()
            .dispatch();
    assertThat(token.getId().toString(), is("11e8dcf3-1c95-be98-a370-5fb11e03b325"));
    assertThat(token.getStoreId().toString(), is("11e8dcdb-52a6-bf5e-b126-277449999f80"));
    assertNull(token.getEmail());
    assertThat(token.getMode(), is(ProcessingMode.TEST));
    assertThat(token.getCreatedOn(), is(parsedDate));
    assertNull(token.getLastUsedOn());
    assertThat(token.getPaymentTypeName(), is(PaymentTypeName.QR_SCAN));
    assertThat(token.getData().asQrScanData().getGateway(), Matchers.is(Gateway.ALIPAY));
  }

  @Test
  void shouldPostAndReturnTransactionTokenWithQrMerchantPaymentData() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createTransactionTokenWithQrMerchantFakeResponse,
        StoreFakeRR.createTransactionTokenWithQrMerchantFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    final String email = "test@univapay.com";
    final QrMerchantData paymentData = new QrMerchantData(QrMpmBrand.Alipay);

    univapay
        .createTransactionToken(email, paymentData, TransactionTokenType.ONE_TIME)
        .build()
        .dispatch(
            new UnivapayCallback<TransactionTokenWithData>() {
              @Override
              public void getResponse(TransactionTokenWithData response) {
                assertThat(response.getId().toString(), is("11e8dcf3-1c95-be98-a370-5fb11e03b325"));
                assertThat(
                    response.getStoreId().toString(), is("11e8dcdb-52a6-bf5e-b126-277449999f80"));
                assertThat(response.getEmail(), is(email));
                assertThat(response.getMode(), is(ProcessingMode.TEST));
                assertThat(response.getCreatedOn(), is(parsedDate));
                assertNull(response.getLastUsedOn());
                assertThat(response.getPaymentTypeName(), is(PaymentTypeName.QR_MERCHANT));
                assertThat(
                    response.getData().asQrMerchantPaymentData().getQrImageUrl(),
                    is("http://qr-image.png"));
                assertThat(
                    response.getData().asQrMerchantPaymentData().getBrand(), is(QrMpmBrand.Alipay));
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
  void shouldPostAndReturnTransactionTokenWithQrMerchantPaymentDataForAlipayConnect() {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createTransactionTokenWithQrMerchantAlipayConnectFakeResponse,
        StoreFakeRR.createTransactionTokenWithQrMerchantAlipayConnectFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    final QrMerchantData paymentData = new QrMerchantData(QrMpmBrand.AlipayConnect);

    Assertions.assertDoesNotThrow(
        () -> {
          TransactionTokenWithData response =
              univapay
                  .createTransactionToken(paymentData, TransactionTokenType.ONE_TIME)
                  .build()
                  .dispatch();

          assertThat(response.getId().toString(), is("11e8dcf3-1c95-be98-a370-5fb11e03b325"));
          assertThat(response.getStoreId().toString(), is("11e8dcdb-52a6-bf5e-b126-277449999f80"));
          assertNull(response.getEmail());
          assertThat(response.getMode(), is(ProcessingMode.TEST));
          assertThat(response.getCreatedOn(), is(parsedDate));
          assertNull(response.getLastUsedOn());
          assertThat(response.getPaymentTypeName(), is(PaymentTypeName.QR_MERCHANT));
          assertNull(response.getData().asQrMerchantPaymentData().getQrImageUrl());

          assertThat(
              response.getData().asQrMerchantPaymentData().getBrand(),
              is(QrMpmBrand.AlipayConnect));

          // To Generate the QrCodeUrl, Continue with the Charge creation...

        },
        "Failing with unexpected exception: ");
  }

  @Test
  void shouldPostAndReturnNoEmailTransactionTokenWithQrMerchantData() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createNoEmailTransactionTokenWithQrMerchantFakeResponse,
        StoreFakeRR.createNoEmailTransactionTokenWithQrMerchantFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    final QrMerchantData paymentData = new QrMerchantData(QrMpmBrand.Alipay);
    TransactionTokenWithData token =
        univapay
            .createTransactionToken(paymentData, TransactionTokenType.ONE_TIME)
            .build()
            .dispatch();
    assertThat(token.getId().toString(), is("11e8dcf3-1c95-be98-a370-5fb11e03b325"));
    assertThat(token.getStoreId().toString(), is("11e8dcdb-52a6-bf5e-b126-277449999f80"));
    assertNull(token.getEmail());
    assertThat(token.getMode(), is(ProcessingMode.TEST));
    assertThat(token.getCreatedOn(), is(parsedDate));
    assertNull(token.getLastUsedOn());
    assertThat(token.getPaymentTypeName(), is(PaymentTypeName.QR_MERCHANT));
    assertThat(
        token.getData().asQrMerchantPaymentData().getQrImageUrl(), is("http://qr-image.png"));

    assertThat(token.getData().asQrMerchantPaymentData().getBrand(), is(QrMpmBrand.Alipay));
  }

  @Test
  void shouldBeAbleToCreateTransactionTokenWithOnlinePaymentData() throws Exception {

    final OffsetDateTime expectedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createNoEmailTransactionTokenWithOnlinePaymentFakeResponse,
        StoreFakeRR.createNoEmailTransactionTokenWithOnlinePaymentFakeRequest);

    UnivapaySDK sdk = createTestInstance(AuthType.APP_TOKEN);

    // To create a Online Payment, the only thing required is the intended payment processor
    OnlinePayment test = new OnlinePayment(OnlineBrand.TEST);

    // Create the Online Payment
    TransactionTokenWithData token =
        sdk.createTransactionToken(test, TransactionTokenType.ONE_TIME).dispatch();

    assertThat(token.getId().toString(), is("004b391f-1c98-43f8-87de-28b21aaaca00"));
    assertThat(token.getStoreId().toString(), is("bf75472e-7f2d-4745-a66d-9b96ae031c7a"));
    assertNull(token.getEmail());
    assertThat(token.getMode(), is(ProcessingMode.LIVE));
    assertThat(token.getCreatedOn(), is(expectedDate));
    assertNull(token.getLastUsedOn());
    assertThat(token.getPaymentTypeName(), is(PaymentTypeName.ONLINE));

    OnlinePaymentData onlinePaymentData = token.getData().asOnlinePaymentData();
    assertNotNull(onlinePaymentData);
    assertThat(onlinePaymentData.getBrand(), is(OnlineBrand.TEST));
    // These will be provided if the charge is successfully processed to the Deferred status
    assertNull(onlinePaymentData.getCallMethod());
    assertNull(onlinePaymentData.getCallMethod());
  }

  @Test
  void shouldCreateTransactionTokenWithFullOnlinePaymentData() throws Exception {

    final OffsetDateTime expectedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createFullTransactionTokenWithOnlinePaymentFakeResponse,
        StoreFakeRR.createFullTransactionTokenWithOnlinePaymentFakeRequest);

    UnivapaySDK sdk = createTestInstance(AuthType.APP_TOKEN);

    OnlinePayment test =
        new OnlinePayment(OnlineBrand.TEST)
            .withCallMethod(CallMethod.SDK)
            .withUserIdentifier("1234");

    // Create the Online Payment
    TransactionTokenWithData token =
        sdk.createTransactionToken(test, TransactionTokenType.ONE_TIME).dispatch();

    assertThat(token.getId().toString(), is("004b391f-1c98-43f8-87de-28b21aaaca00"));
    assertThat(token.getStoreId().toString(), is("bf75472e-7f2d-4745-a66d-9b96ae031c7a"));
    assertNull(token.getEmail());
    assertThat(token.getMode(), is(ProcessingMode.LIVE));
    assertThat(token.getCreatedOn(), is(expectedDate));
    assertNull(token.getLastUsedOn());
    assertThat(token.getPaymentTypeName(), is(PaymentTypeName.ONLINE));

    OnlinePaymentData onlinePaymentData = token.getData().asOnlinePaymentData();
    assertNotNull(onlinePaymentData);
    assertThat(onlinePaymentData.getBrand(), is(OnlineBrand.TEST));
    assertThat(onlinePaymentData.getCallMethod(), is(CallMethod.SDK));
    assertThat(onlinePaymentData.getUserIdentifier(), is("1234"));
  }
}
