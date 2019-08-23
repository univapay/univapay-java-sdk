package com.univapay.sdk.charge;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.adapters.JsonAdapters;
import com.univapay.sdk.builders.charge.ChargesBuilders;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ChargeStatus;
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.MockRRGeneratorWithAppTokenSecret;
import com.univapay.sdk.utils.metadataadapter.ManyTypesAdapter;
import com.univapay.sdk.utils.metadataadapter.ManyTypesMetadata;
import com.univapay.sdk.utils.metadataadapter.MetadataFloatAdapter;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import org.junit.Test;

public class CreateChargeTest extends GenericTest {

  private final ManyTypesAdapter manyTypesAdapter = new ManyTypesAdapter();
  private final ManyTypesMetadata manyTypesMetadata =
      new ManyTypesMetadata(
          "hola",
          BigInteger.valueOf(989223112),
          BigDecimal.valueOf(1234.7981723987),
          true,
          3.141592F);

  private void testChargeCreation(ChargesBuilders.CreateChargeRequestBuilder requestBuilder)
      throws Exception {

    MetadataMap reqMetadata = new MetadataMap();
    reqMetadata.put("cod", String.valueOf(15984632));
    reqMetadata.put("prod", "electronics");

    Charge response = requestBuilder.withMetadata(reqMetadata).build().dispatch();

    assertEquals(response.getId().toString(), "425e88b7-b588-4247-80ee-0ea0caff1190");
    assertEquals(response.getStoreId().toString(), "653ef5a3-73f2-408a-bac5-7058835f7700");
    assertEquals(response.getRequestedAmount(), BigInteger.valueOf(1000));
    assertEquals(response.getRequestedCurrency(), "JPY");
    assertNull(response.getChargedAmount());
    assertNull(response.getChargedCurrency());
    assertEquals(response.getStatus(), ChargeStatus.PENDING);
    assertNull(response.getError());
    assertEquals(Integer.parseInt(response.getMetadata().get("cod")), 15984632);
    assertEquals(response.getMetadata().get("prod"), "electronics");
    assertNull(response.getCreatedOn());
  }

  @Test
  public void shouldPostAndReturnChargeDataWithMetadata() throws Exception {
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
    final boolean capture = true;

    testChargeCreation(univapay.createCharge(tokenId, amount, currency));

    testChargeCreation(univapay.createCharge(tokenId, new MoneyLike(amount, currency)));

    testChargeCreation(univapay.createCharge(tokenId, amount, currency, capture));

    testChargeCreation(univapay.createCharge(tokenId, new MoneyLike(amount, currency), capture));
  }

  @Test
  public void shouldPostAndReturnChargeData() throws Exception {
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

    final Date parsedDate = dateParser.parseDateTime("2017-06-22T16:00:55.436116+09:00").toDate();
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

    assertEquals(response.getId().toString(), "425e88b7-b588-4247-80ee-0ea0caff1190");
    assertEquals(response.getStoreId().toString(), "653ef5a3-73f2-408a-bac5-7058835f7700");
    assertEquals(response.getRequestedAmount(), BigInteger.valueOf(1000));
    assertEquals(response.getRequestedCurrency(), "JPY");
    assertNull(response.getChargedAmount());
    assertNull(response.getChargedCurrency());
    assertThat(response.getOnlyDirectCurrency(), is(true));
    assertThat(response.getDescriptor(), is(descriptor));
    assertEquals(response.getStatus(), ChargeStatus.PENDING);
    assertNull(response.getError());
    assertEquals(response.getCreatedOn(), parsedDate);
  }

  @Test
  public void shouldPostAndReturnChargeDataSynchronous() throws Exception {
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

    final Date parsedDate = dateParser.parseDateTime("2017-06-22T16:00:55.436116+09:00").toDate();

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

    assertEquals(response.getId().toString(), "425e88b7-b588-4247-80ee-0ea0caff1190");
    assertEquals(response.getStoreId().toString(), "653ef5a3-73f2-408a-bac5-7058835f7700");
    assertEquals(response.getRequestedAmount(), BigInteger.valueOf(1000));
    assertEquals(response.getRequestedCurrency(), "JPY");
    assertNull(response.getChargedAmount());
    assertNull(response.getChargedCurrency());
    assertEquals(response.getStatus(), ChargeStatus.PENDING);
    assertNull(response.getError());
    assertEquals(response.getCreatedOn(), parsedDate);
  }

  @Test
  public void shouldPostChargeMetadata() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/charges",
        jwt,
        200,
        ChargesFakeRR.createStoreChargeMetadataFakeResponse,
        ChargesFakeRR.createStoreChargeMetadataFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final MetadataMap metadata = new MetadataMap();

    final String arrayKey = "array";
    final String arrayValue = "[string, 12.3]";
    metadata.put(arrayKey, arrayValue);
    final String floatKey = "float";
    final String floatValue = "10.3";
    metadata.put(floatKey, floatValue);
    final String numberKey = "number";
    final String numberValue = "10";
    metadata.put(numberKey, numberValue);
    final String stringKey = "string";
    final String stringValue = "string";
    metadata.put(stringKey, stringValue);

    Charge response =
        univapay
            .createCharge(
                new TransactionTokenId("653ef5a3-73f2-408a-bac5-7058835f7700"),
                BigInteger.valueOf(1000),
                "JPY")
            .withMetadata(metadata)
            .build()
            .dispatch();

    assertThat(response.getMetadata().get(arrayKey), is(arrayValue));
    assertThat(response.getMetadata().get(floatKey), is(floatValue));
    assertThat(response.getMetadata().get(numberKey), is(numberValue));
    assertThat(response.getMetadata().get(stringKey), is(stringValue));
  }

  @Test
  public void shouldPostChargeUniqueMetadata() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/charges",
        jwt,
        200,
        ChargesFakeRR.chargeFakeResponseMetadataFloat,
        ChargesFakeRR.createChargeFakeRequestMetadataFloat);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final Map<String, Float> metadata = new LinkedHashMap<>();

    final String floatKey = "float";
    final Float floatValue = Float.valueOf("10.3");
    metadata.put(floatKey, floatValue);

    MetadataFloatAdapter adapter = new MetadataFloatAdapter();
    Charge response =
        univapay
            .createCharge(
                new TransactionTokenId("653ef5a3-73f2-408a-bac5-7058835f7700"),
                BigInteger.valueOf(1000),
                "JPY")
            .withMetadata(metadata, adapter)
            .build()
            .dispatch();
    assertThat(response.getMetadata(adapter).get(floatKey), is(floatValue));
  }

  @Test
  public void shouldAuthorizeACharge() throws Exception {

    final Date captureAt =
        JsonAdapters.dateTimeParser.parseDateTime("2017-10-26T15:32:01.234567").toDate();

    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/charges",
        appToken,
        secret,
        200,
        ChargesFakeRR.authorizeChargeFakeResponse,
        ChargesFakeRR.authorizeChargeFakeRequest(
            JsonAdapters.dateTimePrinter.print(captureAt.getTime())));

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
