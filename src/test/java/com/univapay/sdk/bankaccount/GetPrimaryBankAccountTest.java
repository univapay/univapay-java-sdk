package com.univapay.sdk.bankaccount;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import com.univapay.sdk.UnivapaySDK;
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

class GetPrimaryBankAccountTest extends GenericTest {

  @Test
  void shouldRequestsAndReturnInfoOfPrimaryBankAccount() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/bank_accounts/primary",
        jwt,
        200,
        BankAccountsFakeRR.getPrimaryBankAccountFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2017-06-22T16:00:55.436116+09:00", DateTimeFormatter.ISO_DATE_TIME);

    univapay
        .getPrimaryBankAccount()
        .build()
        .dispatch(
            new UnivapayCallback<BankAccount>() {
              @Override
              public void getResponse(BankAccount response) {
                assertEquals("fc08d444-702a-4b6a-8abd-067afd3f437e", response.getId().toString());
                assertEquals("Russell Berry", response.getHolderName());
                assertEquals("Ozu Bank", response.getBankName());
                assertNull(response.getBranchName());
                assertEquals("AL", response.getCountry());
                assertNull(response.getBankAddress());
                assertEquals("EUR", response.getCurrency());
                assertEquals("FR21 1184 2544 44IF N3Y1 EMII D95", response.getAccountNumber());
                assertEquals("HIDLFRZA", response.getSwiftCode());
                assertEquals(" D95", response.getLastFour());
                assertEquals(BankAccountStatus.ERRORED, response.getStatus());
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
