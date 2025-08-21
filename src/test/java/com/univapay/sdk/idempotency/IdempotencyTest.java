package com.univapay.sdk.idempotency;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.IdempotencyStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGeneratorWithAppTokenSecret;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class IdempotencyTest extends GenericTest {

  final TransactionTokenId transactionTokenId =
      new TransactionTokenId("653ef5a3-73f2-408a-bac5-7058835f7700");
  final BigInteger amount = BigInteger.valueOf(1000);
  final String currency = "JPY";

  @Test
  void shouldContainSuccessfullyStoredIdempotencyStatus() throws Exception {

    IdempotencyKey idempotencyKey = new IdempotencyKey("key_to_store");
    IdempotencyStatus idempotencyStatus = IdempotencyStatus.SUCCESSFULLY_STORED;

    MockRRGeneratorWithAppTokenSecret mockRRGenerator =
        new MockRRGeneratorWithAppTokenSecret()
            .withIdempotencySettings(idempotencyKey, idempotencyStatus);
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/charges",
        appToken,
        secret,
        200,
        ChargesFakeRR.createStoreChargeFakeResponse,
        ChargesFakeRR.createStoreChargeFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("cod", "15984632");
    requestMetadata.put("prod", "electronics");

    Charge charge =
        univapay
            .createCharge(transactionTokenId, amount, currency)
            .withMetadata(requestMetadata)
            .withIdempotencyKey(idempotencyKey)
            .build()
            .dispatch();

    assertEquals(charge.getIdempotencyStatus(), idempotencyStatus);
  }

  @Test
  void shouldContainRetrievedIdempotencyStatus() throws Exception {
    IdempotencyKey idempotencyKey = new IdempotencyKey("key_to_retrieve_response");
    IdempotencyStatus idempotencyStatus = IdempotencyStatus.RETRIEVED_IDEMPOTENT_RESPONSE;

    MockRRGeneratorWithAppTokenSecret mockRRGenerator =
        new MockRRGeneratorWithAppTokenSecret()
            .withIdempotencySettings(idempotencyKey, idempotencyStatus);
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/charges",
        appToken,
        secret,
        200,
        ChargesFakeRR.createStoreChargeFakeResponse,
        ChargesFakeRR.createStoreChargeFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("cod", "15984632");
    requestMetadata.put("prod", "electronics");

    Charge charge1 =
        univapay
            .createCharge(transactionTokenId, amount, currency)
            .withMetadata(requestMetadata)
            .withIdempotencyKey(idempotencyKey)
            .build()
            .dispatch();

    assertEquals(charge1.getIdempotencyStatus(), idempotencyStatus);
  }

  @Test
  void shouldContainErrorIdempotencyStatus() throws Exception {
    IdempotencyKey idempotencyKey = new IdempotencyKey("error_key");
    IdempotencyStatus idempotencyStatus = IdempotencyStatus.ERROR;

    MockRRGeneratorWithAppTokenSecret mockRRGenerator =
        new MockRRGeneratorWithAppTokenSecret()
            .withIdempotencySettings(idempotencyKey, idempotencyStatus);
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/charges",
        appToken,
        secret,
        200,
        ChargesFakeRR.createStoreChargeFakeResponse,
        ChargesFakeRR.createStoreChargeFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("cod", "15984632");
    requestMetadata.put("prod", "electronics");

    Charge charge1 =
        univapay
            .createCharge(transactionTokenId, amount, currency)
            .withMetadata(requestMetadata)
            .withIdempotencyKey(idempotencyKey)
            .build()
            .dispatch();

    assertEquals(charge1.getIdempotencyStatus(), idempotencyStatus);
  }

  @Test
  void shouldContainNoStatusIdempotencyStatus() throws Exception {
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

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("cod", "15984632");
    requestMetadata.put("prod", "electronics");

    Charge charge1 =
        univapay
            .createCharge(transactionTokenId, amount, currency)
            .withMetadata(requestMetadata)
            .build()
            .dispatch();

    assertEquals(IdempotencyStatus.NO_STATUS, charge1.getIdempotencyStatus());
  }
}
