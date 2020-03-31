package com.univapay.sdk.bankaccount;

import static org.junit.Assert.*;

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
import java.text.ParseException;
import java.util.Date;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class CreateBankAccountTest extends GenericTest {

  @Test
  public void shouldPostsAndReturnNewBankAccountInfo() throws InterruptedException, ParseException {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/bank_accounts",
        token,
        200,
        BankAccountsFakeRR.postBankAccountFakeResponseWithSwift,
        BankAccountsFakeRR.postBankAccountFakeRequestWithSwift);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final Date parsedDate = dateParser.parseDateTime("2017-06-22T16:00:55.436116+09:00").toDate();

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

                Assert.assertEquals(
                    response.getId().toString(), "6692a026-f2f6-499e-9d90-abd957bc89d9");
                assertEquals(response.getHolderName(), "Person 2");
                assertEquals(response.getBankName(), "Bank 2");
                assertEquals(response.getBranchName(), "Branch 2");
                assertEquals(response.getCountry(), "JP");
                assertThat(response.getCountryEnum(), Matchers.is(Country.JAPAN));
                assertEquals(response.getCurrency(), "JPY");
                assertEquals(response.getSwiftCode(), "BOJPJPJT");
                assertEquals(response.getLastFour(), "5402");
                assertEquals(response.getStatus(), BankAccountStatus.NEW);
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
