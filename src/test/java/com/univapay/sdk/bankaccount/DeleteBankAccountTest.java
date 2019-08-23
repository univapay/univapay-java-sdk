package com.univapay.sdk.bankaccount;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.BankAccountId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import org.junit.Test;

public class DeleteBankAccountTest extends GenericTest {

  @Test
  public void shouldRequestsDeletionOfBankAccount() throws InterruptedException {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "DELETE", "/bank_accounts/eb62d6c1-6b0b-40db-86d5-c631bd755bd6", token, 204, "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);
    univapay
        .deleteBankAccount(new BankAccountId("eb62d6c1-6b0b-40db-86d5-c631bd755bd6"))
        .build()
        .dispatch(new ExpectSuccessCallback<Void>());

    waitCall();

    verify(deleteRequestedFor(urlEqualTo("/bank_accounts/eb62d6c1-6b0b-40db-86d5-c631bd755bd6")));
  }
}
