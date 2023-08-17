package com.univapay.sdk.idempotency;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.IdempotencyStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGeneratorWithAppTokenSecret;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class IdempotencyTest extends GenericTest {

  final TransactionTokenId transactionTokenId =
      new TransactionTokenId("653ef5a3-73f2-408a-bac5-7058835f7700");
  final BigInteger amount = BigInteger.valueOf(1000);
  final String currency = "JPY";

  @Test
  public void shouldContainSuccessfullyStoredIdempotencyStatus()
      throws IOException, UnivapayException {

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

    Assert.assertEquals(charge.getIdempotencyStatus(), idempotencyStatus);
  }

  @Test
  public void shouldContainRetrievedIdempotencyStatus() throws IOException, UnivapayException {
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

    Assert.assertEquals(charge1.getIdempotencyStatus(), idempotencyStatus);
  }

  @Test
  public void shouldContainErrorIdempotencyStatus() throws IOException, UnivapayException {
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

    Assert.assertEquals(charge1.getIdempotencyStatus(), idempotencyStatus);
  }

  @Test
  public void shouldContainNoStatusIdempotencyStatus() throws IOException, UnivapayException {
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

    Assert.assertEquals(charge1.getIdempotencyStatus(), IdempotencyStatus.NO_STATUS);
  }
}
