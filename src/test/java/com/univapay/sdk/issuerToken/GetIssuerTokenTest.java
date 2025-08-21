package com.univapay.sdk.issuerToken;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.CallMethod;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.IssuerToken;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.IssuerTokensFakeRR;
import org.junit.jupiter.api.Test;

class GetIssuerTokenTest extends GenericTest {

  @Test
  void shouldReturnTheIssuerTokenOfCharge() throws Exception {

    // Do a query for the issue token

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/425e88b7-b588-4247-80ee-0ea0caff1190/issuer_token",
        jwt,
        200,
        IssuerTokensFakeRR.getIssuerTokenFakeResponse);

    UnivapaySDK sdk = createTestInstance(AuthType.JWT);

    IssuerToken issuerToken =
        sdk.getIssuerToken(
                new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
                new ChargeId("425e88b7-b588-4247-80ee-0ea0caff1190"))
            .dispatch();

    assertNotNull(issuerToken);
    assertThat(issuerToken.getCallMethod(), is(CallMethod.HTTP_GET));
    assertThat(issuerToken.getIssuerToken(), is("TOKEN"));
  }
}
