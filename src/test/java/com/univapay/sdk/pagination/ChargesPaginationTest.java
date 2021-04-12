package com.univapay.sdk.pagination;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import org.junit.Test;

public class ChargesPaginationTest extends GenericTest {
  @Test
  public void shouldRequestChargesWithPaginationParams() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges?limit=2&cursor_direction=desc&cursor=e544b386-b13c-42c9-ab77-b36f95c99eab",
        jwt,
        200,
        ChargesFakeRR.listAllStoreChargesFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .listCharges(new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"))
        .setLimit(2)
        .setCursorDirection(CursorDirection.DESC)
        .setCursor(new ChargeId("e544b386-b13c-42c9-ab77-b36f95c99eab"))
        .build()
        .dispatch(new ExpectSuccessCallback<PaginatedList<Charge>>());

    waitCall();
  }

  @Test
  public void shouldRequestNext() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges",
        jwt,
        200,
        ChargesFakeRR.listAllStoreChargesFakeResponse);

    UnivapaySDK payments = createTestInstance(AuthType.JWT);

    payments
        .listCharges(new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"))
        .asIterable()
        .iterator()
        .next();
  }
}
