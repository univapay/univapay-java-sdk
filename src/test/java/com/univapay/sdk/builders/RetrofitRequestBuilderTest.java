package com.univapay.sdk.builders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.RequestUtils;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.UnivapayDebugSettings;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.io.IOException;
import java.math.BigInteger;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;

public class RetrofitRequestBuilderTest extends GenericTest {

  Retrofit retrofit;

  @Before
  public void setup() {
    AbstractSDKSettings settings =
        new UnivapayDebugSettings()
            .withEndpoint("http://localhost:" + PORT)
            .withTimeoutSeconds(10L);
    this.retrofit = RequestUtils.createClient(new AppTokenStrategy(appToken, secret), settings);
  }

  @Test
  public void shouldBuildRequestThatThrowAPIErrorWhen404NotFound() {

    stubFor(
        get(urlEqualTo(
                "/stores/00000000-0000-0000-0000-000000000001/charges/00000000-0000-0000-0000-000000000002"))
            .willReturn(aResponse().withStatus(404)));

    GetChargeRequestBuilder requestBuilder =
        new GetChargeRequestBuilder(
            retrofit,
            new StoreId("00000000-0000-0000-0000-000000000001"),
            new ChargeId("00000000-0000-0000-0000-000000000002"));

    try {
      requestBuilder.build().dispatch();
      fail("No exception thrown");
    } catch (IOException e) {
      e.printStackTrace();
      fail();
    } catch (UnivapayException e) {
      assertEquals(404, e.getHttpStatusCode());
    }
  }

  @Test
  public void shouldPostWithDispatchMethodDirectly() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/425e88b7-b588-4247-80ee-0ea0caff1190",
        token,
        200,
        ChargesFakeRR.getStoreChargeFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

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
  public void shouldPostWithSynchronousDispatchMethodDirectly()
      throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/425e88b7-b588-4247-80ee-0ea0caff1190",
        token,
        200,
        ChargesFakeRR.getStoreChargeFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay
        .getCharge(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new ChargeId("425e88b7-b588-4247-80ee-0ea0caff1190"))
        .dispatch();
  }

  @Test
  public void shouldPostWithRetryDispatchMethodDirectly()
      throws IOException, UnivapayException, InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/425e88b7-b588-4247-80ee-0ea0caff1190",
        token,
        200,
        ChargesFakeRR.getStoreChargeFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay
        .getCharge(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new ChargeId("425e88b7-b588-4247-80ee-0ea0caff1190"))
        .dispatch(1, null);
  }
}
