package com.univapay.sdk.charge;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static junit.framework.TestCase.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.CallMethod;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.threeDs.ChargeThreeDsMode;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.IssuerToken;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ChargeStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.mockcontent.JsonLoader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import org.junit.Test;

public class ChargeThreeDsTest extends GenericTest {

  // Three DS related testing
  // When creating a charge, if the payment method is credit card
  // if the status becomes awaiting, means that is awaiting
  // the three ds check to complete to continue the processing
  // If that is the case, fetch the issuer token and complete the processing

  // Should be able to process the three ds
  // Should be able to provide the mpi
  // Should be able to skip

  @Test
  public void shouldBeAbleToEnableProcessThreeDs()
      throws IOException, UnivapayException, InterruptedException, TimeoutException {

    // With a Transaction Token that the three ds is disabled
    // When creating a charge, need to prepare an redirect page for the three ds result (not charge)

    int currentState = 1;

    String scenario = "Charge ThreeDS Test";

    String createChargeRequest =
        JsonLoader.loadJson("/requests/charge/charge-create-with-three-ds-normal.json");
    String createChargeResponse =
        JsonLoader.loadJson("/responses/charge/charge-create-with-three-ds-pending.json");
    stubFor(
        post(urlPathEqualTo("/charges"))
            .withRequestBody(equalToJson(createChargeRequest))
            .inScenario(scenario)
            .whenScenarioStateIs(STARTED)
            .willReturn(okJson(createChargeResponse))
            .willSetStateTo("00" + currentState));

    // Stay pending for a bit

    for (int i = 0; i < 2; i++) {

      stubFor(
          get(urlPathEqualTo(
                  "/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/charges/11efdfaf-1e1d-899c-9c99-8718c995d6c0"))
              .withQueryParam("polling", equalTo("true"))
              .inScenario(scenario)
              .whenScenarioStateIs("00" + currentState)
              .willReturn(okJson(createChargeResponse))
              .willSetStateTo("00" + (++currentState)));
    }

    // Then becomes Awaiting

    String awaitingChargeResponse =
        JsonLoader.loadJson("/responses/charge/charge-create-with-three-ds-awaiting.json");
    stubFor(
        get(urlPathEqualTo(
                "/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/charges/11efdfaf-1e1d-899c-9c99-8718c995d6c0"))
            .withQueryParam("polling", equalTo("true"))
            .inScenario(scenario)
            .whenScenarioStateIs("00" + currentState)
            .willReturn(okJson(awaitingChargeResponse))
            .willSetStateTo("00" + (++currentState)));

    String threeDsIssuerChargeResponse =
        JsonLoader.loadJson("/responses/issuerToken/get-issuer-token-three-ds-charge.json");
    stubFor(
        get(urlPathEqualTo(
                "/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/charges/11efdfaf-1e1d-899c-9c99-8718c995d6c0"
                    + "/three_ds/issuer_token"))
            .inScenario(scenario)
            .whenScenarioStateIs("00" + currentState)
            .willReturn(okJson(threeDsIssuerChargeResponse)));

    try (UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN)) {

      Charge createdCharge =
          univapay
              .createCharge(
                  new TransactionTokenId("11efdfad-91d5-3954-91f5-73fd10072929"),
                  new MoneyLike(BigInteger.valueOf(100L), "JPY"))
              .withThreeDs(
                  ChargeThreeDsMode.NORMAL,
                  "https://my-website/three-3ds-example-for-charge-callback")
              .dispatch();

      assertEquals(ChargeStatus.PENDING, createdCharge.getStatus());

      Charge charge =
          univapay
              .chargeCompletionMonitor(createdCharge.getStoreId(), createdCharge.getId())
              .await();

      assertEquals(ChargeStatus.AWAITING, charge.getStatus());

      // If the Charge is Awaiting, (and the payment method is credit card)
      // Must get the issue token and flow to the "Three DS Processing" flow

      IssuerToken issuerToken =
          univapay.getIssuerTokenThreeDsCharge(charge.getStoreId(), charge.getId()).dispatch();

      assertEquals(
          "https://api.gyro-n.money/stores/11e75262-8c6a-60c4-9d8f-efa3568a7fa6/three_ds/issuer_token/test/response",
          issuerToken.getIssuerToken());
      assertEquals(CallMethod.HTTP_POST, issuerToken.getCallMethod());

      Map<String, String> payload = new HashMap<>();
      payload.put("resource_id", "11efdfaf-1e1d-899c-9c99-8718c995d6c0");
      payload.put("resource_type", "charge");

      assertEquals(payload, issuerToken.getPayload());

      // At this point, use the issuer token, since is a POST request
      // a landing page need to be created, a simple redirect is not enough
      // * Note that the redirect url for the three redirect after the
      //   three ds completion, not upon the charge success
      //   need to verify the charge success

    }
  }

  @Test
  public void shouldBeAbleToProvideExternalThreeDsMPI() throws IOException, UnivapayException {
    // There isn't much to this, API try will accept the MPI
    // As there is no change in the flow of a regular processing
    // This test will be just the form validation with wiremock

    String createChargeRequest =
        JsonLoader.loadJson("/requests/charge/charge-create-with-three-ds-provided.json");
    String createChargeResponse =
        JsonLoader.loadJson("/responses/charge/charge-create-with-three-ds-provided.json");
    stubFor(
        post(urlPathEqualTo("/charges"))
            .withRequestBody(equalToJson(createChargeRequest))
            .willReturn(okJson(createChargeResponse)));

    try (UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN)) {

      Charge createdCharge =
          univapay
              .createCharge(
                  new TransactionTokenId("11efdfad-91d5-3954-91f5-73fd10072929"),
                  new MoneyLike(BigInteger.valueOf(100L), "JPY"))
              .withProvidedThreeDs(
                  "1234567890123456789012345678",
                  "12",
                  "11efbb62-7838-0492-acd7-aaabfef2ee8d",
                  "11efbb62-7838-0492-acd7-aaabfef2ee8a",
                  "2.2.0",
                  "A")
              .dispatch();

      // The flow should be the same
      assertEquals(ChargeStatus.PENDING, createdCharge.getStatus());
      assertEquals(ChargeThreeDsMode.PROVIDED, createdCharge.getThreeDS().getMode());
    }
  }
}
