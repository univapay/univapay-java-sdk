package com.univapay.sdk.qrcode;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.QrCode;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.QrCodeFakeRR;
import java.io.IOException;
import org.junit.Test;

public class GetQrCodeTest extends GenericTest {

  @Test
  public void shouldReturnQrCodeOfACharge() throws IOException, UnivapayException {

    // Do a query for the issue token

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/425e88b7-b588-4247-80ee-0ea0caff1190/qr",
        jwt,
        200,
        QrCodeFakeRR.getQrCodeFakeResponse);

    UnivapaySDK sdk = createTestInstance(AuthType.JWT);

    QrCode issuerToken =
        sdk.getQrCode(
                new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
                new ChargeId("425e88b7-b588-4247-80ee-0ea0caff1190"))
            .dispatch();

    assertThat(issuerToken, is(notNullValue()));
    assertThat(issuerToken.isReady(), is(true));
    assertThat(issuerToken.getQrCode(), is("http://localhost/test?q=1038505734"));
  }
}
