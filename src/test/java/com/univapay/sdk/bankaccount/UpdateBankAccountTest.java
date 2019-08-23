package com.univapay.sdk.bankaccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Date;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.BankAccountId;
import com.univapay.sdk.models.response.bankaccount.BankAccount;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.BankAccountStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.BankAccountsFakeRR;
import org.junit.Test;

public class UpdateBankAccountTest extends GenericTest {

  @Test
  public void shouldRequestUpdateOfBankAccountInfoAndReturnsIt()
      throws InterruptedException, ParseException {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "PATCH",
        "/bank_accounts/6d50a40d-1785-42a6-9504-b003ce319851",
        token,
        200,
        BankAccountsFakeRR.updateBankAccountFakeResponse,
        BankAccountsFakeRR.updatePostBankAccountFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final Date parsedCreatedOn =
        dateParser.parseDateTime("2017-06-22T16:00:55.436116+09:00").toDate();

    univapay
        .updateBankAccount(new BankAccountId("6d50a40d-1785-42a6-9504-b003ce319851"))
        .withIsPrimary(true)
        .withBankName("Bank 2 Corrected")
        .build()
        .dispatch(
            new UnivapayCallback<BankAccount>() {
              @Override
              public void getResponse(BankAccount response) {
                assertEquals(response.getId().toString(), "6d50a40d-1785-42a6-9504-b003ce319851");
                assertEquals(response.getHolderName(), "Person 2");
                assertEquals(response.getBankName(), "Bank 2 Corrected");
                assertEquals(response.getBranchName(), "Branch 2");
                assertEquals(response.getCountry(), "JP");
                assertEquals(response.getCurrency(), "JPY");
                assertEquals(response.getLastFour(), "5402");
                assertEquals(response.getBankAddress(), "Minato-ku");
                assertEquals(response.getAccountNumber(), "579b06d365402");
                assertEquals(response.getSwiftCode(), "BOJPJPJT");
                assertEquals(response.getStatus(), BankAccountStatus.NEW);
                assertEquals(response.getCreatedOn(), parsedCreatedOn);
                assertTrue(response.getPrimary());
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
