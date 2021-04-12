package com.univapay.sdk.webhook;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.common.WebhookId;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import org.junit.Test;

public class DeleteMerchantWebhookTest extends GenericTest {

  @Test
  public void shouldRequestDeletionOfWebhook() throws InterruptedException {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "DELETE", "/webhooks/11e7a4e4-5a21-fc74-8205-17c780e6c7ee", jwt, 200, "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .deleteWebhook(new WebhookId("11e7a4e4-5a21-fc74-8205-17c780e6c7ee"))
        .build()
        .dispatch(new ExpectSuccessCallback<Void>());

    waitCall();

    verify(deleteRequestedFor(urlEqualTo("/webhooks/11e7a4e4-5a21-fc74-8205-17c780e6c7ee")));
  }
}
