package com.univapay.sdk.bankaccount;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.BankAccountId;
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
import org.junit.Assert;
import org.junit.Test;

public class GetBankAccountTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnBankAccountInfo() throws InterruptedException, ParseException {

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
                Assert.assertEquals(
                    response.getId().toString(), "6d50a40d-1785-42a6-9504-b003ce319851");
                assertEquals(response.getHolderName(), "Person 2");
                assertEquals(response.getBankName(), "Bank 2");
                assertEquals(response.getBranchName(), "Branch 2");
                assertEquals(response.getCountry(), "JP");
                assertEquals(response.getCurrency(), "JPY");
                assertEquals(response.getLastFour(), "5402");
                assertEquals(response.getBankAddress(), "Minato-ku");
                assertEquals(response.getAccountNumber(), "579b06d365402");
                assertEquals(response.getSwiftCode(), "BOJPJPJT");
                assertEquals(response.getStatus(), BankAccountStatus.NEW);
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
