package com.univapay.sdk.applicationtoken;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.AppTokenId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import org.junit.Test;

public class DeleteAppTokenTest extends GenericTest {

  @Test
  public void shouldRequestAppTokenDeletion() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "DELETE",
        "/stores/fbb48b8e-a443-45f7-9b7d-c278087f8060/app_tokens/90389195-ce76-43de-935b-7f1d417d23df",
        jwt,
        200,
        "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .deleteAppToken(
            new StoreId("fbb48b8e-a443-45f7-9b7d-c278087f8060"),
            new AppTokenId("90389195-ce76-43de-935b-7f1d417d23df"))
        .build()
        .dispatch();

    verify(
        deleteRequestedFor(
            urlEqualTo(
                "/stores/fbb48b8e-a443-45f7-9b7d-c278087f8060/app_tokens/90389195-ce76-43de-935b-7f1d417d23df")));
  }
}
