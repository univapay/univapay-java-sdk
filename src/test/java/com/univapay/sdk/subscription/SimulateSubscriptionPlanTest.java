package com.univapay.sdk.subscription;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.request.subscription.FixedCyclePaymentPlan;
import com.univapay.sdk.models.response.PaymentsPlan;
import com.univapay.sdk.models.response.subscription.SimulatedPayment;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.SubscriptionPeriod;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.Test;

public class SimulateSubscriptionPlanTest extends GenericTest {

  private void assertPayment(
      SimulatedPayment payment, LocalDate dueDate, BigInteger amount, Boolean isLastPayment) {

    assertThat(payment.getDueDate(), is(dueDate));
    assertThat(payment.getZoneId(), is(ZoneId.of("America/Cancun")));
    assertThat(payment.getAmount(), is(amount));
    assertThat(payment.getCurrency(), is("JPY"));
    assertFalse(payment.getIsPaid());
    assertThat(payment.getIsLastPayment(), is(isLastPayment));
  }

  @Test
  public void shouldRequestAPaymentsPlanSimulation() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/subscriptions/simulate_plan",
        jwt,
        200,
        ChargesFakeRR.simulatePlanFakeResponse,
        ChargesFakeRR.simulatePlanFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    PaymentsPlan paymentsPlan =
        univapay
            .simulateSubscriptionPlan(
                new MoneyLike(BigInteger.valueOf(200000), "JPY"),
                PaymentTypeName.CARD,
                SubscriptionPeriod.QUARTERLY)
            .withInitialAmount(BigInteger.valueOf(10000))
            .withInstallmentPlan(new FixedCyclePaymentPlan(3))
            .withPreserveEndOfMonth(true)
            .withStartOn(LocalDate.parse("2020-01-01"))
            .withZoneId(ZoneId.of("America/Cancun"))
            .build()
            .dispatch();

    ArrayList<SimulatedPayment> payments = paymentsPlan.getItems();
    assertPayment(payments.get(0), LocalDate.parse("2018-08-06"), BigInteger.valueOf(10000), false);
    assertPayment(payments.get(1), LocalDate.parse("2020-01-01"), BigInteger.valueOf(95000), false);
    assertPayment(payments.get(2), LocalDate.parse("2020-04-01"), BigInteger.valueOf(95000), true);
  }

  @Test
  public void shouldRequestAPaymentsPlanSimulationWithStoreID() throws Exception {

    StoreId storeId = new StoreId(UUID.randomUUID());

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/stores/" + storeId.toString() + "/subscriptions/simulate_plan",
        jwt,
        200,
        "[]",
        ChargesFakeRR.simulatePlanFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .simulateSubscriptionPlan(
            storeId,
            new MoneyLike(BigInteger.valueOf(200000), "JPY"),
            PaymentTypeName.CARD,
            SubscriptionPeriod.QUARTERLY)
        .withInitialAmount(BigInteger.valueOf(10000))
        .withInstallmentPlan(new FixedCyclePaymentPlan(3))
        .withPreserveEndOfMonth(true)
        .withStartOn(LocalDate.parse("2020-01-01"))
        .withZoneId(ZoneId.of("America/Cancun"))
        .build()
        .dispatch();
  }
}
