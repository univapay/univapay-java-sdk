package com.univapay.sdk.bankaccount;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.bankaccount.BankAccount;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.BankAccountStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.BankAccountsFakeRR;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class ListBankAccountsTest extends GenericTest {

  @Test
  void shouldRequestAndReturnListOfBankAccounts() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET", "/bank_accounts", jwt, 200, BankAccountsFakeRR.listAllBankAccountsFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2017-06-22T16:00:55.436116+09:00", DateTimeFormatter.ISO_DATE_TIME);

    univapay
        .listBankAccounts()
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<BankAccount>>() {
              @Override
              public void getResponse(PaginatedList<BankAccount> response) {
                assertFalse(response.getHasMore());
                assertEquals(
                    "6692a026-f2f6-499e-9d90-abd957bc89d9",
                    response.getItems().get(0).getId().toString());
                assertEquals("Person 2", response.getItems().get(1).getHolderName());
                assertEquals("Bank 3", response.getItems().get(2).getBankName());
                assertEquals("Branch 1", response.getItems().get(3).getBranchName());
                assertEquals("JP", response.getItems().get(0).getCountry());
                assertEquals("JPY", response.getItems().get(1).getCurrency());
                assertNull(response.getItems().get(2).getSwiftCode());
                assertEquals("5401", response.getItems().get(3).getLastFour());
                assertEquals(BankAccountStatus.NEW, response.getItems().get(1).getStatus());
                assertEquals(response.getItems().get(2).getCreatedOn(), parsedDate);
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
