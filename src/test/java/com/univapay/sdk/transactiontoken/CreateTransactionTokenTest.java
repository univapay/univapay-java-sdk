package com.univapay.sdk.transactiontoken;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.builders.transactiontoken.TransactionTokensBuilders;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.OnlinePayment;
import com.univapay.sdk.models.common.auth.AppJWTStrategy;
import com.univapay.sdk.models.common.auth.AppTokenStrategy;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.transactiontoken.OnlinePaymentData;
import com.univapay.sdk.models.response.transactiontoken.PhoneNumber;
import com.univapay.sdk.models.response.transactiontoken.TransactionToken;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenWithData;
import com.univapay.sdk.types.*;
import com.univapay.sdk.types.brand.OnlineBrand;
import com.univapay.sdk.types.brand.QrMpmBrand;
import com.univapay.sdk.utils.*;
import com.univapay.sdk.utils.metadataadapter.MetadataFloatAdapter;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.hamcrest.core.Is;
import org.junit.Test;

public class CreateTransactionTokenTest extends GenericTest {
  static final MetadataMap metadata = new MetadataMap();
  static final String floatKey = "float";
  static final String floatValue = "10.3";

  static {
    metadata.put(floatKey, floatValue);
  }

  @Test
  public void shouldPostAndReturnTransactionTokenInfoWithCreditCard() throws InterruptedException {
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

    final MetadataMap metadata = new MetadataMap();
    final String floatKey = "float";
    final String floatValue = "10.3";
    metadata.put(floatKey, floatValue);

    univapay
        .createTransactionToken(
            "some@email.com",
            new CreditCard("full name", "4556137309615276", 12, 2018, 599)
                .addAddress(Country.JAPAN, null, "Tokyo", "somewhere", null, "111-1111"),
            TransactionTokenType.ONE_TIME)
        .withMetadata(metadata)
        .withUseConfirmation(true)
        .withIpAddress("172.1.11.123")
        .build()
        .dispatch(
            new UnivapayCallback<TransactionTokenWithData>() {
              @Override
              public void getResponse(TransactionTokenWithData response) {
                assertEquals(response.getId().toString(), "004b391f-1c98-43f8-87de-28b21aaaca00");
                assertEquals(
                    response.getStoreId().toString(), "bf75472e-7f2d-4745-a66d-9b96ae031c7a");
                assertEquals(response.getMode(), ProcessingMode.TEST);
                assertThat(response.getMetadata().get(floatKey), is(floatValue));
                MetadataFloatAdapter adapter = new MetadataFloatAdapter();
                assertThat(
                    response.getMetadata(adapter).get(floatKey), is(Float.valueOf(floatValue)));
                assertEquals(response.getCreatedOn(), parsedDate);
                assertNull(response.getLastUsedOn());
                assertEquals(response.getPaymentTypeName(), PaymentTypeName.CARD);
                assertEquals(response.getData().getCard().getCardholder(), "full name");
                assertEquals(response.getData().getCard().getExpMonth(), 12);
                assertEquals(response.getData().getCard().getExpYear(), 2018);
                assertEquals(response.getData().getCard().getLastFour(), 5276);
                assertEquals(response.getData().getCard().getBrand(), "visa");
                assertThat(response.getData().getCard().getBrandEnum(), Is.is(CardBrand.VISA));
                assertEquals(response.getData().getBilling().getLine1(), "somewhere");
                assertNull(response.getData().getBilling().getLine2());
                assertNull(response.getData().getBilling().getState());
                assertEquals(response.getData().getBilling().getCity(), "TYO");
                assertEquals(response.getData().getBilling().getCountryEnum(), Country.JAPAN);
                assertEquals(response.getData().getBilling().getZip(), "111-1111");
                assertThat(response.getConfirmed(), is(false));
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }

  private TransactionTokensBuilders.CreateTransactionTokenRequestBuilder createBuilder() {
    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);
    return univapay.createTransactionToken(
        "some@email.com",
        new CreditCard("full name", "4556137309615276", 12, 2018, 599)
            .addAddress(Country.JAPAN, null, "Tokyo", "somewhere", null, "111-1111"),
        TransactionTokenType.ONE_TIME);
  }

  @Test
  public void shouldIncludeCustomerId() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/tokens",
        appToken,
        secret,
        200,
        StoreFakeRR.createTransactionTokenForCustomerResponse,
        StoreFakeRR.createTransactionTokenForCustomerRequest);

    final MetadataMap metadata = new MetadataMap();
    final String floatKey = "float";
    final String floatValue = "10.3";
    metadata.put(floatKey, floatValue);

    UUID customerId = UUID.fromString("7680e246-2d10-42bf-8bbb-2230e1ed712c");

    //      The request should be the same whether you set the metadata first...
    TransactionToken transactionToken =
        createBuilder()
            .withMetadata(metadata)
            .withCustomerId(new UnivapayCustomerId(customerId))
            .build()
            .dispatch();

    assertEquals(customerId.toString(), transactionToken.getMetadata().get("customer_id"));

    //      Or the customer Id first.
    TransactionToken transactionToken2 =
        createBuilder()
            .withCustomerId(
                new UnivapayCustomerId(UUID.fromString("7680e246-2d10-42bf-8bbb-2230e1ed712c")))
            .withMetadata(metadata)
            .build()
            .dispatch();

    assertEquals(customerId.toString(), transactionToken2.getMetadata().get("customer_id"));
  }

  @Test
  public void shouldPostAndReturnTransactionTokenInfoWithCreditCardHasNoCVV()
      throws InterruptedException {
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
                assertEquals(response.getId().toString(), "004b391f-1c98-43f8-87de-28b21aaaca00");
                assertEquals(
                    response.getStoreId().toString(), "bf75472e-7f2d-4745-a66d-9b96ae031c7a");
                assertEquals(response.getMode(), ProcessingMode.TEST);
                assertEquals(response.getCreatedOn(), parsedDate);
                assertNull(response.getLastUsedOn());
                assertEquals(response.getPaymentTypeName(), PaymentTypeName.CARD);
                assertEquals(response.getData().getCard().getCardholder(), "full name");
                assertEquals(response.getData().getCard().getExpMonth(), 12);
                assertEquals(response.getData().getCard().getExpYear(), 2018);
                assertEquals(response.getData().getCard().getLastFour(), 5276);
                assertEquals(response.getData().getCard().getBrand(), "visa");
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
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  public void shouldPostAndReturnTransactionTokenInfoWithQrScan() throws InterruptedException {
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
                assertEquals(response.getId().toString(), "004b391f-1c98-43f8-87de-28b21aaaca00");
                assertEquals(
                    response.getStoreId().toString(), "bf75472e-7f2d-4745-a66d-9b96ae031c7a");
                assertEquals(response.getMode(), ProcessingMode.TEST);
                assertEquals(response.getCreatedOn(), parsedDate);
                assertNull(response.getLastUsedOn());
                assertEquals(response.getPaymentTypeName(), PaymentTypeName.QR_SCAN);
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
  public void shouldPostAndReturnRecurringTransactionTokenWithUsageLimit()
      throws InterruptedException {
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
            new CreditCard("full name", "4556137309615276", 12, 2018, 599)
                .addAddress(Country.JAPAN, null, "Tokyo", "somewhere", null, "111-1111"),
            TransactionTokenType.RECURRING)
        .withUsageLimit(RecurringTokenInterval.WEEKLY)
        .build()
        .dispatch(
            new UnivapayCallback<TransactionTokenWithData>() {
              @Override
              public void getResponse(TransactionTokenWithData response) {
                assertEquals(response.getId().toString(), "004b391f-1c98-43f8-87de-28b21aaaca00");
                assertEquals(
                    response.getStoreId().toString(), "bf75472e-7f2d-4745-a66d-9b96ae031c7a");
                assertEquals(response.getMode(), ProcessingMode.TEST);
                assertEquals(response.getCreatedOn(), parsedDate);
                assertNull(response.getLastUsedOn());
                assertEquals(response.getPaymentTypeName(), PaymentTypeName.CARD);
                assertEquals(response.getData().getCard().getCardholder(), "full name");
                assertEquals(response.getData().getCard().getExpMonth(), 12);
                assertEquals(response.getData().getCard().getExpYear(), 2018);
                assertEquals(response.getData().getCard().getLastFour(), 5276);
                assertEquals(response.getData().getCard().getBrand(), "visa");
                assertEquals(response.getData().getBilling().getLine1(), "somewhere");
                assertNull(response.getData().getBilling().getLine2());
                assertNull(response.getData().getBilling().getState());
                assertEquals(response.getData().getBilling().getCity(), "TYO");
                assertEquals(response.getData().getBilling().getCountry(), "JP");
                assertEquals(response.getData().getBilling().getZip(), "111-1111");
                assertEquals(response.getUsageLimit(), RecurringTokenInterval.WEEKLY);
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
  public void shouldPostAndReturnTransactionTokenWithApplePayData() throws InterruptedException {
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
                assertEquals(response.getId().toString(), "004b391f-1c98-43f8-87de-28b21aaaca00");
                assertEquals(
                    response.getStoreId().toString(), "bf75472e-7f2d-4745-a66d-9b96ae031c7a");
                assertEquals(response.getMode(), ProcessingMode.TEST);
                assertEquals(response.getCreatedOn(), parsedDate);
                assertNull(response.getLastUsedOn());
                assertEquals(response.getPaymentTypeName(), PaymentTypeName.APPLE_PAY);
                assertEquals(
                    response.getData().asCardPaymentData().getCard().getCardholder(), "someperson");
                assertEquals(response.getData().asCardPaymentData().getCard().getExpMonth(), 12);
                assertEquals(response.getData().asCardPaymentData().getCard().getExpYear(), 2018);
                assertEquals(response.getData().asCardPaymentData().getCard().getLastFour(), 5276);
                assertEquals(response.getData().asCardPaymentData().getCard().getBrand(), "visa");
                assertEquals(
                    response.getData().asCardPaymentData().getBilling().getLine1(), "somewhere");
                assertNull(response.getData().asCardPaymentData().getBilling().getLine2());
                assertNull(response.getData().asCardPaymentData().getBilling().getState());
                assertEquals(response.getData().asCardPaymentData().getBilling().getCity(), "TYO");
                assertEquals(
                    response.getData().asCardPaymentData().getBilling().getCountryEnum(),
                    Country.JAPAN);
                assertEquals(
                    response.getData().asCardPaymentData().getBilling().getZip(), "111-1111");
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
  public void shouldPostLegacyCountryWithApplePayData() throws InterruptedException {
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
                    response.getData().asCardPaymentData().getBilling().getCountry(), "JP");
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
  public void shouldPostAndReturnTransactionTokenWithKonbiniPaymentData()
      throws InterruptedException {
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

    final Period expirationPeriod = Period.ofDays(9);

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
                assertEquals(response.getId().toString(), "004b391f-1c98-43f8-87de-28b21aaaca00");
                assertEquals(
                    response.getStoreId().toString(), "bf75472e-7f2d-4745-a66d-9b96ae031c7a");
                assertEquals(response.getMode(), ProcessingMode.TEST);
                assertEquals(response.getCreatedOn(), parsedDate);
                assertNull(response.getLastUsedOn());
                assertEquals(response.getPaymentTypeName(), PaymentTypeName.KONBINI);
                assertEquals(
                    response.getData().asKonbiniPaymentData().getCustomerName(), "okyakusama");
                assertEquals(
                    response.getData().asKonbiniPaymentData().getConvenienceStore(),
                    Konbini.FAMILY_MART);
                assertEquals(
                    response.getData().asKonbiniPaymentData().getExpirationPeriod(),
                    expirationPeriod);
                assertEquals(
                    response
                        .getData()
                        .asKonbiniPaymentData()
                        .getPhoneNumber()
                        .getCountryCode()
                        .intValue(),
                    55);
                assertEquals(
                    response.getData().asKonbiniPaymentData().getPhoneNumber().getLocalNumber(),
                    "4799318900");
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
  public void shouldPostAndReturnTransactionTokenWithKonbiniPaymentDataWithoutExpiration()
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
  public void shouldPostLegacyCountry() throws InterruptedException {
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

    univapay
        .createTransactionToken(
            "some@email.com",
            new CreditCard("full name", "4556137309615276", 12, 2018, 599)
                .addAddress("JP", null, "Tokyo", "somewhere", null, "111-1111"),
            TransactionTokenType.ONE_TIME)
        .withMetadata(metadata)
        .withUseConfirmation(true)
        .build()
        .dispatch(
            new UnivapayCallback<TransactionTokenWithData>() {
              @Override
              public void getResponse(TransactionTokenWithData response) {
                assertEquals(response.getData().getBilling().getCountry(), "JP");
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
  public void shouldPostTransactionTokenInfoUniqueMetadata() throws IOException, UnivapayException {
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

    final Map<String, Float> metadata = new LinkedHashMap<>();
    final String floatKey = "float";
    final Float floatValue = Float.valueOf("10.3");
    metadata.put(floatKey, floatValue);
    final MetadataFloatAdapter adapter = new MetadataFloatAdapter();

    TransactionTokenWithData response =
        univapay
            .createTransactionToken(
                "some@email.com",
                new CreditCard("full name", "4556137309615276", 12, 2018, 599)
                    .addAddress(Country.JAPAN, null, "Tokyo", "somewhere", null, "111-1111"),
                TransactionTokenType.ONE_TIME)
            .withMetadata(metadata, adapter)
            .withUseConfirmation(true)
            .build()
            .dispatch();
    assertThat(response.getMetadata(adapter).get(floatKey), is(floatValue));
  }

  @Test
  public void shouldPostTransactionTokenWithAppJTWWithoutSecret()
      throws IOException, UnivapayException {
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

    final MetadataMap metadata = new MetadataMap();
    final String floatKey = "float";
    final String floatValue = "10.3";
    metadata.put(floatKey, floatValue);

    TransactionTokenWithData response =
        univapay
            .createTransactionToken(
                "some@email.com",
                new CreditCard("full name", "4556137309615276", 12, 2018, 599)
                    .addAddress(Country.JAPAN, null, "Tokyo", "somewhere", null, "111-1111"),
                TransactionTokenType.ONE_TIME)
            .withMetadata(metadata)
            .withUseConfirmation(true)
            .build()
            .dispatch();
    assertEquals(response.getId().toString(), "004b391f-1c98-43f8-87de-28b21aaaca00");
    assertEquals(response.getStoreId().toString(), "bf75472e-7f2d-4745-a66d-9b96ae031c7a");
    assertEquals(response.getMode(), ProcessingMode.TEST);
    assertThat(response.getMetadata().get(floatKey), is(floatValue));
    MetadataFloatAdapter adapter = new MetadataFloatAdapter();
    assertThat(response.getMetadata(adapter).get(floatKey), is(Float.valueOf(floatValue)));
    assertEquals(response.getCreatedOn(), parsedDate);
    assertNull(response.getLastUsedOn());
    assertEquals(response.getPaymentTypeName(), PaymentTypeName.CARD);
    assertEquals(response.getData().getCard().getCardholder(), "full name");
    assertEquals(response.getData().getCard().getExpMonth(), 12);
    assertEquals(response.getData().getCard().getExpYear(), 2018);
    assertEquals(response.getData().getCard().getLastFour(), 5276);
    assertEquals(response.getData().getCard().getBrand(), "visa");
    assertThat(response.getData().getCard().getBrandEnum(), is(CardBrand.VISA));
    assertEquals(response.getData().getBilling().getLine1(), "somewhere");
    assertNull(response.getData().getBilling().getLine2());
    assertNull(response.getData().getBilling().getState());
    assertEquals(response.getData().getBilling().getCity(), "TYO");
    assertEquals(response.getData().getBilling().getCountryEnum(), Country.JAPAN);
    assertEquals(response.getData().getBilling().getZip(), "111-1111");
  }

  @Test
  public void shouldPostTransactionTokenWithAppTokenWithoutSecret()
      throws IOException, UnivapayException {
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

    final MetadataMap metadata = new MetadataMap();
    final String floatKey = "float";
    final String floatValue = "10.3";
    metadata.put(floatKey, floatValue);

    TransactionTokenWithData response =
        univapay
            .createTransactionToken(
                "some@email.com",
                new CreditCard("full name", "4556137309615276", 12, 2018, 599)
                    .addAddress(Country.JAPAN, null, "Tokyo", "somewhere", null, "111-1111"),
                TransactionTokenType.ONE_TIME)
            .withMetadata(metadata)
            .withUseConfirmation(true)
            .build()
            .dispatch();
    assertEquals(response.getId().toString(), "004b391f-1c98-43f8-87de-28b21aaaca00");
    assertEquals(response.getStoreId().toString(), "bf75472e-7f2d-4745-a66d-9b96ae031c7a");
    assertEquals(response.getMode(), ProcessingMode.TEST);
    assertThat(response.getMetadata().get(floatKey), is(floatValue));
    MetadataFloatAdapter adapter = new MetadataFloatAdapter();
    assertThat(response.getMetadata(adapter).get(floatKey), is(Float.valueOf(floatValue)));
    assertEquals(response.getCreatedOn(), parsedDate);
    assertNull(response.getLastUsedOn());
    assertEquals(response.getPaymentTypeName(), PaymentTypeName.CARD);
    assertEquals(response.getData().getCard().getCardholder(), "full name");
    assertEquals(response.getData().getCard().getExpMonth(), 12);
    assertEquals(response.getData().getCard().getExpYear(), 2018);
    assertEquals(response.getData().getCard().getLastFour(), 5276);
    assertEquals(response.getData().getCard().getBrand(), "visa");
    assertThat(response.getData().getCard().getBrandEnum(), is(CardBrand.VISA));
    assertEquals(response.getData().getBilling().getLine1(), "somewhere");
    assertNull(response.getData().getBilling().getLine2());
    assertNull(response.getData().getBilling().getState());
    assertEquals(response.getData().getBilling().getCity(), "TYO");
    assertEquals(response.getData().getBilling().getCountryEnum(), Country.JAPAN);
    assertEquals(response.getData().getBilling().getZip(), "111-1111");
  }

  @Test
  public void shouldPostAndReturnTransactionTokenWithPaidyPaymentData()
      throws InterruptedException {
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
                assertThat(response.getLastUsedOn(), is(nullValue()));
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
  public void shouldPostAndReturnNoEmailTransactionTokenWithPaidyPaymentData()
      throws IOException, UnivapayException {
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
    assertThat(token.getEmail(), is(nullValue()));
    assertThat(token.getMode(), is(ProcessingMode.TEST));
    assertThat(token.getCreatedOn(), is(parsedDate));
    assertThat(token.getLastUsedOn(), is(nullValue()));
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
  public void shouldPostAndReturnNoEmailTransactionTokenWithQrScanData()
      throws IOException, UnivapayException {
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
    assertThat(token.getEmail(), is(nullValue()));
    assertThat(token.getMode(), is(ProcessingMode.TEST));
    assertThat(token.getCreatedOn(), is(parsedDate));
    assertThat(token.getLastUsedOn(), is(nullValue()));
    assertThat(token.getPaymentTypeName(), is(PaymentTypeName.QR_SCAN));
    assertThat(token.getData().asQrScanData().getGateway(), Is.is(Gateway.ALIPAY));
  }

  @Test
  public void shouldPostAndReturnTransactionTokenWithQrMerchantPaymentData()
      throws InterruptedException {
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
                assertThat(response.getLastUsedOn(), is(nullValue()));
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
  public void shouldPostAndReturnTransactionTokenWithQrMerchantPaymentDataForAlipayConnect() {
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

    try {
      TransactionTokenWithData response =
          univapay
              .createTransactionToken(paymentData, TransactionTokenType.ONE_TIME)
              .build()
              .dispatch();

      assertThat(response.getId().toString(), is("11e8dcf3-1c95-be98-a370-5fb11e03b325"));
      assertThat(response.getStoreId().toString(), is("11e8dcdb-52a6-bf5e-b126-277449999f80"));
      assertThat(response.getEmail(), is(nullValue()));
      assertThat(response.getMode(), is(ProcessingMode.TEST));
      assertThat(response.getCreatedOn(), is(parsedDate));
      assertThat(response.getLastUsedOn(), is(nullValue()));
      assertThat(response.getPaymentTypeName(), is(PaymentTypeName.QR_MERCHANT));
      assertThat(response.getData().asQrMerchantPaymentData().getQrImageUrl(), is(nullValue()));

      assertThat(
          response.getData().asQrMerchantPaymentData().getBrand(), is(QrMpmBrand.AlipayConnect));

      // To Generate the QrCodeUrl, Continue with the Charge creation...

    } catch (Exception e) {
      fail("Failing with unexpected exception: " + e.toString());
    }
  }

  @Test
  public void shouldPostAndReturnNoEmailTransactionTokenWithQrMerchantData()
      throws IOException, UnivapayException {
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
    assertThat(token.getEmail(), is(nullValue()));
    assertThat(token.getMode(), is(ProcessingMode.TEST));
    assertThat(token.getCreatedOn(), is(parsedDate));
    assertThat(token.getLastUsedOn(), is(nullValue()));
    assertThat(token.getPaymentTypeName(), is(PaymentTypeName.QR_MERCHANT));
    assertThat(
        token.getData().asQrMerchantPaymentData().getQrImageUrl(), is("http://qr-image.png"));

    assertThat(token.getData().asQrMerchantPaymentData().getBrand(), is(QrMpmBrand.Alipay));
  }

  @Test
  public void shouldBeAbleToCreateTransactionTokenWithOnlinePaymentData()
      throws IOException, UnivapayException {

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
    assertThat(token.getEmail(), is(nullValue()));
    assertThat(token.getMode(), is(ProcessingMode.LIVE));
    assertThat(token.getCreatedOn(), is(expectedDate));
    assertThat(token.getLastUsedOn(), is(nullValue()));
    assertThat(token.getPaymentTypeName(), is(PaymentTypeName.ONLINE));

    OnlinePaymentData onlinePaymentData = token.getData().asOnlinePaymentData();
    assertThat(onlinePaymentData, is(notNullValue()));
    assertThat(onlinePaymentData.getBrand(), is(OnlineBrand.TEST));
    // These will be provided if the charge is successfully processed to the Deferred status
    assertThat(onlinePaymentData.getCallMethod(), is(nullValue()));
    assertThat(onlinePaymentData.getCallMethod(), is(nullValue()));
  }

  @Test
  public void shouldCreateTransactionTokenWithFullOnlinePaymentData()
      throws IOException, UnivapayException {

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
    assertThat(token.getEmail(), is(nullValue()));
    assertThat(token.getMode(), is(ProcessingMode.LIVE));
    assertThat(token.getCreatedOn(), is(expectedDate));
    assertThat(token.getLastUsedOn(), is(nullValue()));
    assertThat(token.getPaymentTypeName(), is(PaymentTypeName.ONLINE));

    OnlinePaymentData onlinePaymentData = token.getData().asOnlinePaymentData();
    assertThat(onlinePaymentData, is(notNullValue()));
    assertThat(onlinePaymentData.getBrand(), is(OnlineBrand.TEST));
    assertThat(onlinePaymentData.getCallMethod(), is(CallMethod.SDK));
    assertThat(onlinePaymentData.getUserIdentifier(), is("1234"));
  }
}
