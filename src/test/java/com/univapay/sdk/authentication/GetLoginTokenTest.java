package com.univapay.sdk.authentication;

import static org.junit.Assert.assertEquals;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.response.authentication.LoginTokenInfo;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayDebugSettings;
import com.univapay.sdk.utils.mockcontent.AuthenticationFakeRR;
import org.junit.Assert;
import org.junit.Test;

public class GetLoginTokenTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnLoginTokenInfo() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/authenticate",
        null,
        200,
        AuthenticationFakeRR.getLogintokenFakeResponse,
        AuthenticationFakeRR.getLoginTokenFakeRequest);

    UnivapaySDK univapay =
        UnivapaySDK.create(
            new UnivapayDebugSettings()
                .withEndpoint("http://localhost:" + PORT)
                .withRequestsLogging(true));

    LoginTokenInfo response =
        univapay.getLoginToken("mruser@internet.com", "mruserspassword").build().dispatch();

    Assert.assertEquals(
        response.getMerchantId().toString(), "65774989-5cbb-4790-9cb9-17f127059d2d");
    assertEquals(
        response.getJWTAuthStrategy().getToken(),
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb2dpbl90b2tlbiIsImV4cCI6MTUyMDQyMjU3MywiaWF0IjoxNTIwNDE4OTczLCJqdGkiOiIxMWU4MjFmMy01YmIxLTBmODYtOTcxZi0wMWRkOGYwOTFhN2QiLCJtZXJjaGFudF9pZCI6IjExZTgyMWJlLTY4ZDQtNzMwOC1iYjYwLWI3MmRhYzA4MTA5OCIsIm5hbWUiOiJSb290IEFkbWluIiwiZW1haWwiOiJyb290X2FkbWluQHVuaXZhcGF5LmNvbSIsImxhbmciOiJqYSIsImlwX2FkZHJlc3MiOiIwOjA6MDowOjA6MDowOjEiLCJhdWQiOiIxMWU4MjFiZS02NWY4LWMxZmMtYjdmNi1iYjQ4NzFiYmExN2EiLCJyb2xlcyI6WyJhZG1pbiJdfQ.iaypSYq2kM1iCsAhZiRIe8WiEiAkPWqRNX6KhcrT8fY");
    assertEquals(response.getToken(), "quqhrNtsUY0ypKPbgfeO");
  }
}
