package com.univapay.sdk.authentication;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import org.junit.Test;

public class DeleteLoginTokenTest extends GenericTest {

  @Test
  public void shouldRequestDeletionOfLoginToken() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT("POST", "/logout", jwt, 200, "");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay.deleteLoginToken().build().dispatch(new ExpectSuccessCallback<Void>());

    waitCall();

    verify(postRequestedFor(urlEqualTo("/logout")));
  }
}
