package com.univapay.sdk.merchant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.merchant.Transaction;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.TransactionType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.MerchantsFakeRR;
import java.math.BigInteger;
import org.junit.Test;

public class GetMerchantTransactionHistoryTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnMerchantVerificationData() throws InterruptedException {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/transaction_history",
        token,
        200,
        MerchantsFakeRR.getMerchantTransactionHistoryFakeResponse);
    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay
        .getTransactionHistory()
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Transaction>>() {
              @Override
              public void getResponse(PaginatedList<Transaction> response) {
                assertFalse(response.getHasMore());
                assertEquals(
                    response.getItems().get(0).getResourceId().toString(),
                    "63d3e4ad-ca05-435a-82ca-addb276cb01b");
                assertEquals(
                    response.getItems().get(1).getResourceId().toString(),
                    "d2aecb69-7d8b-42b1-802f-534197797ba2");
                assertEquals(response.getItems().get(0).getAmount(), BigInteger.valueOf(15));
                assertEquals(response.getItems().get(1).getAmount(), BigInteger.valueOf(1000));
                assertEquals(
                    response.getItems().get(0).getTransactionType(), TransactionType.REFUND);
                assertEquals(
                    response.getItems().get(1).getTransactionType(), TransactionType.CHARGE);
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }
}
