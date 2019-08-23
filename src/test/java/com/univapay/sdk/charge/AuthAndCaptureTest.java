package com.univapay.sdk.charge;

import java.math.BigInteger;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import org.junit.Test;

public class AuthAndCaptureTest extends GenericTest {

  private UnivapaySDK bootstrap() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/stores/11e786da-4714-5028-8280-bb9bc7cf54e9/charges/11e7b868-3601-31ce-b52e-bb2249cf0fcc/capture",
        jwt,
        201,
        "",
        ChargesFakeRR.captureChargeFakeRequest);

    return createTestInstance(AuthType.JWT);
  }

  @Test
  public void shouldCaptureAnAuthorizedCharge() throws Exception {

    UnivapaySDK univapay = bootstrap();

    univapay
        .captureAuthorizedCharge(
            new StoreId("11e786da-4714-5028-8280-bb9bc7cf54e9"),
            new ChargeId("11e7b868-3601-31ce-b52e-bb2249cf0fcc"),
            BigInteger.valueOf(1000),
            "JPY")
        .build()
        .dispatch();
  }

  @Test
  public void shouldCaptureTakingMoneyAsArgument() throws Exception {
    UnivapaySDK univapay = bootstrap();

    univapay
        .captureAuthorizedCharge(
            new StoreId("11e786da-4714-5028-8280-bb9bc7cf54e9"),
            new ChargeId("11e7b868-3601-31ce-b52e-bb2249cf0fcc"),
            new MoneyLike(BigInteger.valueOf(1000), "JPY"))
        .build()
        .dispatch();
  }
}
