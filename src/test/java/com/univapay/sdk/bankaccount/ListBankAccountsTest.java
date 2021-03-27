package com.univapay.sdk.bankaccount;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.bankaccount.BankAccount;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.BankAccountStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.BankAccountsFakeRR;
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Test;

public class ListBankAccountsTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnListOfBankAccounts()
      throws InterruptedException, ParseException {

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
                    response.getItems().get(0).getId().toString(),
                    "6692a026-f2f6-499e-9d90-abd957bc89d9");
                assertEquals(response.getItems().get(1).getHolderName(), "Person 2");
                assertEquals(response.getItems().get(2).getBankName(), "Bank 3");
                assertEquals(response.getItems().get(3).getBranchName(), "Branch 1");
                assertEquals(response.getItems().get(0).getCountry(), "JP");
                assertEquals(response.getItems().get(1).getCurrency(), "JPY");
                assertNull(response.getItems().get(2).getSwiftCode());
                assertEquals(response.getItems().get(3).getLastFour(), "5401");
                assertEquals(response.getItems().get(1).getStatus(), BankAccountStatus.NEW);
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
