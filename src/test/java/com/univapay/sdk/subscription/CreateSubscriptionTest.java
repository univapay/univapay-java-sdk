package com.univapay.sdk.subscription;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.ScheduledPaymentId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.request.subscription.FixedCycleAmountInstallmentsPlan;
import com.univapay.sdk.models.request.subscription.FixedCycleInstallmentsPlan;
import com.univapay.sdk.models.request.subscription.RevolvingInstallmentsPlan;
import com.univapay.sdk.models.response.subscription.FullSubscription;
import com.univapay.sdk.models.response.subscription.ScheduleSettings;
import com.univapay.sdk.models.response.subscription.ScheduledPayment;
import com.univapay.sdk.types.*;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.MockRRGeneratorWithAppTokenSecret;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.hamcrest.Matchers;
import org.junit.Test;

public class CreateSubscriptionTest extends GenericTest {

  private final LocalDate startOn = LocalDate.parse("2020-08-31");
  private final BigInteger initialAmount = BigInteger.valueOf(1000);

  @Test
  public void shouldPostAndReturnSubscriptionInfo() throws InterruptedException {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/subscriptions",
        appToken,
        secret,
        201,
        ChargesFakeRR.createSubscriptionFakeResponse,
        ChargesFakeRR.createSubscriptionFakeRequest);

    TransactionTokenId transactionTokenId =
        new TransactionTokenId("11e897ac-8cb5-36c2-aea6-0fe2b400c0b1");

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    Map<String, String> requestMetadata = new HashMap<>();
    requestMetadata.put("some_key", "some value");
    final String descriptor = "test descriptor";

    final Duration firstChargeCaptureAfter = Duration.ofDays(2);
    univapay
        .createSubscription(
            transactionTokenId, BigInteger.valueOf(10000), "JPY", SubscriptionPeriod.MONTHLY)
        .withInitialAmount(initialAmount)
        .withStartOn(startOn)
        .withPreserveEndOfMoth(true)
        .withZoneId(ZoneId.of("America/Cancun"))
        .withMetadata(requestMetadata)
        .withOnlyDirectCurrency(true)
        .withDescriptor(descriptor)
        .withFirstChargeCaptureAfter(firstChargeCaptureAfter)
        .withFirstChargeAuthorizationOnly(true)
        .build()
        .dispatch(
            new UnivapayCallback<FullSubscription>() {
              @Override
              public void getResponse(FullSubscription response) {
                assertThat(response.getId().toString(), is("11e897ac-8cc6-5178-aea8-b7d42ee9639f"));
                assertThat(
                    response.getStoreId().toString(), is("11e82dbf-7e6a-d146-9423-03ae2c18d764"));
                assertThat(
                    response.getTransactionTokenId().toString(),
                    is("11e897ac-8cb5-36c2-aea6-0fe2b400c0b1"));
                assertThat(response.getAmount(), is(BigInteger.valueOf(10000)));
                assertThat(response.getAmountLeft(), is(BigInteger.valueOf(0)));
                assertThat(response.getAmountLeftFormatted(), is(BigDecimal.valueOf(0)));
                assertThat(response.getCurrency(), is("JPY"));
                assertThat(response.getAmountFormatted(), is(BigDecimal.valueOf(10000)));
                assertThat(response.getPeriod(), is(SubscriptionPeriod.MONTHLY));
                assertThat(response.getInitialAmount(), is(BigInteger.valueOf(1000)));
                assertThat(response.getInitialAmountFormatted(), is(BigDecimal.valueOf(1000)));
                assertThat(response.getOnlyDirectCurrency(), is(true));
                assertThat(response.getStatus(), Matchers.is(SubscriptionStatus.UNVERIFIED));
                assertThat(response.getDescriptor(), is(descriptor));
                assertThat(response.getMode(), Matchers.is(ProcessingMode.TEST));
                assertNotNull(response.getCreatedOn());
                assertThat(response.getMetadata().get("some_key"), is("some value"));
                assertThat(response.getFirstChargeCaptureAfter(), is(firstChargeCaptureAfter));

                ScheduleSettings scheduleSettings = response.getScheduleSettings();

                assertThat(scheduleSettings.getStartOn(), is(LocalDate.parse("2018-08-31")));
                assertThat(scheduleSettings.getZoneId(), is(ZoneId.of("America/Cancun")));

                ScheduledPayment nextPayment = response.getNextPayment();

                assertThat(
                    nextPayment.getId().toString(), is("11e897ac-8ccc-b176-aea9-37b0f1f6fa10"));
                assertThat(nextPayment.getDueDate(), is(LocalDate.parse("2018-08-04")));
                assertThat(nextPayment.getZoneId(), is(ZoneId.of("America/Cancun")));
                assertThat(nextPayment.getAmount(), is(BigInteger.valueOf(1000)));
                assertThat(nextPayment.getCurrency(), is("JPY"));
                assertThat(nextPayment.getAmountFormatted(), is(BigDecimal.valueOf(1000)));
                assertFalse(nextPayment.getIsPaid());
                assertFalse(nextPayment.getIsLastPayment());
                assertNotNull(nextPayment.getCreatedOn());
                assertNotNull(nextPayment.getUpdatedOn());

                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  public void shouldCreateSubscriptionWithMoneyAsParameter() throws Exception {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/subscriptions",
        appToken,
        secret,
        201,
        ChargesFakeRR.createSubscriptionFakeResponse,
        ChargesFakeRR.createSubscriptionFakeRequest);

    TransactionTokenId transactionTokenId =
        new TransactionTokenId("11e897ac-8cb5-36c2-aea6-0fe2b400c0b1");

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    Map<String, String> requestMetadata = new HashMap<>();
    requestMetadata.put("some_key", "some value");
    final String descriptor = "test descriptor";
    final Duration firstChargeCaptureAfter = Duration.ofDays(2);

    univapay
        .createSubscription(
            transactionTokenId,
            new MoneyLike(BigInteger.valueOf(10000), "JPY"),
            SubscriptionPeriod.MONTHLY)
        .withInitialAmount(initialAmount)
        .withStartOn(startOn)
        .withPreserveEndOfMoth(true)
        .withZoneId(ZoneId.of("America/Cancun"))
        .withMetadata(requestMetadata)
        .withOnlyDirectCurrency(true)
        .withDescriptor(descriptor)
        .withFirstChargeCaptureAfter(firstChargeCaptureAfter)
        .withFirstChargeAuthorizationOnly(true)
        .build()
        .dispatch(
            new UnivapayCallback<FullSubscription>() {
              @Override
              public void getResponse(FullSubscription response) {
                assertThat(response.getId().toString(), is("11e897ac-8cc6-5178-aea8-b7d42ee9639f"));
                assertThat(
                    response.getStoreId().toString(), is("11e82dbf-7e6a-d146-9423-03ae2c18d764"));
                assertThat(
                    response.getTransactionTokenId().toString(),
                    is("11e897ac-8cb5-36c2-aea6-0fe2b400c0b1"));
                assertThat(response.getAmount(), is(BigInteger.valueOf(10000)));
                assertThat(response.getAmountLeft(), is(BigInteger.valueOf(0)));
                assertThat(response.getAmountLeftFormatted(), is(BigDecimal.valueOf(0)));
                assertThat(response.getCurrency(), is("JPY"));
                assertThat(response.getAmountFormatted(), is(BigDecimal.valueOf(10000)));
                assertThat(response.getPeriod(), is(SubscriptionPeriod.MONTHLY));
                assertThat(response.getInitialAmount(), is(BigInteger.valueOf(1000)));
                assertThat(response.getInitialAmountFormatted(), is(BigDecimal.valueOf(1000)));
                assertThat(response.getOnlyDirectCurrency(), is(true));
                assertThat(response.getDescriptor(), is(descriptor));
                assertThat(response.getStatus(), is(SubscriptionStatus.UNVERIFIED));
                assertThat(response.getMode(), is(ProcessingMode.TEST));
                assertNotNull(response.getCreatedOn());
                assertThat(response.getMetadata().get("some_key"), is("some value"));
                assertThat(response.getFirstChargeCaptureAfter(), is(firstChargeCaptureAfter));
                assertThat(response.getFirstChargeAuthorizationOnly(), is(true));

                ScheduleSettings scheduleSettings = response.getScheduleSettings();

                assertThat(scheduleSettings.getStartOn(), is(LocalDate.parse("2018-08-31")));
                assertThat(scheduleSettings.getZoneId(), is(ZoneId.of("America/Cancun")));

                ScheduledPayment nextPayment = response.getNextPayment();

                assertThat(
                    nextPayment.getId().toString(), is("11e897ac-8ccc-b176-aea9-37b0f1f6fa10"));
                assertThat(nextPayment.getDueDate(), is(LocalDate.parse("2018-08-04")));
                assertThat(nextPayment.getZoneId(), is(ZoneId.of("America/Cancun")));
                assertThat(nextPayment.getAmount(), is(BigInteger.valueOf(1000)));
                assertThat(nextPayment.getCurrency(), is("JPY"));
                assertThat(nextPayment.getAmountFormatted(), is(BigDecimal.valueOf(1000)));
                assertFalse(nextPayment.getIsPaid());
                assertFalse(nextPayment.getIsLastPayment());
                assertNotNull(nextPayment.getCreatedOn());
                assertNotNull(nextPayment.getUpdatedOn());

                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }

  @Test
  public void shouldCreateSubscriptionWithFixedCycleInstallments()
      throws UnivapayException, IOException {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/subscriptions",
        appToken,
        secret,
        201,
        ChargesFakeRR.createFixedCycleInstallmentsSubscriptionFakeResponse,
        ChargesFakeRR.createFixedCycleInstallmentsSubscriptionFakeRequest);

    TransactionTokenId transactionTokenId =
        new TransactionTokenId("11e89704-fa2f-ec0e-8f78-ab45170ecd0d");

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    Map<String, String> requestMetadata = new HashMap<>();
    requestMetadata.put("service", "product payments");

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2018-03-07T18:25:40.128999+09:00", DateTimeFormatter.ISO_DATE_TIME);

    FullSubscription subscription =
        univapay
            .createSubscription(
                transactionTokenId, BigInteger.valueOf(12000), "JPY", SubscriptionPeriod.BIWEEKLY)
            .withMetadata(requestMetadata)
            .withStartOn(LocalDate.parse("2018-08-31"))
            .withZoneId(ZoneId.of("Asia/Tokyo"))
            .withInstallmentPlan(new FixedCycleInstallmentsPlan(5))
            .withInitialAmount(BigInteger.valueOf(1000))
            .build()
            .dispatch();

    assertThat(subscription.getPaymentsLeft(), is(5));
    assertThat(subscription.getAmountLeft(), is(BigInteger.valueOf(0)));
    assertThat(subscription.getAmountLeftFormatted(), is(BigDecimal.valueOf(0)));
    assertThat(subscription.getInitialAmount(), is(BigInteger.valueOf(1000)));
    assertEquals(subscription.getInstallmentPlan().getPlanType(), InstallmentPlanType.FIXED_CYCLES);
    assertEquals(5, (int) subscription.getInstallmentPlan().getFixedCycles());
    assertThat(
        subscription.getNextPayment().getId().toString(),
        Matchers.is(new ScheduledPaymentId("11e89704-fa47-ea16-b484-dfc0efdd7c9f").toString()));
    assertThat(subscription.getNextPayment().getDueDate(), is(LocalDate.parse("2018-08-03")));
    assertThat(subscription.getNextPayment().getZoneId(), is(ZoneId.of("Asia/Tokyo")));
    assertThat(subscription.getNextPayment().getAmount(), is(BigInteger.valueOf(1000)));
    assertThat(subscription.getNextPayment().getCurrency(), is("JPY"));
    assertThat(subscription.getNextPayment().getAmountFormatted(), is(BigDecimal.valueOf(1000)));
    assertFalse(subscription.getNextPayment().getIsPaid());
    assertFalse(subscription.getNextPayment().getIsLastPayment());
    assertNotNull(subscription.getNextPayment().getCreatedOn());
    assertNotNull(subscription.getNextPayment().getUpdatedOn());
  }

  @Test
  public void shouldCreateSubscriptionWithFixedCycleAmountInstallments()
      throws UnivapayException, IOException {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/subscriptions",
        appToken,
        secret,
        201,
        ChargesFakeRR.createFixedCycleAmountInstallmentsSubscriptionFakeResponse,
        ChargesFakeRR.createFixedCycleAmountInstallmentsSubscriptionFakeRequest);

    TransactionTokenId transactionTokenId =
        new TransactionTokenId("11e89704-fa2f-ec0e-8f78-ab45170ecd0d");

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    Map<String, String> requestMetadata = new HashMap<>();
    requestMetadata.put("service", "refrigerator");

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2018-03-07T18:25:40.128999+09:00", DateTimeFormatter.ISO_DATE_TIME);

    FullSubscription subscription =
        univapay
            .createSubscription(
                transactionTokenId, BigInteger.valueOf(12000), "JPY", SubscriptionPeriod.BIWEEKLY)
            .withMetadata(requestMetadata)
            .withStartOn(LocalDate.parse("2018-08-31"))
            .withZoneId(ZoneId.of("Asia/Tokyo"))
            .withInstallmentPlan(new FixedCycleAmountInstallmentsPlan(BigInteger.valueOf(5000)))
            .withInitialAmount(BigInteger.valueOf(1000))
            .build()
            .dispatch();

    assertThat(subscription.getPaymentsLeft(), is(4));
    assertThat(subscription.getInitialAmount(), is(BigInteger.valueOf(1000)));
    assertThat(subscription.getAmountLeft(), is(BigInteger.valueOf(0)));
    assertThat(subscription.getAmountLeftFormatted(), is(BigDecimal.valueOf(0)));
    assertEquals(
        subscription.getInstallmentPlan().getPlanType(), InstallmentPlanType.FIXED_CYCLE_AMOUNT);
    assertEquals(subscription.getInstallmentPlan().getFixedCycleAmount(), BigInteger.valueOf(5000));
    assertEquals(
        "11e89704-9733-65ae-b481-a30a06a542dc", subscription.getNextPayment().getId().toString());
    assertThat(subscription.getNextPayment().getDueDate(), is(LocalDate.parse("2018-08-03")));
    assertThat(subscription.getNextPayment().getZoneId(), is(ZoneId.of("Asia/Tokyo")));
    assertThat(subscription.getNextPayment().getAmount(), is(BigInteger.valueOf(1000)));
    assertThat(subscription.getNextPayment().getCurrency(), is("JPY"));
    assertThat(subscription.getNextPayment().getAmountFormatted(), is(BigDecimal.valueOf(1000)));
    assertFalse(subscription.getNextPayment().getIsPaid());
    assertFalse(subscription.getNextPayment().getIsLastPayment());
    assertNotNull(subscription.getNextPayment().getCreatedOn());
    assertNotNull(subscription.getNextPayment().getUpdatedOn());
  }

  @Test
  public void shouldCreateSubscriptionWithRevolvingAmountInstallments()
      throws UnivapayException, IOException {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/subscriptions",
        appToken,
        secret,
        201,
        ChargesFakeRR.createRevolvingInstallmentsSubscriptionFakeResponse,
        ChargesFakeRR.createRevolvingInstallmentsSubscriptionFakeRequest);

    TransactionTokenId transactionTokenId =
        new TransactionTokenId("7f5eecc8-3b38-4cec-86bb-644af74cb186");

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    Map<String, String> requestMetadata = new HashMap<>();
    requestMetadata.put("reason", "monthly magazine");

    FullSubscription subscription =
        univapay
            .createSubscription(
                transactionTokenId, BigInteger.valueOf(1000), "JPY", SubscriptionPeriod.DAILY)
            .withMetadata(requestMetadata)
            .withInstallmentPlan(new RevolvingInstallmentsPlan())
            .build()
            .dispatch();

    assertEquals(subscription.getInstallmentPlan().getPlanType(), InstallmentPlanType.REVOLVING);
  }

  @Test
  public void shouldCreateSubscriptionUniqueMetadata() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/subscriptions",
        jwt,
        200,
        ChargesFakeRR.createSubscriptionMetadataFakeResponse(false),
        ChargesFakeRR.createSubscriptionMetadataFakeRequest(false));

    TransactionTokenId transactionTokenId =
        new TransactionTokenId("11e897ac-8cb5-36c2-aea6-0fe2b400c0b1");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    Map<String, String> requestMetadata = new HashMap<>();
    requestMetadata.put("name", "test-name");
    requestMetadata.put("value", "1234.7981723987");

    FullSubscription response =
        univapay
            .createSubscription(
                transactionTokenId, BigInteger.valueOf(10000), "JPY", SubscriptionPeriod.MONTHLY)
            .withInitialAmount(initialAmount)
            .withStartOn(startOn)
            .withPreserveEndOfMoth(true)
            .withZoneId(ZoneId.of("America/Cancun"))
            .withMetadata(requestMetadata)
            .build()
            .dispatch();

    assertThat(response.getMetadata(), is(requestMetadata));
  }
}
