package com.univapay.sdk.subscription;

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
import com.univapay.sdk.types.SubscriptionPeriod;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import com.univapay.sdk.utils.mockcontent.ErrorsFakeRR;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RetrySubscriptionTest extends GenericTest {

  private final LocalDate startOn = LocalDate.parse("2020-08-31");
  private final BigInteger initialAmount = BigInteger.valueOf(1000);

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

  @Before
  public void setup() {
    WireMock.resetAllScenarios();
    WireMock.resetAllRequests();
  }

  @After
  public void teardown() {
    WireMock.resetAllScenarios();
    WireMock.resetAllRequests();
  }

  @Test
  public void shouldRetrySubscriptionIgnoringDescriptorIfNotSupported() throws Exception {

    BigInteger amount = BigInteger.valueOf(10000);
    TransactionTokenId transactionTokenId =
        new TransactionTokenId("11e897ac-8cb5-36c2-aea6-0fe2b400c0b1");

    stubFor(
        getStub(
            appJWTStrategyWithSecret,
            "/subscriptions",
            TestScenario.SEND_UNSUPPORTED_DESCRIPTOR_ERROR,
            ChargesFakeRR.createSubscriptionMetadataFakeRequest(true),
            ErrorsFakeRR.descriptorNotSupportedError,
            TestScenario.SEND_WITHOUT_DESCRIPTOR,
            400));

    stubFor(
        getStub(
            appJWTStrategyWithSecret,
            "/subscriptions",
            TestScenario.SEND_WITHOUT_DESCRIPTOR,
            ChargesFakeRR.createSubscriptionMetadataFakeRequest(false),
            ChargesFakeRR.createSubscriptionMetadataFakeResponse(false),
            201));

    UnivapaySDK univapay = UnivapaySDK.create(appJWTStrategyWithSecret, testSettings);

    final String descriptor = "test descriptor";

    Map<String, String> requestMetadata = new HashMap<>();
    requestMetadata.put("name", "test-name");
    requestMetadata.put("value", "1234.7981723987");

    univapay
        .createSubscription(transactionTokenId, amount, "JPY", SubscriptionPeriod.MONTHLY)
        .withInitialAmount(initialAmount)
        .withStartOn(startOn)
        .withDescriptor(descriptor, true)
        .withPreserveEndOfMoth(true)
        .withZoneId(ZoneId.of("America/Cancun"))
        .withMetadata(requestMetadata)
        .build()
        .dispatch();

    verify(2, postRequestedFor(urlEqualTo("/subscriptions")));
  }

  @Test
  public void shouldNotRetryChargeIfDescriptorNotSupportedByDefault() throws Exception {
    BigInteger amount = BigInteger.valueOf(10000);
    TransactionTokenId transactionTokenId =
        new TransactionTokenId("11e897ac-8cb5-36c2-aea6-0fe2b400c0b1");

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/subscriptions",
        appJWTStrategyWithSecret.getToken(),
        400,
        ErrorsFakeRR.descriptorNotSupportedError,
        ChargesFakeRR.createSubscriptionMetadataFakeRequest(true));

    AppJWTStrategy auth = new AppJWTStrategy(appJWTStrategyWithSecret.getToken());

    UnivapaySDK univapay = UnivapaySDK.create(auth, testSettings);

    final String descriptor = "test descriptor";

    Map<String, String> requestMetadata = new HashMap<>();
    requestMetadata.put("name", "test-name");
    requestMetadata.put("value", "1234.7981723987");

    try {
      univapay
          .createSubscription(transactionTokenId, amount, "JPY", SubscriptionPeriod.MONTHLY)
          .withInitialAmount(initialAmount)
          .withStartOn(startOn)
          .withDescriptor(descriptor)
          .withPreserveEndOfMoth(true)
          .withZoneId(ZoneId.of("America/Cancun"))
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

    verify(1, postRequestedFor(urlEqualTo("/subscriptions")));
  }
}
