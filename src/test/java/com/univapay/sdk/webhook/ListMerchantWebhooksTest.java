package com.univapay.sdk.webhook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.text.ParseException;
import java.util.Date;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.webhook.Webhook;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.PaymentSystemEvent;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import org.junit.Test;

public class ListMerchantWebhooksTest extends GenericTest {
  @Test
  public void shouldRequestAndReturnListOfWebhooks() throws InterruptedException, ParseException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET", "/webhooks", token, 200, StoreFakeRR.listAllMerchantWebhooksResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final Date parsedDate0 = dateParser.parseDateTime("2017-09-28T07:35:02.000000+09:00").toDate();
    final Date parsedDate1 = dateParser.parseDateTime("2017-09-21T05:33:16.000000+09:00").toDate();

    univapay
        .listWebhooks()
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Webhook>>() {
              @Override
              public void getResponse(PaginatedList<Webhook> response) {

                assertFalse(response.getHasMore());

                assertEquals(
                    response.getItems().get(0).getId().toString(),
                    "11e7a41f-8a04-9980-a983-c34a960d1344");
                assertEquals(
                    response.getItems().get(0).getTriggers().get(0),
                    PaymentSystemEvent.TRANSFER_FINALIZED);
                assertEquals(
                    response.getItems().get(0).getUrl().toString(), "http://www.webhook.com");
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate0);
                assertEquals(response.getItems().get(0).getUpdatedOn(), parsedDate0);

                assertEquals(
                    response.getItems().get(1).getId().toString(),
                    "11e79e8e-5eb2-2540-a5e6-f7fda438424b");
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
