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
import org.junit.Test;

public class BankaccountsPaginationTest extends GenericTest {

  @Test
  public void shouldRequestBankAccountsWithPaginationParams() throws InterruptedException {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/bank_accounts?limit=4&cursor_direction=asc&cursor=6692a026-f2f6-499e-9d90-abd957bc89d8",
        token,
        200,
        BankAccountsFakeRR.listAllBankAccountsFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

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
  public void shouldRequestNext() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET", "/bank_accounts", token, 200, BankAccountsFakeRR.listAllBankAccountsFakeResponse);

    UnivapaySDK payments = createTestInstance(AuthType.LOGIN_TOKEN);

    payments.listBankAccounts().asIterable().iterator().next();
  }
}
