package com.univapay.sdk.charge;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;
import org.junit.Test;

public class GetChargeTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnChargeInfo() throws InterruptedException {
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
        .build()
        .dispatch(
            new UnivapayCallback<Charge>() {
              @Override
              public void getResponse(Charge response) {
                assertEquals(response.getRequestedAmount(), BigInteger.valueOf(1000));
                assertEquals(response.getRequestedCurrency(), "JPY");
                assertThat(response.getOnlyDirectCurrency(), is(true));
                assertThat(response.getDescriptor(), is("test descriptor"));
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
  public void shouldReturnChargeComplexMetadata() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/425e88b7-b588-4247-80ee-0ea0caff1190",
        jwt,
        200,
        ChargesFakeRR.getStoreChargeMetadataFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);
    Charge response =
        univapay
            .getCharge(
                new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
                new ChargeId("425e88b7-b588-4247-80ee-0ea0caff1190"))
            .build()
            .dispatch();

    Map<String, String> metadata = response.getMetadata();
    assertThat(metadata.get("array"), is("[\"string\",\"12.3\"]"));
    assertThat(metadata.get("float"), is("10.3"));
    assertThat(metadata.get("number"), is("10"));
    assertThat(metadata.get("string"), is("string value"));
  }
}
