package com.univapay.sdk.jwt;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.auth.AppJWTStrategy;
import com.univapay.sdk.settings.AbstractSDKSettings;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayDebugSettings;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class AuthWithJWTTest extends GenericTest {

  @Test
  void canRequestUsingJWTCredentials() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/charges",
        jwt,
        201,
        ChargesFakeRR.createStoreChargeFakeResponse,
        ChargesFakeRR.createStoreChargeFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("cod", "15984632");
    requestMetadata.put("prod", "electronics");

    univapay
        .createCharge(
            new TransactionTokenId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            BigInteger.valueOf(1000),
            "JPY")
        .withMetadata(requestMetadata)
        .build()
        .dispatch();
  }

  @Test
  void canRequestUsingJWTCredentialsWithSecret() throws Exception {
    AppJWTStrategy jwtCredentials = new AppJWTStrategy(appJWT, appJWTSecret);

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/charges",
        jwtCredentials.getAuthHeader().getValue().replace(AuthType.JWT.getPrefix() + " ", ""),
        201,
        ChargesFakeRR.createStoreChargeFakeResponse,
        ChargesFakeRR.createStoreChargeFakeRequest);

    AbstractSDKSettings settings =
        new UnivapayDebugSettings().withEndpoint(TEST_ENDPOINT).withRequestsLogging(true);
    UnivapaySDK univapay = UnivapaySDK.create(jwtCredentials, settings);

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("cod", "15984632");
    requestMetadata.put("prod", "electronics");

    univapay
        .createCharge(
            new TransactionTokenId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            BigInteger.valueOf(1000),
            "JPY")
        .withMetadata(requestMetadata)
        .build()
        .dispatch();
  }
}
