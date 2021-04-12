package com.univapay.sdk.pagination;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.subscription.Subscription;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import org.junit.Test;

public class SubscriptionsPaginationTest extends GenericTest {

  @Test
  public void shouldRequestSubscriptionsWithPaginationParams() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/45facc11-efc8-4156-8ef3-e363a70a54c3/subscriptions?limit=1&cursor_direction=desc&cursor=714498e7-5031-4d48-b2ac-9bad8a8a4142",
        jwt,
        200,
        ChargesFakeRR.listAllStoreSubscriptionsFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .listSubscriptions(new StoreId("45facc11-efc8-4156-8ef3-e363a70a54c3"))
        .setLimit(1)
        .setCursorDirection(CursorDirection.DESC)
        .setCursor(new SubscriptionId("714498e7-5031-4d48-b2ac-9bad8a8a4142"))
        .build()
        .dispatch(new ExpectSuccessCallback<PaginatedList<Subscription>>());

    waitCall();
  }

  @Test
  public void shouldRequestNext() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/45facc11-efc8-4156-8ef3-e363a70a54c3/subscriptions",
        jwt,
        200,
        ChargesFakeRR.listAllStoreSubscriptionsFakeResponse);

    UnivapaySDK payments = createTestInstance(AuthType.JWT);

    payments
        .listSubscriptions(new StoreId("45facc11-efc8-4156-8ef3-e363a70a54c3"))
        .asIterable()
        .iterator()
        .next();
  }
}
