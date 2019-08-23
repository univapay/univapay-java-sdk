package com.univapay.sdk.webhook;

import static org.junit.Assert.assertEquals;

import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.io.IOException;
import java.net.URL;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.WebhookId;
import com.univapay.sdk.models.response.webhook.Webhook;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import org.junit.Test;

public class UpdateMerchantWebhookTest extends GenericTest {

  @Test
  public void shouldPostAndReturnUpdatedWebhookInfo() throws InterruptedException, IOException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "PATCH",
        "/webhooks/11e7a41f-8a04-9980-a983-c34a960d1344",
        token,
        200,
        StoreFakeRR.updateMerchantWebhookResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final String WEBHOOK_URL = "http://www.webhook.com";

    univapay
        .updateWebhook(new WebhookId("11e7a41f-8a04-9980-a983-c34a960d1344"))
        .withURL(new URL(WEBHOOK_URL))
        .build()
        .dispatch(
            new UnivapayCallback<Webhook>() {
              @Override
              public void getResponse(Webhook response) {
                assertEquals(response.getUrl().toString(), WEBHOOK_URL);
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
