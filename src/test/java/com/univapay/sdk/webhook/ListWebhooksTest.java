package com.univapay.sdk.webhook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Test;

public class ListWebhooksTest extends GenericTest {
  @Test
  public void shouldRequestAndReturnListOfWebhooks() throws InterruptedException, ParseException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/8486dc98-9836-41dd-b598-bbf49d5bc861/webhooks",
        token,
        200,
        StoreFakeRR.listAllStoreWebhooksResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

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
                    response.getItems().get(0).getId().toString(),
                    "9a363dc6-ce7d-4b6d-af5b-b92aebd0bf40");
                assertEquals(
                    response.getItems().get(0).getStoreId().toString(),
                    "8486dc98-9836-41dd-b598-bbf49d5bc861");
                assertEquals(
                    response.getItems().get(0).getTriggers().get(0),
                    PaymentSystemEvent.CHARGE_FINISHED);
                assertEquals(
                    response.getItems().get(0).getTriggers().get(1),
                    PaymentSystemEvent.SUBSCRIPTION_CANCELED);
                assertEquals(
                    response.getItems().get(0).getTriggers().get(2),
                    PaymentSystemEvent.REFUND_FINISHED);
                assertEquals(
                    response.getItems().get(0).getTriggers().get(3),
                    PaymentSystemEvent.TRANSFER_FINALIZED);
                assertEquals(
                    response.getItems().get(0).getUrl().toString(),
                    "http://www.anotherwebhook.com");
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);
                assertEquals(response.getItems().get(0).getUpdatedOn(), parsedDate);

                assertEquals(
                    response.getItems().get(1).getId().toString(),
                    "f3002de3-c076-4194-bef5-c88882e4f5fe");
                assertEquals(
                    response.getItems().get(1).getStoreId().toString(),
                    "8486dc98-9836-41dd-b598-bbf49d5bc861");
                assertEquals(
                    response.getItems().get(1).getTriggers().get(0),
                    PaymentSystemEvent.CHARGE_FINISHED);
                assertEquals(
                    response.getItems().get(1).getTriggers().get(1),
                    PaymentSystemEvent.SUBSCRIPTION_PAYMENT);
                assertEquals(
                    response.getItems().get(1).getTriggers().get(2),
                    PaymentSystemEvent.REFUND_FINISHED);
                assertEquals(
                    response.getItems().get(1).getTriggers().get(3),
                    PaymentSystemEvent.SUBSCRIPTION_CANCELED);
                assertEquals(
                    response.getItems().get(1).getTriggers().get(4),
                    PaymentSystemEvent.TRANSFER_FINALIZED);
                assertEquals(
                    response.getItems().get(1).getTriggers().get(5),
                    PaymentSystemEvent.SUBSCRIPTION_FAILURE);
                assertEquals(
                    response.getItems().get(1).getUrl().toString(), "http://www.webhook.com");
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
