package com.univapay.sdk.webhook;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.common.WebhookId;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import org.junit.Test;

public class DeleteWebhookTest extends GenericTest {

  @Test
  public void shouldRequestDeletionOfWebhook() throws InterruptedException {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "DELETE",
        "/stores/f5cc70be-da82-4fad-affc-e79888189066/webhooks/7b934b64-9c82-433f-a4fe-13a54efc0fc2",
        jwt,
        200,
        "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .deleteWebhook(
            new StoreId("f5cc70be-da82-4fad-affc-e79888189066"),
            new WebhookId("7b934b64-9c82-433f-a4fe-13a54efc0fc2"))
        .build()
        .dispatch(new ExpectSuccessCallback<Void>());

    waitCall();

    verify(
        deleteRequestedFor(
            urlEqualTo(
                "/stores/f5cc70be-da82-4fad-affc-e79888189066/webhooks/7b934b64-9c82-433f-a4fe-13a54efc0fc2")));
  }
}
