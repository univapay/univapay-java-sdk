package com.univapay.sdk.webhook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.WebhookId;
import com.univapay.sdk.models.response.webhook.Webhook;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.PaymentSystemEvent;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import org.junit.Test;

public class GetWebhookTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnWebhookInfo() throws InterruptedException, ParseException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/8486dc98-9836-41dd-b598-bbf49d5bc861/webhooks/f3002de3-c076-4194-bef5-c88882e4f5fe",
        token,
        200,
        StoreFakeRR.getStoreWebhookResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final Date parsedDate = dateParser.parseDateTime("2017-06-22T16:00:55.436116+09:00").toDate();

    final List<PaymentSystemEvent> triggers = new ArrayList<>();
    triggers.add(PaymentSystemEvent.CHARGE_FINISHED);
    triggers.add((PaymentSystemEvent.SUBSCRIPTION_PAYMENT));
    triggers.add((PaymentSystemEvent.REFUND_FINISHED));
    triggers.add((PaymentSystemEvent.SUBSCRIPTION_CANCELED));
    triggers.add((PaymentSystemEvent.TRANSFER_FINALIZED));
    triggers.add((PaymentSystemEvent.SUBSCRIPTION_FAILURE));

    final String WEBHOOK_URL = "http://www.webhook.com";

    univapay
        .getWebhook(
            new StoreId("8486dc98-9836-41dd-b598-bbf49d5bc861"),
            new WebhookId("f3002de3-c076-4194-bef5-c88882e4f5fe"))
        .build()
        .dispatch(
            new UnivapayCallback<Webhook>() {
              @Override
              public void getResponse(Webhook response) {

                assertEquals(response.getId().toString(), "f3002de3-c076-4194-bef5-c88882e4f5fe");
                assertEquals(
                    response.getStoreId().toString(), "8486dc98-9836-41dd-b598-bbf49d5bc861");
                assertEquals(response.getTriggers().size(), 6);
                assertTrue(response.getTriggers().containsAll(triggers));
                assertEquals(response.getUrl().toString(), WEBHOOK_URL);
                assertEquals(response.getCreatedOn(), parsedDate);
                assertEquals(response.getUpdatedOn(), parsedDate);
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
