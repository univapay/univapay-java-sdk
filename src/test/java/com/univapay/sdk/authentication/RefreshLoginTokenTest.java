package com.univapay.sdk.authentication;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.ScenarioMappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.auth.AuthHeader;
import com.univapay.sdk.models.common.auth.LoginJWTStrategy;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.RefreshLoginJWTCallback;
import com.univapay.sdk.utils.UnivapayClient;
import com.univapay.sdk.utils.UnivapayDebugSettings;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;

public class RefreshLoginTokenTest extends GenericTest {

  private enum TestScenario {
    STARTED(Scenario.STARTED),
    SEND_REFRESH("Send refresh token"),
    USE_REFRESH("Use refresh token");

    private String name;

    TestScenario(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  private MappingBuilder getStub(
      LoginJWTStrategy jwt,
      String route,
      TestScenario state,
      String response,
      TestScenario nextState,
      LoginJWTStrategy refreshToken,
      String refreshHeader) {

    ResponseDefinitionBuilder responseBuilder =
        aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withHeader("Content-Length", String.valueOf(response.length()))
            .withBody(response);

    if (refreshToken != null) {
      responseBuilder.withHeader(refreshHeader, refreshToken.getAuthHeader().getValue());
    }

    ScenarioMappingBuilder stub =
        get(urlEqualTo(route))
            .withHeader("Authorization", containing(jwt.getAuthHeader().getValue()))
            .inScenario("Refresh JWT Scenario")
            .whenScenarioStateIs(state.getName())
            .willReturn(responseBuilder);

    if (nextState != null) {
      stub.willSetStateTo(nextState.getName());
    }

    return stub;
  }

  private MappingBuilder getStub(
      LoginJWTStrategy jwt,
      String route,
      TestScenario state,
      String response,
      TestScenario nextScenario,
      String refreshHeader) {
    return getStub(jwt, route, state, response, nextScenario, null, refreshHeader);
  }

  private MappingBuilder getStub(
      LoginJWTStrategy jwt,
      String route,
      TestScenario state,
      String response,
      String refreshHeader) {
    return getStub(jwt, route, state, response, null, null, refreshHeader);
  }

  @After
  public void teardown() {
    WireMock.resetAllScenarios();
  }

  @Test
  public void shouldRefreshLoginJWT() throws Exception {

    String mockResponse = "{\"items\":[], \"has_more\":false}";
    final String OLD_TOKEN_VALUE =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb2dpbl90b2tlbiIsImV4cCI6MTUzNzg1MTIyMiwiaWF0IjoxNTM3ODQ3NjIyLCJqdGkiOiIxMWU4YzA3Ni05N2Y5LTZjZjUtOGUyMC0zNWFhY2VkZTg3YmMiLCJtZXJjaGFudF9pZCI6IjExZTczZWExLWQ3MDMtZDUwZS05NTZiLTFiYmQ0OWU2ZmMxZiIsIm5hbWUiOiJSb290IGFkbWluIiwiZW1haWwiOiJyb290X2FkbWluQHVuaXZhcGF5LmNvbSIsImxhbmciOiJqYSIsImlwX2FkZHJlc3MiOiIxMjIuMjIwLjE5Mi4yNDIiLCJwbGF0Zm9ybV9pZCI6IjExZTczZWExLWQ3MmMtOGNlMi05OTZkLTRiYjY2NzFlYjY2NyIsImxvZ29fdXJpIjoiaHR0cHM6Ly9zMy1hcC1ub3J0aGVhc3QtMS5hbWF6b25hd3MuY29tL2dvcGF5LWltYWdlcy9zdGFnaW5nLzIwMTgtMDUtMjEveFFqMDY3V3FxMlpFZDNla2R2bHYucG5nIiwicm9sZXMiOlsiYWRtaW4iXX0.WpR-5MnWdOmmIEXOSKAFSYDvrBP_BRVEpMEEG_abL3A";
    final String NEW_TOKEN_VALUE =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb2dpbl90b2tlbiIsImV4cCI6MTUzNzg1ODY5OCwiaWF0IjoxNTM3ODU1MDk4LCJqdGkiOiIxMWU4YzA4OC0wMDI5LTZjZDktOGUyMC1mZDZmNmZmNDJhZDAiLCJtZXJjaGFudF9pZCI6IjExZTg4MzNkLWRhNmEtZDU3Ni1hMGJlLWMzZTczNzc3OTMyNyIsIm5hbWUiOiJQYXltZW50cyBkZXYgdGhyZWUiLCJlbWFpbCI6InBheW1lbnRzLWRldi10aHJlZUB1bml2YXBheS5jb20iLCJsYW5nIjoiamEiLCJpcF9hZGRyZXNzIjoiMTIyLjIyMC4xOTIuMjQyIiwicGxhdGZvcm1faWQiOiIxMWU3M2VhMS1kNzJjLThjZTItOTk2ZC00YmI2NjcxZWI2NjciLCJsb2dvX3VyaSI6Imh0dHBzOi8vczMtYXAtbm9ydGhlYXN0LTEuYW1hem9uYXdzLmNvbS9nb3BheS1pbWFnZXMvc3RhZ2luZy8yMDE4LTA5LTI1L2dKV1BJRTBzbkg0QmpyYkNwM1ZTLnBuZyIsInJvbGVzIjpbImFkbWluIl19.KNLlT-v1xs2HoBq2DsWtxJ2fRlJ_V6eS3meBhF9hqKI";

    LoginJWTStrategy oldJWT = new LoginJWTStrategy(OLD_TOKEN_VALUE);

    LoginJWTStrategy newJWT = new LoginJWTStrategy(NEW_TOKEN_VALUE);

    stubFor(
        getStub(
            oldJWT,
            "/stores",
            TestScenario.STARTED,
            mockResponse,
            TestScenario.SEND_REFRESH,
            UnivapayClient.AUTHORIZATION_HEADER));
    stubFor(
        getStub(
            oldJWT,
            "/stores",
            TestScenario.SEND_REFRESH,
            mockResponse,
            TestScenario.USE_REFRESH,
            newJWT,
            UnivapayClient.AUTHORIZATION_HEADER));
    stubFor(
        getStub(
            newJWT,
            "/stores",
            TestScenario.USE_REFRESH,
            mockResponse,
            UnivapayClient.AUTHORIZATION_HEADER));

    final List<Integer> called = new ArrayList<>();

    UnivapayDebugSettings settings = new UnivapayDebugSettings();
    settings.withEndpoint(TEST_ENDPOINT);
    settings.withRequestsLogging(true);
    settings.withRefreshLoginJWTCallback(
        new RefreshLoginJWTCallback() {
          @Override
          public void onRefreshed(AuthHeader authHeader) {
            assertThat(authHeader.getTokenValue(), is(NEW_TOKEN_VALUE));
            called.add(1);
          }
        });

    UnivapaySDK univapay = UnivapaySDK.create(oldJWT, settings);

    univapay.listStores().build().dispatch();
    assertThat(univapay.getTokenValue(), is(OLD_TOKEN_VALUE));
    univapay.listStores().build().dispatch();
    assertThat(univapay.getTokenValue(), is(NEW_TOKEN_VALUE));
    univapay.listStores().build().dispatch();
    assertThat(univapay.getTokenValue(), is(NEW_TOKEN_VALUE));
    assertThat(univapay.getAuthType(), Matchers.is(AuthType.JWT));
    assertThat(called.size(), is(1));
  }

  @Test
  public void shouldRefreshLoginJWTRemappedAuth() throws Exception {

    String mockResponse = "{\"items\":[], \"has_more\":false}";

    LoginJWTStrategy oldJWT =
        new LoginJWTStrategy(
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb2dpbl90b2tlbiIsImV4cCI6MTUzNzg1MTIyMiwiaWF0IjoxNTM3ODQ3NjIyLCJqdGkiOiIxMWU4YzA3Ni05N2Y5LTZjZjUtOGUyMC0zNWFhY2VkZTg3YmMiLCJtZXJjaGFudF9pZCI6IjExZTczZWExLWQ3MDMtZDUwZS05NTZiLTFiYmQ0OWU2ZmMxZiIsIm5hbWUiOiJSb290IGFkbWluIiwiZW1haWwiOiJyb290X2FkbWluQHVuaXZhcGF5LmNvbSIsImxhbmciOiJqYSIsImlwX2FkZHJlc3MiOiIxMjIuMjIwLjE5Mi4yNDIiLCJwbGF0Zm9ybV9pZCI6IjExZTczZWExLWQ3MmMtOGNlMi05OTZkLTRiYjY2NzFlYjY2NyIsImxvZ29fdXJpIjoiaHR0cHM6Ly9zMy1hcC1ub3J0aGVhc3QtMS5hbWF6b25hd3MuY29tL2dvcGF5LWltYWdlcy9zdGFnaW5nLzIwMTgtMDUtMjEveFFqMDY3V3FxMlpFZDNla2R2bHYucG5nIiwicm9sZXMiOlsiYWRtaW4iXX0.WpR-5MnWdOmmIEXOSKAFSYDvrBP_BRVEpMEEG_abL3A");

    LoginJWTStrategy newJWT =
        new LoginJWTStrategy(
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb2dpbl90b2tlbiIsImV4cCI6MTUzNzg1ODY5OCwiaWF0IjoxNTM3ODU1MDk4LCJqdGkiOiIxMWU4YzA4OC0wMDI5LTZjZDktOGUyMC1mZDZmNmZmNDJhZDAiLCJtZXJjaGFudF9pZCI6IjExZTg4MzNkLWRhNmEtZDU3Ni1hMGJlLWMzZTczNzc3OTMyNyIsIm5hbWUiOiJQYXltZW50cyBkZXYgdGhyZWUiLCJlbWFpbCI6InBheW1lbnRzLWRldi10aHJlZUB1bml2YXBheS5jb20iLCJsYW5nIjoiamEiLCJpcF9hZGRyZXNzIjoiMTIyLjIyMC4xOTIuMjQyIiwicGxhdGZvcm1faWQiOiIxMWU3M2VhMS1kNzJjLThjZTItOTk2ZC00YmI2NjcxZWI2NjciLCJsb2dvX3VyaSI6Imh0dHBzOi8vczMtYXAtbm9ydGhlYXN0LTEuYW1hem9uYXdzLmNvbS9nb3BheS1pbWFnZXMvc3RhZ2luZy8yMDE4LTA5LTI1L2dKV1BJRTBzbkg0QmpyYkNwM1ZTLnBuZyIsInJvbGVzIjpbImFkbWluIl19.KNLlT-v1xs2HoBq2DsWtxJ2fRlJ_V6eS3meBhF9hqKI");

    stubFor(
        getStub(
            oldJWT,
            "/stores",
            TestScenario.STARTED,
            mockResponse,
            TestScenario.SEND_REFRESH,
            UnivapayClient.X_AMZN_REMAPPED_AUTHORIZATION_HEADER));
    stubFor(
        getStub(
            oldJWT,
            "/stores",
            TestScenario.SEND_REFRESH,
            mockResponse,
            TestScenario.USE_REFRESH,
            newJWT,
            UnivapayClient.X_AMZN_REMAPPED_AUTHORIZATION_HEADER));
    stubFor(
        getStub(
            newJWT,
            "/stores",
            TestScenario.USE_REFRESH,
            mockResponse,
            UnivapayClient.X_AMZN_REMAPPED_AUTHORIZATION_HEADER));

    UnivapaySDK univapay = UnivapaySDK.create(oldJWT, testSettings);

    univapay.listStores().build().dispatch();
    univapay.listStores().build().dispatch();
    univapay.listStores().build().dispatch();
  }
}
