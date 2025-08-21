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
import org.junit.jupiter.api.Test;

class RefundsPaginationTest extends GenericTest {
  @Test
  void shouldRequestRefundsWithPaginationParams() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges"
            + "/6791acdd-d901-49b8-a46f-24a7a39e894f/refunds"
            + "?limit=2&cursor_direction=desc&cursor=04ea4e3e-3f19-43d3-8593-fed3aba06771",
        jwt,
        200,
        ChargesFakeRR.listAllRefundsFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

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
  void shouldRequestNext() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges"
            + "/6791acdd-d901-49b8-a46f-24a7a39e894f/refunds",
        jwt,
        200,
        ChargesFakeRR.listAllRefundsFakeResponse);

    UnivapaySDK payments = createTestInstance(AuthType.JWT);

    payments
        .listRefunds(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new ChargeId("6791acdd-d901-49b8-a46f-24a7a39e894f"))
        .asIterable()
        .iterator()
        .next();
  }
}
