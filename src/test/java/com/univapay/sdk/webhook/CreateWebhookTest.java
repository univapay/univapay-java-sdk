package com.univapay.sdk.webhook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.webhook.Webhook;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.PaymentSystemEvent;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CreateWebhookTest extends GenericTest {
  @Test
  void shouldPostAndReturnWebhookInfo() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/stores/f5cc70be-da82-4fad-affc-e79888189066/webhooks",
        jwt,
        200,
        StoreFakeRR.createStoreWebhookResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final List<PaymentSystemEvent> triggers = new ArrayList<>();
    triggers.add(PaymentSystemEvent.CHARGE_FINISHED);
    triggers.add(PaymentSystemEvent.SUBSCRIPTION_SUSPENDED);

    final String WEBHOOK_URL = "http://www.anotherurl.com";

    univapay
        .createWebhook(new StoreId("f5cc70be-da82-4fad-affc-e79888189066"), new URL(WEBHOOK_URL))
        .build()
        .dispatch(
            new UnivapayCallback<Webhook>() {
              @Override
              public void getResponse(Webhook response) {
                assertEquals(response.getUrl().toString(), WEBHOOK_URL);
                assertEquals(
                    "11e81d1c-0990-29f6-8391-9b9a12e8726e", response.getMerchantId().toString());
                assertEquals(
                    "f5cc70be-da82-4fad-affc-e79888189066", response.getStoreId().toString());
                assertEquals(response.getUrl().toString(), WEBHOOK_URL);
                assertEquals(2, response.getTriggers().size());
                assertTrue(response.getTriggers().containsAll(triggers));
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
