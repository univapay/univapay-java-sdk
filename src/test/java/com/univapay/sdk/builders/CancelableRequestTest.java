package com.univapay.sdk.builders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.fail;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.UnivapayCallback;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class CancelableRequestTest extends GenericTest {

  @Test
  void shouldBeAbleToCancelSomeRequest() throws Exception {
    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    stubFor(
        get(urlEqualTo(
                "/stores/00000000-0000-0000-0000-000000000001/charges/00000000-0000-0000-0000-000000000002"))
            .willReturn(aResponse().withFixedDelay(1000).withStatus(404)));

    Request<Charge> request =
        univapay
            .getCharge(
                new StoreId("00000000-0000-0000-0000-000000000001"),
                new ChargeId("00000000-0000-0000-0000-000000000002"))
            .build();

    final CountDownLatch latch = new CountDownLatch(1);

    Cancelable cancelable =
        request.dispatch(
            new UnivapayCallback<Charge>() {
              @Override
              public void getResponse(Charge response) {
                try {
                  fail("Request was cancelled, therefore this should never execute");
                } finally {
                  latch.countDown();
                }
              }

              @Override
              public void getFailure(Throwable error) {
                try {
                  fail("Request was cancelled, therefore this should never execute");

                } finally {
                  latch.countDown();
                }
              }

              @Override
              public void onCancel() {
                latch.countDown();
              }
            });

    cancelable.cancel();

    boolean successful = latch.await(10, TimeUnit.SECONDS);

    if (!successful) {
      fail("Request was cancelled, but the latch wasn't released (callback not invoked)");
    }
  }
}
