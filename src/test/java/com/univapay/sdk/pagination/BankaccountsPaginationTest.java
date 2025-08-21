package com.univapay.sdk.pagination;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.BankAccountId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.bankaccount.BankAccount;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.BankAccountsFakeRR;
import org.junit.jupiter.api.Test;

class BankaccountsPaginationTest extends GenericTest {

  @Test
  void shouldRequestBankAccountsWithPaginationParams() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/bank_accounts?limit=4&cursor_direction=asc&cursor=6692a026-f2f6-499e-9d90-abd957bc89d8",
        jwt,
        200,
        BankAccountsFakeRR.listAllBankAccountsFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .listBankAccounts()
        .setLimit(4)
        .setCursorDirection(CursorDirection.ASC)
        .setCursor(new BankAccountId("6692a026-f2f6-499e-9d90-abd957bc89d8"))
        .build()
        .dispatch(new ExpectSuccessCallback<PaginatedList<BankAccount>>());

    waitCall();
  }

  @Test
  void shouldRequestNext() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET", "/bank_accounts", jwt, 200, BankAccountsFakeRR.listAllBankAccountsFakeResponse);

    UnivapaySDK payments = createTestInstance(AuthType.JWT);

    payments.listBankAccounts().asIterable().iterator().next();
  }
}
