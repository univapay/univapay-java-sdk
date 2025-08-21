package com.univapay.sdk.bankaccount;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.BankAccountId;
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

class GetBankAccountTest extends GenericTest {

  @Test
  void shouldRequestAndReturnBankAccountInfo() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/bank_accounts/6d50a40d-1785-42a6-9504-b003ce319851",
        jwt,
        200,
        BankAccountsFakeRR.getBankAccountFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2017-06-22T16:00:55.436116+09:00", DateTimeFormatter.ISO_DATE_TIME);

    univapay
        .getBankAccount(new BankAccountId("6d50a40d-1785-42a6-9504-b003ce319851"))
        .build()
        .dispatch(
            new UnivapayCallback<BankAccount>() {
              @Override
              public void getResponse(BankAccount response) {
                assertEquals("6d50a40d-1785-42a6-9504-b003ce319851", response.getId().toString());
                assertEquals("Person 2", response.getHolderName());
                assertEquals("Bank 2", response.getBankName());
                assertEquals("Branch 2", response.getBranchName());
                assertEquals("JP", response.getCountry());
                assertEquals("JPY", response.getCurrency());
                assertEquals("5402", response.getLastFour());
                assertEquals("Minato-ku", response.getBankAddress());
                assertEquals("579b06d365402", response.getAccountNumber());
                assertEquals("BOJPJPJT", response.getSwiftCode());
                assertEquals(BankAccountStatus.NEW, response.getStatus());
                assertEquals(response.getCreatedOn(), parsedDate);
                assertFalse(response.getPrimary());
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                fail();
              }
            });

    waitCall();
  }
}
