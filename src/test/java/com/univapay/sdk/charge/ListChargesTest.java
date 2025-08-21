package com.univapay.sdk.charge;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class ListChargesTest extends GenericTest {

  @Test
  void shouldRequestAndReturnListOfCharges() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges",
        jwt,
        200,
        ChargesFakeRR.listAllStoreChargesFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .listCharges(new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"))
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Charge>>() {
              @Override
              public void getResponse(PaginatedList<Charge> response) {
                assertFalse(response.getHasMore());
                assertEquals(
                    response.getItems().get(0).getRequestedAmount(), BigInteger.valueOf(123));
                assertThat(response.getItems().get(0).getOnlyDirectCurrency(), is(true));
                assertThat(response.getItems().get(0).getDescriptor(), is("test descriptor"));
                assertThat(response.getItems().get(1).getOnlyDirectCurrency(), is(false));
                assertNull(response.getItems().get(1).getDescriptor());
                assertEquals(
                    response.getItems().get(1).getRequestedAmount(), BigInteger.valueOf(1000));
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }
}
