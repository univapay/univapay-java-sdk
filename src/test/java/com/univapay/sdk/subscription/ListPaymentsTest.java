package com.univapay.sdk.subscription;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.subscription.ScheduledPayment;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class ListPaymentsTest extends GenericTest {

  @Test
  void shouldListPaymentsForASubscription() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/11e82dbf-7e6a-d146-9423-03ae2c18d764/subscriptions/11e89932-7c5f-78c8-b747-27cf2f8ee9b4/payments",
        jwt,
        200,
        ChargesFakeRR.listPaymentsFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    PaginatedList<ScheduledPayment> payments =
        univapay
            .listScheduledPayments(
                new StoreId("11e82dbf-7e6a-d146-9423-03ae2c18d764"),
                new SubscriptionId("11e89932-7c5f-78c8-b747-27cf2f8ee9b4"))
            .build()
            .dispatch();

    ScheduledPayment payment0 = payments.getItems().get(0);

    assertThat(payment0.getId().toString(), Matchers.is("11e89932-7c9b-efce-a32a-73219ce0ccd1"));
    assertThat(payment0.getDueDate(), Matchers.is(LocalDate.parse("2018-09-30")));
    assertThat(payment0.getZoneId(), Matchers.is(ZoneId.of("Asia/Tokyo")));
    assertThat(payment0.getAmount(), Matchers.is(BigInteger.valueOf(6000)));
    assertThat(payment0.getCurrency(), Matchers.is("JPY"));
    assertThat(payment0.getAmountFormatted(), Matchers.is(BigDecimal.valueOf(6000)));
    assertFalse(payment0.getIsPaid());
    assertTrue(payment0.getIsLastPayment());
    assertNotNull(payment0.getCreatedOn());
    assertNotNull(payment0.getUpdatedOn());

    ScheduledPayment payment1 = payments.getItems().get(1);

    assertThat(payment1.getId().toString(), Matchers.is("11e89932-7c98-f4fe-a329-fb3d1441fc35"));
    assertThat(payment1.getDueDate(), Matchers.is(LocalDate.parse("2018-08-31")));
    assertThat(payment1.getZoneId(), Matchers.is(ZoneId.of("Asia/Tokyo")));
    assertThat(payment1.getAmount(), Matchers.is(BigInteger.valueOf(6000)));
    assertThat(payment1.getCurrency(), Matchers.is("JPY"));
    assertThat(payment1.getAmountFormatted(), Matchers.is(BigDecimal.valueOf(6000)));
    assertFalse(payment1.getIsPaid());
    assertFalse(payment1.getIsLastPayment());
    assertNotNull(payment1.getCreatedOn());
    assertNotNull(payment1.getUpdatedOn());

    ScheduledPayment payment2 = payments.getItems().get(2);

    assertThat(payment2.getId().toString(), Matchers.is("11e89932-7c6a-8376-b749-7b3852eeec56"));
    assertThat(payment2.getDueDate(), Matchers.is(LocalDate.parse("2018-08-06")));
    assertThat(payment2.getZoneId(), Matchers.is(ZoneId.of("Asia/Tokyo")));
    assertThat(payment2.getAmount(), Matchers.is(BigInteger.valueOf(3000)));
    assertThat(payment2.getCurrency(), Matchers.is("JPY"));
    assertThat(payment2.getAmountFormatted(), Matchers.is(BigDecimal.valueOf(3000)));
    assertTrue(payment2.getIsPaid());
    assertFalse(payment2.getIsLastPayment());
    assertNotNull(payment2.getCreatedOn());
    assertNotNull(payment2.getUpdatedOn());

    assertFalse(payments.getHasMore());
  }
}
