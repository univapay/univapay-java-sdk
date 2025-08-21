package com.univapay.sdk.bankaccount;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.bankaccounts.JapaneseBankAccount;
import com.univapay.sdk.models.response.bankaccount.BankAccount;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.BankAccountStatus;
import com.univapay.sdk.types.BankAccountType;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.BankAccountsFakeRR;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class CreateBankAccountTest extends GenericTest {

  @Test
  void shouldPostsAndReturnNewBankAccountInfo() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/bank_accounts",
        jwt,
        200,
        BankAccountsFakeRR.postBankAccountFakeResponseWithSwift,
        BankAccountsFakeRR.postBankAccountFakeRequestWithSwift);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2017-06-22T16:00:55.436116+09:00", DateTimeFormatter.ISO_DATE_TIME);

    JapaneseBankAccount bankAccount =
        new JapaneseBankAccount(
                "Person 2", "Bank 2", "Branch 2", "JPY", "579b06d365402", BankAccountType.SAVINGS)
            .withSwiftCode("BOJPJPJT");

    univapay
        .createBankAccount(bankAccount)
        .build()
        .dispatch(
            new UnivapayCallback<BankAccount>() {
              @Override
              public void getResponse(BankAccount response) {

                assertEquals("6692a026-f2f6-499e-9d90-abd957bc89d9", response.getId().toString());
                assertEquals("Person 2", response.getHolderName());
                assertEquals("Bank 2", response.getBankName());
                assertEquals("Branch 2", response.getBranchName());
                assertEquals("JP", response.getCountry());
                assertThat(response.getCountryEnum(), Matchers.is(Country.JAPAN));
                assertEquals("JPY", response.getCurrency());
                assertEquals("BOJPJPJT", response.getSwiftCode());
                assertEquals("5402", response.getLastFour());
                assertEquals(BankAccountStatus.NEW, response.getStatus());
                assertEquals(response.getCreatedOn(), parsedDate);
                assertFalse(response.getPrimary());
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
