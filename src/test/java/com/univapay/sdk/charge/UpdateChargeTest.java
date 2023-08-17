package com.univapay.sdk.charge;

import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ChargeStatus;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class UpdateChargeTest extends GenericTest {

  @Test
  public void shouldPostAndReturnUpdatedChargeInfo() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/stores/11e786da-4714-5028-8280-bb9bc7cf54e9/charges/11e792d6-6e0c-bf1e-bede-0be6e2f0ac23",
        jwt,
        200,
        ChargesFakeRR.updateStoreChargeFakeResponse,
        ChargesFakeRR.updateStoreChargeFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedCreatedOn = parseDate("2017-09-06T07:38:52.000000+09:00");
    final OffsetDateTime parsedUpdatedOn = parseDate("2017-10-02T06:25:06.000000+09:00");

    final Map<String, Object> requestMetadata = new HashMap();
    requestMetadata.put("hoge", "あああ");

    final Map<String, String> responseMetadata = new HashMap<>();
    responseMetadata.put("hoge", "ううう");

    univapay
        .updateCharge(
            new StoreId("11e786da-4714-5028-8280-bb9bc7cf54e9"),
            new ChargeId("11e792d6-6e0c-bf1e-bede-0be6e2f0ac23"))
        .withMetadata(requestMetadata)
        .build()
        .dispatch(
            new UnivapayCallback<Charge>() {

              @Override
              public void getResponse(Charge response) {
                assertEquals(response.getId().toString(), "11e792d6-6e0c-bf1e-bede-0be6e2f0ac23");
                assertEquals(
                    response.getStoreId().toString(), "11e786da-4714-5028-8280-bb9bc7cf54e9");
                assertEquals(
                    response.getTransactionTokenId().toString(),
                    "11e792d6-6b6a-c44a-9eb0-23f3053db978");
                assertEquals(
                    response.getSubscriptionId().toString(),
                    "11e792d6-6e02-3756-9eb2-bb14816a56bc");
                assertEquals(response.getRequestedAmount(), BigInteger.valueOf(132));
                assertEquals(response.getRequestedCurrency(), "USD");
                assertEquals(response.getRequestedAmountFormatted(), BigDecimal.valueOf(1.32));
                assertEquals(response.getChargedAmount(), BigInteger.valueOf(125));
                assertEquals(response.getChargedCurrency(), "EUR");
                assertEquals(response.getChargedAmountFormatted(), BigDecimal.valueOf(1.25));
                assertEquals(
                    response.getStatus(), new Gson().fromJson("successful", ChargeStatus.class));
                assertEquals(response.getMetadata(), responseMetadata);
                assertEquals(response.getMode(), new Gson().fromJson("test", ProcessingMode.class));
                assertEquals(response.getCreatedOn(), parsedCreatedOn);
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
  public void shouldUpdateChargeMetadata() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/stores/11e786da-4714-5028-8280-bb9bc7cf54e9/charges/11e792d6-6e0c-bf1e-bede-0be6e2f0ac23",
        jwt,
        200,
        ChargesFakeRR.createStoreChargeMetadataFakeResponse,
        ChargesFakeRR.updateChargeFakeRequestMetadata);

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
            .updateCharge(
                new StoreId("11e786da-4714-5028-8280-bb9bc7cf54e9"),
                new ChargeId("11e792d6-6e0c-bf1e-bede-0be6e2f0ac23"))
            .withMetadata(requestMetadata)
            .build()
            .dispatch();

    assertEquals(requestMetadata, response.getMetadata());
  }

  @Test
  public void shouldPostChargeUniqueMetadata() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/stores/11e786da-4714-5028-8280-bb9bc7cf54e9/charges/11e792d6-6e0c-bf1e-bede-0be6e2f0ac23",
        jwt,
        200,
        ChargesFakeRR.chargeFakeResponseMetadataNumbers,
        ChargesFakeRR.updateChargeFakeRequestMetadataNumbers);

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
            .updateCharge(
                new StoreId("11e786da-4714-5028-8280-bb9bc7cf54e9"),
                new ChargeId("11e792d6-6e0c-bf1e-bede-0be6e2f0ac23"))
            .withMetadata(requestMetadata)
            .build()
            .dispatch();

    assertEquals(responseMetadata, response.getMetadata());
  }
}
