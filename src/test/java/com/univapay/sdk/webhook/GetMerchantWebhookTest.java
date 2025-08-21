package com.univapay.sdk.webhook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.WebhookId;
import com.univapay.sdk.models.response.webhook.Webhook;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.PaymentSystemEvent;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class GetMerchantWebhookTest extends GenericTest {

  @Test
  void shouldRequestAndReturnWebhookInfo() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/webhooks/11e796c8-a853-8928-9665-a709cfc94f15",
        jwt,
        200,
        StoreFakeRR.getMerchantWebhookResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2017-09-11T08:10:21.000000+09:00", DateTimeFormatter.ISO_DATE_TIME);

    final String WEBHOOK_URL = "http://www.webhook.com";

    final List<PaymentSystemEvent> triggers = new ArrayList<>();
    triggers.add(PaymentSystemEvent.CHARGE_FINISHED);
    triggers.add(PaymentSystemEvent.SUBSCRIPTION_FAILURE);

    univapay
        .getWebhook(new WebhookId("11e796c8-a853-8928-9665-a709cfc94f15"))
        .build()
        .dispatch(
            new UnivapayCallback<Webhook>() {
              @Override
              public void getResponse(Webhook response) {
                assertEquals("11e796c8-a853-8928-9665-a709cfc94f15", response.getId().toString());
                assertEquals(
                    "11e81d1c-0990-29f6-8391-9b9a12e8726e", response.getMerchantId().toString());
                assertEquals(
                    "11e786da-4714-5028-8280-bb9bc7cf54e9", response.getStoreId().toString());
                assertEquals(response.getUrl().toString(), WEBHOOK_URL);
                assertEquals(2, response.getTriggers().size());
                assertTrue(response.getTriggers().containsAll(triggers));
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
