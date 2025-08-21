package com.univapay.sdk.charge;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.builders.charge.ChargesBuilders;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ChargeStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.MockRRGeneratorWithAppTokenSecret;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CreateChargeTest extends GenericTest {

  private void testChargeCreation(ChargesBuilders.CreateChargeRequestBuilder requestBuilder)
      throws Exception {

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("cod", "15984632");
    requestMetadata.put("prod", "electronics");

    Charge response = requestBuilder.withMetadata(requestMetadata).build().dispatch();

    assertEquals("425e88b7-b588-4247-80ee-0ea0caff1190", response.getId().toString());
    assertEquals("653ef5a3-73f2-408a-bac5-7058835f7700", response.getStoreId().toString());
    assertEquals(response.getRequestedAmount(), BigInteger.valueOf(1000));
    assertEquals("JPY", response.getRequestedCurrency());
    assertNull(response.getChargedAmount());
    assertNull(response.getChargedCurrency());
    assertEquals(ChargeStatus.PENDING, response.getStatus());
    assertNull(response.getError());
    assertEquals("15984632", response.getMetadata().get("cod"));
    assertEquals("electronics", response.getMetadata().get("prod"));
    assertNull(response.getCreatedOn());
  }

  @Test
  void shouldPostAndReturnChargeDataWithMetadata() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/charges",
        appToken,
        secret,
        200,
        ChargesFakeRR.createStoreChargeFakeResponse,
        ChargesFakeRR.createStoreChargeFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    TransactionTokenId tokenId = new TransactionTokenId("653ef5a3-73f2-408a-bac5-7058835f7700");
    BigInteger amount = BigInteger.valueOf(1000);
    String currency = "JPY";

    testChargeCreation(univapay.createCharge(tokenId, amount, currency));

    testChargeCreation(univapay.createCharge(tokenId, new MoneyLike(amount, currency)));
  }

  @Test
  void shouldPostAndReturnCaptureChargeDataWithMetadata() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/charges",
        appToken,
        secret,
        200,
        ChargesFakeRR.createStoreChargeFakeResponse,
        ChargesFakeRR.createStoreCaptureChargeFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    TransactionTokenId tokenId = new TransactionTokenId("653ef5a3-73f2-408a-bac5-7058835f7700");
    BigInteger amount = BigInteger.valueOf(1000);
    String currency = "JPY";
    final boolean capture = true;

    testChargeCreation(univapay.createCharge(tokenId, amount, currency, capture));

    testChargeCreation(univapay.createCharge(tokenId, new MoneyLike(amount, currency), capture));
  }

  @Test
  void shouldPostAndReturnChargeData() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/charges",
        appToken,
        secret,
        200,
        ChargesFakeRR.createStoreChargeNoMetadataFakeResponse,
        ChargesFakeRR.createStoreChargeNoMetadataFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");
    final String descriptor = "test descriptor";

    Charge response =
        univapay
            .createCharge(
                new TransactionTokenId("653ef5a3-73f2-408a-bac5-7058835f7700"),
                BigInteger.valueOf(1000),
                "JPY")
            .withOnlyDirectCurrency(true)
            .withDescriptor(descriptor)
            .build()
            .dispatch();

    assertEquals("425e88b7-b588-4247-80ee-0ea0caff1190", response.getId().toString());
    assertEquals("653ef5a3-73f2-408a-bac5-7058835f7700", response.getStoreId().toString());
    assertEquals(response.getRequestedAmount(), BigInteger.valueOf(1000));
    assertEquals("JPY", response.getRequestedCurrency());
    assertNull(response.getChargedAmount());
    assertNull(response.getChargedCurrency());
    assertThat(response.getOnlyDirectCurrency(), is(true));
    assertThat(response.getDescriptor(), is(descriptor));
    assertEquals(ChargeStatus.PENDING, response.getStatus());
    assertNull(response.getError());
    assertEquals(response.getCreatedOn(), parsedDate);
  }

  @Test
  void shouldPostAndReturnChargeDataSynchronous() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/charges",
        appToken,
        secret,
        200,
        ChargesFakeRR.createStoreChargeNoMetadataFakeResponse,
        ChargesFakeRR.createStoreChargeNoMetadataFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    final String descriptor = "test descriptor";
    Charge response =
        univapay
            .createCharge(
                new TransactionTokenId("653ef5a3-73f2-408a-bac5-7058835f7700"),
                BigInteger.valueOf(1000),
                "JPY")
            .withOnlyDirectCurrency(true)
            .withDescriptor(descriptor)
            .build()
            .dispatch();

    assertEquals("425e88b7-b588-4247-80ee-0ea0caff1190", response.getId().toString());
    assertEquals("653ef5a3-73f2-408a-bac5-7058835f7700", response.getStoreId().toString());
    assertEquals(response.getRequestedAmount(), BigInteger.valueOf(1000));
    assertEquals("JPY", response.getRequestedCurrency());
    assertNull(response.getChargedAmount());
    assertNull(response.getChargedCurrency());
    assertEquals(ChargeStatus.PENDING, response.getStatus());
    assertNull(response.getError());
    assertEquals(response.getCreatedOn(), parsedDate);
  }

  @Test
  void shouldPostChargeMetadata() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/charges",
        jwt,
        200,
        ChargesFakeRR.createStoreChargeMetadataFakeResponse,
        ChargesFakeRR.createStoreChargeMetadataFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    List<String> metadataArray = new ArrayList<>();
    metadataArray.add("1");
    metadataArray.add("2");

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("array", metadataArray);
    requestMetadata.put("float", 10.3);
    requestMetadata.put("number", 10L);
    requestMetadata.put("string", "string");

    Charge response =
        univapay
            .createCharge(
                new TransactionTokenId("653ef5a3-73f2-408a-bac5-7058835f7700"),
                BigInteger.valueOf(1000),
                "JPY")
            .withMetadata(requestMetadata)
            .build()
            .dispatch();

    assertEquals(requestMetadata, response.getMetadata());
  }

  @Test
  void shouldBeAbleToHandleNumbers() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/charges",
        jwt,
        200,
        ChargesFakeRR.chargeFakeResponseMetadataNumbers,
        ChargesFakeRR.createChargeFakeRequestMetadataNumbers);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    // No Matter the kind of number you send on the metadata
    // When GSON reads back the JsNumber, it will convert to Long if possible
    // else will try to convert as Double
    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("key-1", "10.3"); // JsString
    requestMetadata.put("key-2", 10.3); // JsNumber
    requestMetadata.put("key-3", "10"); // JsString
    requestMetadata.put("key-4", 10); // JsNumber
    requestMetadata.put("key-5", 10L); // JsNumber
    requestMetadata.put("key-6", 10.0); // JsNumber

    Map<String, Object> responseMetadata = new HashMap<>();
    responseMetadata.put("key-1", "10.3"); // String
    responseMetadata.put("key-2", 10.3D); // Double
    responseMetadata.put("key-3", "10"); // JsString
    responseMetadata.put("key-4", 10L); // Long
    responseMetadata.put("key-5", 10L); // Long
    responseMetadata.put("key-6", 10.0); // Double

    Charge response =
        univapay
            .createCharge(
                new TransactionTokenId("653ef5a3-73f2-408a-bac5-7058835f7700"),
                BigInteger.valueOf(1000),
                "JPY")
            .withMetadata(requestMetadata)
            .build()
            .dispatch();

    assertEquals(responseMetadata, response.getMetadata());
  }

  @Test
  void shouldAuthorizeACharge() throws Exception {

    final OffsetDateTime captureAt = OffsetDateTime.now();

    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/charges",
        appToken,
        secret,
        200,
        ChargesFakeRR.authorizeChargeFakeResponse,
        ChargesFakeRR.authorizeChargeFakeRequest(formatDate(captureAt)));

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    univapay
        .authorizeCharge(
            new TransactionTokenId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            BigInteger.valueOf(1000),
            "JPY")
        .withCaptureAt(captureAt)
        .build()
        .dispatch();

    univapay
        .authorizeCharge(
            new TransactionTokenId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new MoneyLike(BigInteger.valueOf(1000), "JPY"))
        .withCaptureAt(captureAt)
        .build()
        .dispatch();
  }
}
