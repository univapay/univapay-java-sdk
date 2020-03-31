package com.univapay.sdk.subscription;

import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.ScheduledPaymentId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.response.subscription.ScheduledPayment;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.hamcrest.Matchers;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.threeten.bp.ZoneId;

public class GetSubscriptionPaymentTest extends GenericTest {

  @Test
  public void shouldGetAPaymentForASubscription() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/11e82dbf-7e6a-d146-9423-03ae2c18d764/subscriptions/11e89925-9602-58f2-8f45-fb716a8fc34e/payments/11e89925-967d-1bd2-a831-43c06e016572",
        token,
        200,
        ChargesFakeRR.getPaymentFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    ScheduledPayment payment =
        univapay
            .getScheduledPayment(
                new StoreId("11e82dbf-7e6a-d146-9423-03ae2c18d764"),
                new SubscriptionId("11e89925-9602-58f2-8f45-fb716a8fc34e"),
                new ScheduledPaymentId("11e89925-967d-1bd2-a831-43c06e016572"))
            .build()
            .dispatch();

    assertThat(payment.getId().toString(), Matchers.is("11e89925-967d-1bd2-a831-43c06e016572"));
    assertThat(payment.getDueDate(), Matchers.is(LocalDate.parse("2019-01-30")));
    assertThat(payment.getZoneId(), Matchers.is(ZoneId.of("Asia/Tokyo")));
    assertThat(payment.getAmount(), Matchers.is(BigInteger.valueOf(21963)));
    assertThat(payment.getCurrency(), Matchers.is("JPY"));
    assertThat(payment.getAmountFormatted(), Matchers.is(BigDecimal.valueOf(21963)));
    assertFalse(payment.getIsPaid());
    assertFalse(payment.getIsLastPayment());
    assertNotNull(payment.getCreatedOn());
    assertNotNull(payment.getUpdatedOn());
  }
}
