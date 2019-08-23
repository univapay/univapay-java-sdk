package com.univapay.sdk.subscription;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import org.junit.Test;

public class DeleteSubscriptionTest extends GenericTest {

  @Test
  public void shouldRequestDeletionOfSubscription() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "DELETE",
        "/stores/45facc11-efc8-4156-8ef3-e363a70a54c3/subscriptions/714498e7-5031-4d48-b2ac-9bad8a8a4141",
        token,
        200,
        "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay
        .deleteSubscription(
            new StoreId("45facc11-efc8-4156-8ef3-e363a70a54c3"),
            new SubscriptionId("714498e7-5031-4d48-b2ac-9bad8a8a4141"))
        .build()
        .dispatch(new ExpectSuccessCallback<Void>());

    waitCall();

    verify(
        deleteRequestedFor(
            urlEqualTo(
                "/stores/45facc11-efc8-4156-8ef3-e363a70a54c3/subscriptions/714498e7-5031-4d48-b2ac-9bad8a8a4141")));
  }
}
