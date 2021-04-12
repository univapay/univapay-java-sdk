package com.univapay.sdk.transactiontoken;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import org.junit.Test;

public class DeleteTransactionTokenTest extends GenericTest {

  @Test
  public void shouldRequestDeletionOfTransactionToken() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "DELETE",
        "/stores/fbb48b8e-a443-45f7-9b7d-c278087f8060/tokens/004b391f-1c98-43f8-87de-28b21aaaca00",
        jwt,
        200,
        "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .deleteTransactionToken(
            new StoreId("fbb48b8e-a443-45f7-9b7d-c278087f8060"),
            new TransactionTokenId("004b391f-1c98-43f8-87de-28b21aaaca00"))
        .build()
        .dispatch();

    verify(
        deleteRequestedFor(
            urlEqualTo(
                "/stores/fbb48b8e-a443-45f7-9b7d-c278087f8060/tokens/004b391f-1c98-43f8-87de-28b21aaaca00")));
  }
}
