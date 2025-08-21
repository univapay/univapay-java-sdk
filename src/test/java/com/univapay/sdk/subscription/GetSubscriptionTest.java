package com.univapay.sdk.subscription;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.response.subscription.FullSubscription;
import com.univapay.sdk.models.response.subscription.ScheduleSettings;
import com.univapay.sdk.models.response.subscription.ScheduledPayment;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.SubscriptionPeriod;
import com.univapay.sdk.types.SubscriptionStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class GetSubscriptionTest extends GenericTest {

  @Test
  void shouldRequestAndReturnSubscriptionInfo() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/11e82dbf-7e6a-d146-9423-03ae2c18d764/subscriptions/11e897ac-8cc6-5178-aea8-b7d42ee9639f",
        jwt,
        200,
        ChargesFakeRR.getSubscriptionFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    FullSubscription response =
        univapay
            .getSubscription(
                new StoreId("11e82dbf-7e6a-d146-9423-03ae2c18d764"),
                new SubscriptionId("11e897ac-8cc6-5178-aea8-b7d42ee9639f"))
            .build()
            .dispatch();

    assertThat(response.getId().toString(), Matchers.is("11e897ac-8cc6-5178-aea8-b7d42ee9639f"));
    assertThat(
        response.getStoreId().toString(), Matchers.is("11e82dbf-7e6a-d146-9423-03ae2c18d764"));
    assertThat(
        response.getTransactionTokenId().toString(),
        Matchers.is("11e897ac-8cb5-36c2-aea6-0fe2b400c0b1"));
    assertThat(response.getAmount(), Matchers.is(BigInteger.valueOf(10000)));
    assertThat(response.getAmountLeft(), is(BigInteger.valueOf(0)));
    assertThat(response.getAmountLeftFormatted(), is(BigDecimal.valueOf(0)));
    assertThat(response.getCurrency(), Matchers.is("JPY"));
    assertThat(response.getAmountFormatted(), Matchers.is(BigDecimal.valueOf(10000)));
    assertThat(response.getPeriod(), Matchers.is(SubscriptionPeriod.MONTHLY));
    assertThat(response.getInitialAmount(), Matchers.is(BigInteger.valueOf(1000)));
    assertThat(response.getInitialAmountFormatted(), Matchers.is(BigDecimal.valueOf(1000)));
    assertThat(response.getOnlyDirectCurrency(), Matchers.is(true));
    assertThat(response.getStatus(), Matchers.is(SubscriptionStatus.UNVERIFIED));
    assertThat(response.getMode(), Matchers.is(ProcessingMode.TEST));
    assertNotNull(response.getCreatedOn());
    assertThat(response.getMetadata().get("some_key"), Matchers.is("some value"));

    ScheduleSettings scheduleSettings = response.getScheduleSettings();

    assertThat(scheduleSettings.getStartOn(), Matchers.is(LocalDate.parse("2018-08-31")));
    assertThat(scheduleSettings.getZoneId(), Matchers.is(ZoneId.of("America/Cancun")));

    ScheduledPayment nextPayment = response.getNextPayment();

    assertThat(nextPayment.getId().toString(), Matchers.is("11e897ac-8ccc-b176-aea9-37b0f1f6fa10"));
    assertThat(nextPayment.getDueDate(), Matchers.is(LocalDate.parse("2018-08-04")));
    assertThat(nextPayment.getZoneId(), Matchers.is(ZoneId.of("America/Cancun")));
    assertThat(nextPayment.getAmount(), Matchers.is(BigInteger.valueOf(1000)));
    assertThat(nextPayment.getCurrency(), Matchers.is("JPY"));
    assertThat(nextPayment.getAmountFormatted(), Matchers.is(BigDecimal.valueOf(1000)));
    assertFalse(nextPayment.getIsPaid());
    assertFalse(nextPayment.getIsLastPayment());
    assertNotNull(nextPayment.getCreatedOn());
    assertNotNull(nextPayment.getUpdatedOn());
  }
}
