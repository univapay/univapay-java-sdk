package com.univapay.sdk.utility;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import org.junit.jupiter.api.Test;

class UtilityTest extends GenericTest {

  @Test
  void shouldBeat() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT("GET", "/heartbeat", jwt, 200, "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay.beat().build().dispatch();
  }
}
