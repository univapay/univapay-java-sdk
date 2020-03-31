package com.univapay.sdk.store;

import static org.junit.Assert.assertEquals;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.UnivapayCustomerId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.io.IOException;
import java.util.UUID;
import org.junit.Test;

public class CreateCustomerIdTest extends GenericTest {

  @Test
  public void shouldRequestANewCustomerId() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/stores/11e862e3-dcc0-56c2-bacd-c3732ae21835/create_customer_id",
        jwt,
        200,
        StoreFakeRR.createCustomerIdFakeResponse,
        StoreFakeRR.createCustomerIdFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    UnivapayCustomerId univapayCustomerId =
        univapay
            .createCustomerId(
                new StoreId("11e862e3-dcc0-56c2-bacd-c3732ae21835"),
                "sdfiosjdfoisfapsdiofuopszaidfu")
            .build()
            .dispatch();

    assertEquals(
        UUID.fromString("004a7593-8071-439a-8000-788a79579ac4"), univapayCustomerId.toUUID());
  }
}
