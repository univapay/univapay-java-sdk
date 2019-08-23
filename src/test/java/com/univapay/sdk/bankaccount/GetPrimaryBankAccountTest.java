package com.univapay.sdk.bankaccount;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.response.bankaccount.BankAccount;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.BankAccountStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.BankAccountsFakeRR;
import org.junit.Assert;
import org.junit.Test;

public class GetPrimaryBankAccountTest extends GenericTest {

  @Test
  public void shouldRequestsAndReturnInfoOfPrimaryBankAccount()
      throws InterruptedException, ParseException {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/bank_accounts/primary",
        token,
        200,
        BankAccountsFakeRR.getPrimaryBankAccountFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final Date parsedDate = dateParser.parseDateTime("2017-06-22T16:00:55.436116+09:00").toDate();

    univapay
        .getPrimaryBankAccount()
        .build()
        .dispatch(
            new UnivapayCallback<BankAccount>() {
              @Override
              public void getResponse(BankAccount response) {
                Assert.assertEquals(
                    response.getId().toString(), "fc08d444-702a-4b6a-8abd-067afd3f437e");
                assertEquals(response.getHolderName(), "Russell Berry");
                assertEquals(response.getBankName(), "Ozu Bank");
                assertNull(response.getBranchName());
                assertEquals(response.getCountry(), "AL");
                assertNull(response.getBankAddress());
                assertEquals(response.getCurrency(), "EUR");
                assertEquals(response.getAccountNumber(), "FR21 1184 2544 44IF N3Y1 EMII D95");
                assertEquals(response.getSwiftCode(), "HIDLFRZA");
                assertEquals(response.getLastFour(), " D95");
                assertEquals(response.getStatus(), BankAccountStatus.ERRORED);
                assertEquals(response.getCreatedOn(), parsedDate);
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
