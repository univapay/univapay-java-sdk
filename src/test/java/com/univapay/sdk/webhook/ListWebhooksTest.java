package com.univapay.sdk.webhook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
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

class ListWebhooksTest extends GenericTest {
  @Test
  void shouldRequestAndReturnListOfWebhooks() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/8486dc98-9836-41dd-b598-bbf49d5bc861/webhooks",
        jwt,
        200,
        StoreFakeRR.listAllStoreWebhooksResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2017-06-22T16:00:55.436116+09:00", DateTimeFormatter.ISO_DATE_TIME);

    univapay
        .listWebhooks(new StoreId("8486dc98-9836-41dd-b598-bbf49d5bc861"))
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Webhook>>() {
              @Override
              public void getResponse(PaginatedList<Webhook> response) {

                assertFalse(response.getHasMore());
                assertEquals(
                    "9a363dc6-ce7d-4b6d-af5b-b92aebd0bf40",
                    response.getItems().get(0).getId().toString());
                assertEquals(
                    "8486dc98-9836-41dd-b598-bbf49d5bc861",
                    response.getItems().get(0).getStoreId().toString());
                assertEquals(
                    PaymentSystemEvent.CHARGE_FINISHED,
                    response.getItems().get(0).getTriggers().get(0));
                assertEquals(
                    PaymentSystemEvent.SUBSCRIPTION_CANCELED,
                    response.getItems().get(0).getTriggers().get(1));
                assertEquals(
                    PaymentSystemEvent.REFUND_FINISHED,
                    response.getItems().get(0).getTriggers().get(2));
                assertEquals(
                    PaymentSystemEvent.TRANSFER_FINALIZED,
                    response.getItems().get(0).getTriggers().get(3));
                assertEquals(
                    "http://www.anotherwebhook.com",
                    response.getItems().get(0).getUrl().toString());
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);
                assertEquals(response.getItems().get(0).getUpdatedOn(), parsedDate);

                assertEquals(
                    "f3002de3-c076-4194-bef5-c88882e4f5fe",
                    response.getItems().get(1).getId().toString());
                assertEquals(
                    "8486dc98-9836-41dd-b598-bbf49d5bc861",
                    response.getItems().get(1).getStoreId().toString());
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
                assertEquals(response.getItems().get(1).getCreatedOn(), parsedDate);
                assertEquals(response.getItems().get(1).getUpdatedOn(), parsedDate);
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
