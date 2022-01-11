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
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.MockRRGeneratorWithAppTokenSecret;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.metadataadapter.MetadataFloatAdapter;
import com.univapay.sdk.utils.mockcontent.CancelsFakeRR;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Test;

public class UpdateCancelTest extends GenericTest {

  final StoreId storeId = new StoreId("11e7b331-ee33-f8ee-a37d-1b150f2ba2f6");
  final ChargeId chargeId = new ChargeId("11e7b333-cb82-3d54-a37d-036f78f60e1c");
  final CancelId cancelId = new CancelId("cdf3ba40-b333-11e7-a37d-d75967ccf22e");

  @Test
  public void canUpdateACancel() throws InterruptedException {

    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "PATCH",
        "/stores/"
            + storeId.toString()
            + "/charges/"
            + chargeId.toString()
            + "/cancels/"
            + cancelId.toString(),
        appToken,
        secret,
        200,
        CancelsFakeRR.updateCancelFakeResponse,
        CancelsFakeRR.updateCancelFakeRequest);

    final MetadataMap metadata = new MetadataMap();

    metadata.put("product_id", "updated123");

    final OffsetDateTime parsedDate = parseDate("2017-10-17T12:07:53.809331Z");

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    univapay
        .updateCancel(storeId, chargeId, cancelId)
        .withMetadata(metadata)
        .build()
        .dispatch(
            new UnivapayCallback<Cancel>() {
              @Override
              public void getResponse(Cancel response) {
                assertEquals(response.getCancelId().toString(), cancelId.toString());
                assertEquals(response.getChargeId().toString(), chargeId.toString());
                assertEquals(response.getCreatedOn(), parsedDate);
                assertEquals(response.getCancelStatus(), CancelStatus.SUCCESSFUL);
                assertEquals(response.getMetadata().get("product_id"), "updated123");
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
  public void updateCancelMetadata() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/stores/"
            + storeId.toString()
            + "/charges/"
            + chargeId.toString()
            + "/cancels/"
            + cancelId.toString(),
        jwt,
        200,
        CancelsFakeRR.updateCancelFakeResponseMetadata,
        CancelsFakeRR.updateCancelFakeRequestMetadata);

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

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    Cancel response =
        univapay
            .updateCancel(storeId, chargeId, cancelId)
            .withMetadata(metadata)
            .build()
            .dispatch();
    assertThat(response.getMetadata().get(arrayKey), is(arrayValue));
    assertThat(response.getMetadata().get(floatKey), is(floatValue));
    assertThat(response.getMetadata().get(numberKey), is(numberValue));
    assertThat(response.getMetadata().get(stringKey), is(stringValue));
  }

  @Test
  public void updateCancelUniqueMetadata() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/stores/"
            + storeId.toString()
            + "/charges/"
            + chargeId.toString()
            + "/cancels/"
            + cancelId.toString(),
        jwt,
        200,
        CancelsFakeRR.cancelFakeResponseMetadataFloat,
        CancelsFakeRR.cancelFakeRequestMetadataFloat);

    final Map<String, Float> metadata = new LinkedHashMap<>();

    final String floatKey = "float";
    final Float floatValue = Float.valueOf("10.3");
    metadata.put(floatKey, floatValue);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    MetadataFloatAdapter adapter = new MetadataFloatAdapter();

    Cancel response =
        univapay
            .updateCancel(storeId, chargeId, cancelId)
            .withMetadata(metadata, adapter)
            .build()
            .dispatch();
    assertThat(response.getMetadata(adapter).get(floatKey), is(floatValue));
  }
}
