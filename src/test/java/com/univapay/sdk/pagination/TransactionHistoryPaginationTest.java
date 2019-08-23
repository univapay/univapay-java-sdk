package com.univapay.sdk.pagination;

import static org.junit.Assert.fail;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.ResourceId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.merchant.Transaction;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.MerchantsFakeRR;
import org.junit.Test;

public class TransactionHistoryPaginationTest extends GenericTest {
  @Test
  public void shouldRequestTransactionHistoryWithPaginationParams() throws InterruptedException {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/45facc11-efc8-4156-8ef3-e363a70a54c3/transaction_history?limit=3&cursor_direction=asc&cursor=e1771339-b989-4a43-99c1-5e35d8008427",
        token,
        200,
        MerchantsFakeRR.getStoreTransactionHistoryFakeResponse);
    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay
        .getTransactionHistory(new StoreId("45facc11-efc8-4156-8ef3-e363a70a54c3"))
        .setLimit(3)
        .setCursorDirection(CursorDirection.ASC)
        .setCursor(new ResourceId("e1771339-b989-4a43-99c1-5e35d8008427"))
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Transaction>>() {
              @Override
              public void getResponse(PaginatedList<Transaction> response) {
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                fail();
                notify();
              }
            });

    waitCall();
  }

  @Test
  public void shouldRequestNext() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/45facc11-efc8-4156-8ef3-e363a70a54c3/transaction_history",
        token,
        200,
        MerchantsFakeRR.getStoreTransactionHistoryFakeResponse);

    UnivapaySDK payments = createTestInstance(AuthType.LOGIN_TOKEN);

    payments
        .getTransactionHistory(new StoreId("45facc11-efc8-4156-8ef3-e363a70a54c3"))
        .asIterable()
        .iterator()
        .next();
  }
}
