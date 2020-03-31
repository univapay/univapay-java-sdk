package com.univapay.sdk.subscription;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.subscription.ScheduleSettings;
import com.univapay.sdk.models.response.subscription.Subscription;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.SubscriptionPeriod;
import com.univapay.sdk.types.SubscriptionStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import org.hamcrest.Matchers;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.threeten.bp.ZoneId;

public class ListSubscriptionsMerchantTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnListOfSubscriptions()
      throws InterruptedException, ParseException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/subscriptions",
        token,
        200,
        ChargesFakeRR.listAllMerchantSubscriptionsFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay
        .listSubscriptions()
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Subscription>>() {
              @Override
              public void getResponse(PaginatedList<Subscription> response) {

                assertFalse(response.getHasMore());
                assertThat(response.getItems().size(), is(2));
                Subscription firstItem = response.getItems().get(0);

                assertThat(
                    firstItem.getId().toString(),
                    Matchers.is("11e897b6-0191-b412-ad05-87632bade75a"));
                assertThat(
                    firstItem.getStoreId().toString(),
                    Matchers.is("11e82dbf-7e6a-d146-9423-03ae2c18d764"));
                assertThat(
                    firstItem.getTransactionTokenId().toString(),
                    Matchers.is("11e897b6-017d-9108-ad03-c72af7e345ea"));
                assertThat(firstItem.getAmount(), Matchers.is(BigInteger.valueOf(1000)));
                assertThat(firstItem.getCurrency(), Matchers.is("JPY"));
                assertThat(firstItem.getAmountFormatted(), Matchers.is(BigDecimal.valueOf(1000)));
                assertThat(firstItem.getPeriod(), Matchers.is(SubscriptionPeriod.MONTHLY));
                assertThat(firstItem.getInitialAmount(), Matchers.is(BigInteger.valueOf(10000)));
                assertThat(
                    firstItem.getInitialAmountFormatted(), Matchers.is(BigDecimal.valueOf(10000)));
                assertThat(firstItem.getOnlyDirectCurrency(), is(true));
                assertThat(firstItem.getStatus(), Matchers.is(SubscriptionStatus.CURRENT));
                assertThat(firstItem.getDescriptor(), is("test descriptor"));
                assertThat(firstItem.getMode(), Matchers.is(ProcessingMode.TEST));
                assertNotNull(firstItem.getCreatedOn());
                assertThat(firstItem.getMetadata().get("some_key"), Matchers.is("some value"));

                ScheduleSettings scheduleSettings = firstItem.getScheduleSettings();

                assertThat(
                    scheduleSettings.getStartOn(), Matchers.is(LocalDate.parse("2018-08-31")));
                assertThat(scheduleSettings.getZoneId(), Matchers.is(ZoneId.of("America/Cancun")));

                Subscription secondItem = response.getItems().get(1);

                assertThat(secondItem.getOnlyDirectCurrency(), is(false));
                assertThat(secondItem.getDescriptor(), is(nullValue()));

                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                System.out.println(error.getMessage());
                fail();
                notifyCall();
              }
            });

    waitCall();
  }
}
