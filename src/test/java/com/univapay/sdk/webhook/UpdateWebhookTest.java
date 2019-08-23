package com.univapay.sdk.webhook;

import static org.junit.Assert.assertEquals;

import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.io.IOException;
import java.net.URL;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.WebhookId;
import com.univapay.sdk.models.response.webhook.Webhook;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import org.junit.Test;

public class UpdateWebhookTest extends GenericTest {

  @Test
  public void shouldPostAndReturnUpdatedWebhookInfo() throws InterruptedException, IOException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "PATCH",
        "/stores/f5cc70be-da82-4fad-affc-e79888189066/webhooks/7b934b64-9c82-433f-a4fe-13a54efc0fc2",
        token,
        200,
        StoreFakeRR.updateStoreWebhookResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay
        .updateWebhook(
            new StoreId("f5cc70be-da82-4fad-affc-e79888189066"),
            new WebhookId("7b934b64-9c82-433f-a4fe-13a54efc0fc2"))
        .withURL(new URL("http://www.anotherurlcorrected.com"))
        .build()
        .dispatch(
            new UnivapayCallback<Webhook>() {
              @Override
              public void getResponse(Webhook response) {
                assertEquals(response.getUrl().toString(), "http://www.anotherurlcorrected.com");
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
