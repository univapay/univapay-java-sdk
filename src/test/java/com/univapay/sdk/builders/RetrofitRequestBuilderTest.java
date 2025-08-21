package com.univapay.sdk.builders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.builders.charge.ChargesBuilders.GetChargeRequestBuilder;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.auth.AppTokenStrategy;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.settings.AbstractSDKSettings;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.*;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;

class RetrofitRequestBuilderTest extends GenericTest {

  private Retrofit retrofit;
  private final RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

  @BeforeEach
  void setup() {
    AbstractSDKSettings settings =
        new UnivapayDebugSettings().withEndpoint(TEST_ENDPOINT).withTimeoutSeconds(10L);
    this.retrofit = retrofitBuilder.createClient(new AppTokenStrategy(appToken, secret), settings);
  }

  @Test
  void shouldBuildRequestThatThrowAPIErrorWhen404NotFound() {

    stubFor(
        get(urlEqualTo(
                "/stores/00000000-0000-0000-0000-000000000001/charges/00000000-0000-0000-0000-000000000002"))
            .willReturn(aResponse().withStatus(404)));

    GetChargeRequestBuilder requestBuilder =
        new GetChargeRequestBuilder(
            retrofit,
            new StoreId("00000000-0000-0000-0000-000000000001"),
            new ChargeId("00000000-0000-0000-0000-000000000002"));

    Request<Charge> builtRequest = requestBuilder.build();

    UnivapayException exception = assertThrows(UnivapayException.class, builtRequest::dispatch);

    assertEquals(404, exception.getHttpStatusCode());
  }

  @Test
  void shouldPostWithDispatchMethodDirectly() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/425e88b7-b588-4247-80ee-0ea0caff1190",
        jwt,
        200,
        ChargesFakeRR.getStoreChargeFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .getCharge(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new ChargeId("425e88b7-b588-4247-80ee-0ea0caff1190"))
        .dispatch(
            new UnivapayCallback<Charge>() {
              @Override
              public void getResponse(Charge response) {
                assertEquals(response.getRequestedAmount(), BigInteger.valueOf(1000));
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                System.out.println(error);
                notifyCall();
              }
            });
    waitCall();
  }

  @Test
  void shouldPostWithSynchronousDispatchMethodDirectly() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/425e88b7-b588-4247-80ee-0ea0caff1190",
        jwt,
        200,
        ChargesFakeRR.getStoreChargeFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .getCharge(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new ChargeId("425e88b7-b588-4247-80ee-0ea0caff1190"))
        .dispatch();
  }

  @Test
  void shouldPostWithRetryDispatchMethodDirectly() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/425e88b7-b588-4247-80ee-0ea0caff1190",
        jwt,
        200,
        ChargesFakeRR.getStoreChargeFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .getCharge(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new ChargeId("425e88b7-b588-4247-80ee-0ea0caff1190"))
        .dispatch(1, null);
  }
}
