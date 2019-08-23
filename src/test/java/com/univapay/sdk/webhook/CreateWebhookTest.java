package com.univapay.sdk.webhook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.webhook.Webhook;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.PaymentSystemEvent;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import org.junit.Test;

public class CreateWebhookTest extends GenericTest {
  @Test
  public void shouldPostAndReturnWebhookInfo() throws InterruptedException, IOException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/stores/f5cc70be-da82-4fad-affc-e79888189066/webhooks",
        token,
        200,
        StoreFakeRR.createStoreWebhookResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

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
                    response.getMerchantId().toString(), "11e81d1c-0990-29f6-8391-9b9a12e8726e");
                assertEquals(
                    response.getStoreId().toString(), "f5cc70be-da82-4fad-affc-e79888189066");
                assertEquals(response.getUrl().toString(), WEBHOOK_URL);
                assertEquals(response.getTriggers().size(), 2);
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
