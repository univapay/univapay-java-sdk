package com.univapay.sdk.webhook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.response.webhook.Webhook;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.PaymentSystemEvent;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import org.junit.Test;

public class CreateMerchantWebhookTest extends GenericTest {
  @Test
  public void shouldPostAndReturnWebhookInfo() throws InterruptedException, IOException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST", "/webhooks", token, 200, StoreFakeRR.createMerchantWebhookResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);
    final List<PaymentSystemEvent> triggers = new ArrayList<>();
    triggers.add(PaymentSystemEvent.CHARGE_FINISHED);
    triggers.add(PaymentSystemEvent.SUBSCRIPTION_SUSPENDED);

    final String WEBHOOK_URL = "http://www.webhook.com";

    univapay
        .createWebhook(new URL(WEBHOOK_URL))
        .withTriggers(triggers)
        .build()
        .dispatch(
            new UnivapayCallback<Webhook>() {
              @Override
              public void getResponse(Webhook response) {
                assertEquals(
                    response.getMerchantId().toString(), "11e81d1c-0990-29f6-8391-9b9a12e8726e");
                assertEquals(
                    response.getStoreId().toString(), "11e786da-4714-5028-8280-bb9bc7cf54e9");
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
