package com.univapay.sdk.charge;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.builders.ResourceMonitor;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ChargeStatus;
import com.univapay.sdk.utils.ExponentialBackoff;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.junit.Test;

public class ChargeCompletionMonitorTest extends GenericTest {

  private volatile AssertionError assertionError;

  @Test
  public void shouldRequestAndReturnChargeInfo()
      throws InterruptedException, UnivapayException, TimeoutException, IOException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/425e88b7-b588-4247-80ee-0ea0caff1190?polling=true",
        token,
        200,
        ChargesFakeRR.getStoreChargeFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    ResourceMonitor<Charge> monitor =
        univapay.chargeCompletionMonitor(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new ChargeId("425e88b7-b588-4247-80ee-0ea0caff1190"));

    Charge charge = monitor.await(60_000, new ExponentialBackoff(0, 5000, 1.5, 0.5));
    assertEquals(ChargeStatus.SUCCESSFUL, charge.getStatus());
  }

  @Test
  public void shouldRequestAndReturnChargeInfoAsync()
      throws InterruptedException, UnivapayException, TimeoutException, IOException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/425e88b7-b588-4247-80ee-0ea0caff1190?polling=true",
        token,
        200,
        ChargesFakeRR.getStoreChargeFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    ResourceMonitor<Charge> monitor =
        univapay.chargeCompletionMonitor(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new ChargeId("425e88b7-b588-4247-80ee-0ea0caff1190"));

    monitor.await(
        new UnivapayCallback<Charge>() {
          @Override
          public void getResponse(Charge charge) {
            try {
              assertEquals(ChargeStatus.SUCCESSFUL, charge.getStatus());
            } catch (AssertionError err) {
              assertionError = err;
            } finally {
              notifyCall();
            }
          }

          @Override
          public void getFailure(Throwable error) {
            notifyCall();
          }
        },
        60_000,
        new ExponentialBackoff(0, 5_000, 1.5, 0.5));

    waitCall();

    if (assertionError != null) {
      throw assertionError;
    }
  }
}
