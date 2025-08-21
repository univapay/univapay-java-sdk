package com.univapay.sdk.bankaccount;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.BankAccountId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import org.junit.jupiter.api.Test;

class DeleteBankAccountTest extends GenericTest {

  @Test
  void shouldRequestsDeletionOfBankAccount() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "DELETE", "/bank_accounts/eb62d6c1-6b0b-40db-86d5-c631bd755bd6", jwt, 204, "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);
    univapay
        .deleteBankAccount(new BankAccountId("eb62d6c1-6b0b-40db-86d5-c631bd755bd6"))
        .build()
        .dispatch(new ExpectSuccessCallback<Void>());

    waitCall();

    verify(deleteRequestedFor(urlEqualTo("/bank_accounts/eb62d6c1-6b0b-40db-86d5-c631bd755bd6")));
  }
}
