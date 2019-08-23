package com.univapay.sdk.pagination;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.RefundId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.refund.Refund;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import org.junit.Test;

public class RefundsPaginationTest extends GenericTest {
  @Test
  public void shouldRequestRefundsWithPaginationParams() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges"
            + "/6791acdd-d901-49b8-a46f-24a7a39e894f/refunds"
            + "?limit=2&cursor_direction=desc&cursor=04ea4e3e-3f19-43d3-8593-fed3aba06771",
        token,
        200,
        ChargesFakeRR.listAllRefundsFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay
        .listRefunds(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new ChargeId("6791acdd-d901-49b8-a46f-24a7a39e894f"))
        .setLimit(2)
        .setCursorDirection(CursorDirection.DESC)
        .setCursor(new RefundId("04ea4e3e-3f19-43d3-8593-fed3aba06771"))
        .build()
        .dispatch(new ExpectSuccessCallback<PaginatedList<Refund>>());

    waitCall();
  }

  @Test
  public void shouldRequestNext() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges"
            + "/6791acdd-d901-49b8-a46f-24a7a39e894f/refunds",
        token,
        200,
        ChargesFakeRR.listAllRefundsFakeResponse);

    UnivapaySDK payments = createTestInstance(AuthType.LOGIN_TOKEN);

    payments
        .listRefunds(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new ChargeId("6791acdd-d901-49b8-a46f-24a7a39e894f"))
        .asIterable()
        .iterator()
        .next();
  }
}
