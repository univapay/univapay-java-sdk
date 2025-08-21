package com.univapay.sdk.transactiontoken;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.UnivapayCustomerId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.transactiontoken.TransactionToken;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.TransactionTokenType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ListTransactionTokenTest extends GenericTest {

  @Test
  void shouldRequestAndReturnListOfTransactionTokens() throws Exception {
    UUID customerId = UUID.randomUUID();
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/tokens?all=true&search=nada&mode=test&type=recurring&customer_id=" + customerId,
        jwt,
        200,
        StoreFakeRR.listTransactionTokenFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate0 = parseDate("2017-08-24T02:23:53.000000+09:00");
    final OffsetDateTime parsedDate1 = parseDate("2017-08-24T02:04:31.000000+09:00");

    univapay
        .listTransactionTokens()
        .withListAll(true)
        .withSearch("nada")
        .withMode(ProcessingMode.TEST)
        .withType(TransactionTokenType.RECURRING)
        .withCustomerId(new UnivapayCustomerId(customerId))
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<TransactionToken>>() {
              @Override
              public void getResponse(PaginatedList<TransactionToken> response) {
                try {
                  assertFalse(response.getHasMore());

                  assertTrue(response.getItems().get(0).getActive());
                  assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate0);
                  assertEquals(
                      "11e78873-45e4-5046-862b-a7a5e83cd63c",
                      response.getItems().get(0).getId().toString());
                  assertEquals(ProcessingMode.TEST, response.getItems().get(0).getMode());
                  assertEquals(
                      response.getItems().get(0).getPaymentTypeName(),
                      new Gson().fromJson("card", PaymentTypeName.class));
                  assertEquals(
                      "11e786da-4714-5028-8280-bb9bc7cf54e9",
                      response.getItems().get(0).getStoreId().toString());
                  assertEquals(
                      TransactionTokenType.RECURRING, response.getItems().get(0).getType());

                  assertTrue(response.getItems().get(1).getActive());
                  assertEquals(response.getItems().get(1).getCreatedOn(), parsedDate1);
                  assertEquals(
                      "11e78870-912b-d69e-9cb4-ff984d158c41",
                      response.getItems().get(1).getId().toString());
                  assertEquals(ProcessingMode.TEST, response.getItems().get(1).getMode());
                  assertEquals(
                      response.getItems().get(1).getPaymentTypeName(),
                      new Gson().fromJson("card", PaymentTypeName.class));
                  assertEquals(
                      "11e786da-4714-5028-8280-bb9bc7cf54e9",
                      response.getItems().get(1).getStoreId().toString());
                  assertEquals(
                      TransactionTokenType.RECURRING, response.getItems().get(1).getType());
                } catch (Exception e) {
                  fail(e.getMessage());
                } finally {
                  notifyCall();
                }
              }

              @Override
              public void getFailure(Throwable error) {
                fail(error.getMessage());
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  void shouldRequestTokensForStore() throws Exception {
    UUID customerId = UUID.randomUUID();
    StoreId storeId = new StoreId(UUID.randomUUID());
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/"
            + storeId
            + "/tokens?all=true&search=nada&mode=test&type=recurring&customer_id="
            + customerId,
        jwt,
        200,
        "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .listTransactionTokens(storeId)
        .withListAll(true)
        .withSearch("nada")
        .withMode(ProcessingMode.TEST)
        .withType(TransactionTokenType.RECURRING)
        .withCustomerId(new UnivapayCustomerId(customerId))
        .build()
        .dispatch();
  }
}
