package com.univapay.sdk.pagination;

import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.WebhookId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.webhook.Webhook;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import org.junit.Test;

public class WebhooksPaginationTest extends GenericTest {
  @Test
  public void shouldRequestWebhooksWithPaginationParams() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/8486dc98-9836-41dd-b598-bbf49d5bc861/webhooks?limit=2&cursor_direction=desc&cursor=9a363dc6-ce7d-4b6d-af5b-b92aebd0bf41",
        token,
        200,
        StoreFakeRR.listAllStoreWebhooksResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay
        .listWebhooks(new StoreId("8486dc98-9836-41dd-b598-bbf49d5bc861"))
        .setLimit(2)
        .setCursorDirection(CursorDirection.DESC)
        .setCursor(new WebhookId("9a363dc6-ce7d-4b6d-af5b-b92aebd0bf41"))
        .build()
        .dispatch(new ExpectSuccessCallback<PaginatedList<Webhook>>());

    waitCall();
  }

  @Test
  public void shouldRequestNext() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/8486dc98-9836-41dd-b598-bbf49d5bc861/webhooks",
        token,
        200,
        StoreFakeRR.listAllStoreWebhooksResponse);

    UnivapaySDK payments = createTestInstance(AuthType.LOGIN_TOKEN);

    payments
        .listWebhooks(new StoreId("8486dc98-9836-41dd-b598-bbf49d5bc861"))
        .asIterable()
        .iterator()
        .next();
  }
}
