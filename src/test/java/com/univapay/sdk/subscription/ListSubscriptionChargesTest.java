package com.univapay.sdk.subscription;

import static org.junit.jupiter.api.Assertions.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ChargeStatus;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class ListSubscriptionChargesTest extends GenericTest {
  @Test
  void listsSubscriptionChargesSuccessfully() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/33fb1370-e930-11e6-8e89-07a500c3935d/subscriptions"
            + "/96fcbd50-e932-11e6-8f46-d79febd4479a/charges",
        jwt,
        200,
        ChargesFakeRR.listSubscriptionCharges);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .listSubscriptionCharges(
            new StoreId("33fb1370-e930-11e6-8e89-07a500c3935d"),
            new SubscriptionId("96fcbd50-e932-11e6-8f46-d79febd4479a"))
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Charge>>() {
              @Override
              public void getResponse(PaginatedList<Charge> response) {
                assertFalse(response.getHasMore());
                Charge charge = response.getItems().get(0);
                assertEquals("96fe72ee-e932-11e6-8f46-0bed6cd5f04d", charge.getId().toString());
                assertEquals(
                    "33fb1370-e930-11e6-8e89-07a500c3935d", charge.getStoreId().toString());
                assertEquals(
                    "5b7a7e3e-e932-11e6-8f46-d7104cb63448",
                    charge.getTransactionTokenId().toString());
                assertEquals(
                    "96fcbd50-e932-11e6-8f46-d79febd4479a", charge.getSubscriptionId().toString());
                assertEquals(charge.getRequestedAmount(), BigInteger.valueOf(1000));
                assertEquals("JPY", charge.getRequestedCurrency());
                assertEquals(charge.getRequestedAmountFormatted(), BigDecimal.valueOf(1000));
                assertEquals(charge.getChargedAmount(), BigInteger.valueOf(1000));
                assertEquals("JPY", charge.getChargedCurrency());
                assertEquals(charge.getChargedAmountFormatted(), BigDecimal.valueOf(1000));
                assertEquals(ChargeStatus.SUCCESSFUL, charge.getStatus());
                assertEquals(ProcessingMode.TEST, charge.getMode());
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {

                notifyCall();
                fail();
              }
            });

    waitCall();
  }
}
