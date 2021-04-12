package com.univapay.sdk.utility;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import java.io.IOException;
import org.junit.Test;

public class UtilityTest extends GenericTest {

  @Test
  public void shouldBeat() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT("GET", "/heartbeat", jwt, 200, "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay.beat().build().dispatch();
  }
}
