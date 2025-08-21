package com.univapay.sdk.cancel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.CancelId;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.cancel.Cancel;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CancelStatus;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGeneratorWithAppTokenSecret;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.CancelsFakeRR;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;

class GetCancelTest extends GenericTest {

  final StoreId storeId = new StoreId("11e7b331-ee33-f8ee-a37d-1b150f2ba2f6");
  final ChargeId chargeId = new ChargeId("11e7b333-cb82-3d54-a37d-036f78f60e1c");
  final CancelId cancelId = new CancelId("cdf3ba40-b333-11e7-a37d-d75967ccf22e");

  @Test
  void canGetACancel() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/" + storeId + "/charges/" + chargeId + "/cancels/" + cancelId,
        appToken,
        secret,
        200,
        CancelsFakeRR.getCancelFakeResponse);

    final OffsetDateTime parsedDate = parseDate("2017-10-17T12:07:53.809331Z");

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    univapay
        .getCancel(storeId, chargeId, cancelId)
        .build()
        .dispatch(
            new UnivapayCallback<Cancel>() {
              @Override
              public void getResponse(Cancel response) {
                assertEquals(response.getCancelId().toString(), cancelId.toString());
                assertEquals(response.getChargeId().toString(), chargeId.toString());
                assertEquals(response.getCreatedOn(), parsedDate);
                assertEquals(CancelStatus.SUCCESSFUL, response.getCancelStatus());
                assertEquals("1245", response.getMetadata().get("product_id"));
                assertEquals("12345678", response.getMetadata().get("customer_id"));
                assertEquals(ProcessingMode.TEST, response.getMode());
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
}
