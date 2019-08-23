package com.univapay.sdk.store;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import org.junit.Test;

public class DeleteStoreTest extends GenericTest {

  @Test
  public void shouldRequestDeletionOfStore() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "DELETE", "/stores/fbb48b8e-a443-45f7-9b7d-c278087f8060", token, 200, "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay
        .deleteStore(new StoreId("fbb48b8e-a443-45f7-9b7d-c278087f8060"))
        .build()
        .dispatch(new ExpectSuccessCallback<Void>());

    waitCall();

    verify(deleteRequestedFor(urlEqualTo("/stores/fbb48b8e-a443-45f7-9b7d-c278087f8060")));
  }
}
