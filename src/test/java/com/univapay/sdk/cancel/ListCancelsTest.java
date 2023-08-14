package com.univapay.sdk.cancel;

import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.cancel.Cancel;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CancelStatus;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGeneratorWithAppTokenSecret;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.CancelsFakeRR;
import java.time.OffsetDateTime;
import org.junit.Test;

public class ListCancelsTest extends GenericTest {
  @Test
  public void shouldListCancels() throws InterruptedException {

    final StoreId storeId = new StoreId("11e7b331-ee33-f8ee-a37d-1b150f2ba2f6");
    final ChargeId chargeId = new ChargeId("11e7b333-cb82-3d54-a37d-036f78f60e1c");

    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/" + storeId + "/charges" + "/" + chargeId + "/cancels",
        appToken,
        secret,
        200,
        CancelsFakeRR.listCancelsFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-10-17T12:07:53.809331Z");

    univapay
        .listCancels(storeId, chargeId)
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Cancel>>() {
              @Override
              public void getResponse(PaginatedList<Cancel> response) {
                assertFalse(response.getHasMore());
                Cancel cancel = response.getItems().get(0);
                assertEquals(
                    cancel.getCancelId().toString(), "cdf3ba40-b333-11e7-a37d-d75967ccf22e");
                assertEquals(cancel.getChargeId().toString(), chargeId.toString());
                assertEquals(cancel.getCreatedOn(), parsedDate);
                assertEquals(cancel.getCancelStatus(), CancelStatus.SUCCESSFUL);
                assertEquals(cancel.getMetadata().get("product_id"), "updated123");
                assertEquals(cancel.getMode(), ProcessingMode.TEST);
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
