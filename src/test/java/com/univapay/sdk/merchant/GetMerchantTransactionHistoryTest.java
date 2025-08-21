package com.univapay.sdk.merchant;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
import org.junit.jupiter.api.Test;

class GetMerchantTransactionHistoryTest extends GenericTest {

  @Test
  void shouldRequestAndReturnMerchantVerificationData() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/transaction_history",
        jwt,
        200,
        MerchantsFakeRR.getMerchantTransactionHistoryFakeResponse);
    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .getTransactionHistory()
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Transaction>>() {
              @Override
              public void getResponse(PaginatedList<Transaction> response) {
                assertFalse(response.getHasMore());
                assertEquals(
                    "63d3e4ad-ca05-435a-82ca-addb276cb01b",
                    response.getItems().get(0).getResourceId().toString());
                assertEquals(
                    "d2aecb69-7d8b-42b1-802f-534197797ba2",
                    response.getItems().get(1).getResourceId().toString());
                assertEquals(response.getItems().get(0).getAmount(), BigInteger.valueOf(15));
                assertEquals(response.getItems().get(1).getAmount(), BigInteger.valueOf(1000));
                assertEquals(
                    TransactionType.REFUND, response.getItems().get(0).getTransactionType());
                assertEquals(
                    TransactionType.CHARGE, response.getItems().get(1).getTransactionType());
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
