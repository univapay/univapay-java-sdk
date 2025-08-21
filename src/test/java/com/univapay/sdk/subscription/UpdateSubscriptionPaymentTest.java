package com.univapay.sdk.subscription;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.ScheduledPaymentId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import org.junit.jupiter.api.Test;

class UpdateSubscriptionPaymentTest extends GenericTest {

  @Test
  void shouldUpdateSubscriptionPaymentPaidStatus() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/stores/11e82dbf-7e6a-d146-9423-03ae2c18d764/subscriptions/11e89925-9602-58f2-8f45-fb716a8fc34e/payments/11e89925-967d-1bd2-a831-43c06e016572",
        jwt,
        200,
        "{}",
        ChargesFakeRR.updatePaymentPaidStatusFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .updateScheduledPayment(
            new StoreId("11e82dbf-7e6a-d146-9423-03ae2c18d764"),
            new SubscriptionId("11e89925-9602-58f2-8f45-fb716a8fc34e"),
            new ScheduledPaymentId("11e89925-967d-1bd2-a831-43c06e016572"))
        .withIsPaid(true)
        .build()
        .dispatch();
  }
}
