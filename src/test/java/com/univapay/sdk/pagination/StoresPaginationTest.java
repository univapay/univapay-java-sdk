package com.univapay.sdk.pagination;

import static org.junit.Assert.fail;

import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.store.Store;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import org.junit.Test;

public class StoresPaginationTest extends GenericTest {

  @Test
  public void shouldRequestStoresWithPaginationParams() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores?limit=2&cursor_direction=desc&cursor=8486dc98-9836-41dd-b598-bbf49d5bc862",
        token,
        200,
        StoreFakeRR.listAllStoresPaginationParamFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay
        .listStores()
        .setLimit(2)
        .setCursorDirection(CursorDirection.DESC)
        .setCursor(new StoreId("8486dc98-9836-41dd-b598-bbf49d5bc862"))
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Store>>() {
              @Override
              public void getResponse(PaginatedList<Store> response) {
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                fail();
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  public void shouldRequestNext() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET", "/stores", token, 200, StoreFakeRR.listAllStoresPaginationParamFakeResponse);

    UnivapaySDK payments = createTestInstance(AuthType.LOGIN_TOKEN);

    payments.listStores().asIterable().iterator().next();
  }
}
