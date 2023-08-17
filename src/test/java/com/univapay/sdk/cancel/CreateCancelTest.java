package com.univapay.sdk.cancel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.CancelId;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.cancel.Cancel;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CancelStatus;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.MockRRGeneratorWithAppTokenSecret;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.CancelsFakeRR;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class CreateCancelTest extends GenericTest {

  final StoreId storeId = new StoreId("11e7b331-ee33-f8ee-a37d-1b150f2ba2f6");
  final ChargeId chargeId = new ChargeId("11e7b333-cb82-3d54-a37d-036f78f60e1c");
  final CancelId cancelId = new CancelId("cdf3ba40-b333-11e7-a37d-d75967ccf22e");

  @Test
  public void createsACancel() throws InterruptedException {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/stores/" + storeId + "/charges" + "/" + chargeId + "/cancels",
        appToken,
        secret,
        200,
        CancelsFakeRR.createCancelFakeResponse,
        CancelsFakeRR.createCancelFakeRequest);

    Map<String, Object> requestMetadata = new HashMap<>();

    requestMetadata.put("product_id", "1245");
    requestMetadata.put("customer_id", "12345678");

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-10-17T12:07:53.809331Z");

    univapay
        .createCancel(storeId, chargeId)
        .withMetadata(requestMetadata)
        .build()
        .dispatch(
            new UnivapayCallback<Cancel>() {
              @Override
              public void getResponse(Cancel response) {
                assertEquals(response.getCancelId().toString(), cancelId.toString());
                assertEquals(response.getChargeId().toString(), chargeId.toString());
                assertEquals(response.getCreatedOn(), parsedDate);
                assertEquals(response.getCancelStatus(), CancelStatus.PENDING);
                assertEquals(response.getMetadata().get("product_id"), "1245");
                assertEquals(response.getMetadata().get("customer_id"), "12345678");
                assertEquals(response.getMode(), ProcessingMode.TEST);
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
  public void createCancelMetadata() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/stores/" + storeId + "/charges" + "/" + chargeId + "/cancels",
        jwt,
        200,
        CancelsFakeRR.createCancelFakeResponseMetadata,
        CancelsFakeRR.createCancelFakeRequestMetadata);

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("float", "10.3");
    requestMetadata.put("number", "10");
    requestMetadata.put("string", "string something");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    Cancel response =
        univapay.createCancel(storeId, chargeId).withMetadata(requestMetadata).build().dispatch();

    assertThat(response.getMetadata().get("float"), is("10.3"));
    assertThat(response.getMetadata().get("number"), is("10"));
    assertThat(response.getMetadata().get("string"), is("string something"));
  }

  @Test
  public void createCancelUniqueMetadata() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/stores/" + storeId + "/charges" + "/" + chargeId + "/cancels",
        jwt,
        200,
        CancelsFakeRR.cancelFakeResponseMetadataFloat,
        CancelsFakeRR.cancelFakeRequestMetadataFloat);

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("float", "10.3");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    Cancel response =
        univapay.createCancel(storeId, chargeId).withMetadata(requestMetadata).build().dispatch();
    assertThat(response.getMetadata().get("float"), is("10.3"));
  }
}
