package com.univapay.sdk.utility;

import java.io.IOException;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import org.junit.Test;

public class UtilityTest extends GenericTest {

  @Test
  public void shouldBeat() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse("GET", "/heartbeat", token, 200, "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay.beat().build().dispatch();
  }
}
