package com.univapay.sdk.webhook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.webhook.Webhook;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.PaymentSystemEvent;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class ListMerchantWebhooksTest extends GenericTest {
  @Test
  void shouldRequestAndReturnListOfWebhooks() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET", "/webhooks", jwt, 200, StoreFakeRR.listAllMerchantWebhooksResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate0 =
        OffsetDateTime.parse("2017-09-28T07:35:02.000000+09:00", DateTimeFormatter.ISO_DATE_TIME);
    final OffsetDateTime parsedDate1 =
        OffsetDateTime.parse("2017-09-21T05:33:16.000000+09:00", DateTimeFormatter.ISO_DATE_TIME);

    univapay
        .listWebhooks()
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Webhook>>() {
              @Override
              public void getResponse(PaginatedList<Webhook> response) {

                assertFalse(response.getHasMore());

                assertEquals(
                    "11e7a41f-8a04-9980-a983-c34a960d1344",
                    response.getItems().get(0).getId().toString());
                assertEquals(
                    PaymentSystemEvent.TRANSFER_FINALIZED,
                    response.getItems().get(0).getTriggers().get(0));
                assertEquals(
                    "http://www.webhook.com", response.getItems().get(0).getUrl().toString());
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate0);
                assertEquals(response.getItems().get(0).getUpdatedOn(), parsedDate0);

                assertEquals(
                    "11e79e8e-5eb2-2540-a5e6-f7fda438424b",
                    response.getItems().get(1).getId().toString());
                assertEquals(
                    PaymentSystemEvent.CHARGE_FINISHED,
                    response.getItems().get(1).getTriggers().get(0));
                assertEquals(
                    PaymentSystemEvent.SUBSCRIPTION_PAYMENT,
                    response.getItems().get(1).getTriggers().get(1));
                assertEquals(
                    PaymentSystemEvent.REFUND_FINISHED,
                    response.getItems().get(1).getTriggers().get(2));
                assertEquals(
                    PaymentSystemEvent.SUBSCRIPTION_CANCELED,
                    response.getItems().get(1).getTriggers().get(3));
                assertEquals(
                    PaymentSystemEvent.TRANSFER_FINALIZED,
                    response.getItems().get(1).getTriggers().get(4));
                assertEquals(
                    PaymentSystemEvent.SUBSCRIPTION_FAILURE,
                    response.getItems().get(1).getTriggers().get(5));
                assertEquals(
                    "http://www.webhook.com", response.getItems().get(1).getUrl().toString());
                assertEquals(response.getItems().get(1).getCreatedOn(), parsedDate1);
                assertEquals(response.getItems().get(1).getUpdatedOn(), parsedDate1);

                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }
}
