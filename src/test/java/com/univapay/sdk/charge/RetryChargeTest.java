package com.univapay.sdk.charge;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.ScenarioMappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.auth.AppJWTStrategy;
import com.univapay.sdk.models.errors.DetailedError;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import com.univapay.sdk.utils.mockcontent.ErrorsFakeRR;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RetryChargeTest extends GenericTest {

  @Getter
  private enum TestScenario {
    SEND_UNSUPPORTED_DESCRIPTOR_ERROR(Scenario.STARTED),
    SEND_WITHOUT_DESCRIPTOR("Retry the request with an empty descriptor");

    private final String name;

    TestScenario(String name) {
      this.name = name;
    }
  }

  private MappingBuilder getStub(
      AppJWTStrategy jwt,
      String route,
      TestScenario state,
      String request,
      String response,
      TestScenario nextState,
      int status) {

    ResponseDefinitionBuilder responseBuilder =
        aResponse()
            .withStatus(status)
            .withHeader("Content-Type", "application/json")
            .withHeader("Content-Length", String.valueOf(response.length()))
            .withBody(response);

    ScenarioMappingBuilder stub =
        post(urlEqualTo(route))
            .withRequestBody(equalToJson(request))
            .withHeader("Authorization", containing(jwt.getAuthHeader().getValue()))
            .inScenario("Retry ignoring descriptor scenario")
            .whenScenarioStateIs(state.getName())
            .willReturn(responseBuilder);

    if (nextState != null) {
      stub.willSetStateTo(nextState.getName());
    }

    return stub;
  }

  private MappingBuilder getStub(
      AppJWTStrategy jwt,
      String route,
      TestScenario state,
      String request,
      String response,
      int status) {
    return getStub(jwt, route, state, request, response, null, status);
  }

  @BeforeEach
  void setup() {
    WireMock.resetAllScenarios();
    WireMock.resetAllRequests();
  }

  @AfterEach
  void teardown() {
    WireMock.resetAllScenarios();
    WireMock.resetAllRequests();
  }

  @Test
  void shouldRetryChargeIgnoringDescriptorIfNotSupported() throws Exception {

    BigInteger amount = BigInteger.valueOf(1000);
    TransactionTokenId transactionTokenId =
        new TransactionTokenId("11e8d66c-0b26-aace-ae22-7b4a85c3ff85");

    stubFor(
        getStub(
            appJWTStrategyWithSecret,
            "/charges",
            TestScenario.SEND_UNSUPPORTED_DESCRIPTOR_ERROR,
            ChargesFakeRR.createFullChargeWithComplexMetadataFakeRequest(null, amount, true),
            ErrorsFakeRR.descriptorNotSupportedError,
            TestScenario.SEND_WITHOUT_DESCRIPTOR,
            400));

    stubFor(
        getStub(
            appJWTStrategyWithSecret,
            "/charges",
            TestScenario.SEND_WITHOUT_DESCRIPTOR,
            ChargesFakeRR.createFullChargeWithComplexMetadataFakeRequest(null, amount, false),
            ChargesFakeRR.createFullChargeWithComplexMetadataFakeResponse(null, false),
            201));

    UnivapaySDK univapay = UnivapaySDK.create(appJWTStrategyWithSecret, testSettings);

    final String descriptor = "test descriptor";

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("name", "test-name");
    requestMetadata.put("value", "1234.7981723987");

    univapay
        .createCharge(transactionTokenId, amount, "JPY")
        .withOnlyDirectCurrency(true)
        .withDescriptor(descriptor, true)
        .withMetadata(requestMetadata)
        .build()
        .dispatch();

    verify(2, postRequestedFor(urlEqualTo("/charges")));
  }

  @Test
  void shouldNotRetryChargeIfDescriptorNotSupportedByDefault() throws Exception {
    BigInteger amount = BigInteger.valueOf(1000);
    TransactionTokenId transactionTokenId =
        new TransactionTokenId("11e8d66c-0b26-aace-ae22-7b4a85c3ff85");

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/charges",
        appJWTStrategyWithSecret.getToken(),
        400,
        ErrorsFakeRR.descriptorNotSupportedError,
        ChargesFakeRR.createFullChargeWithComplexMetadataFakeRequest(null, amount, true));

    AppJWTStrategy auth = new AppJWTStrategy(appJWTStrategyWithSecret.getToken());

    UnivapaySDK univapay = UnivapaySDK.create(auth, testSettings);

    final String descriptor = "test descriptor";

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("name", "test-name");
    requestMetadata.put("value", "1234.7981723987");

    try {
      univapay
          .createCharge(transactionTokenId, amount, "JPY")
          .withOnlyDirectCurrency(true)
          .withDescriptor(descriptor)
          .withMetadata(requestMetadata)
          .build()
          .dispatch();
    } catch (UnivapayException gpe) {
      assertThat(gpe.getHttpStatusCode(), is(400));
      assertThat(gpe.getBody().getErrors().size(), is(1));
      assertThat(
          gpe.getBody().getErrors().get(0),
          is(new DetailedError("descriptor", "NOT_SUPPORTED_BY_PROCESSOR")));
    }

    verify(1, postRequestedFor(urlEqualTo("/charges")));
  }
}
