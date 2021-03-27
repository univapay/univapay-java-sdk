package com.univapay.sdk.subscription;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.ScheduledPaymentId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ChargeStatus;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.TransactionTokenType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.hamcrest.Matchers;
import org.junit.Test;

public class ListPaymentChargesTest extends GenericTest {

  @Test
  public void shouldListPaymentcharges() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/11e82dbf-7e6a-d146-9423-03ae2c18d764/subscriptions/11e89932-7c5f-78c8-b747-27cf2f8ee9b4/payments/11e89932-7c6a-8376-b749-7b3852eeec56/charges",
        jwt,
        200,
        ChargesFakeRR.listChargesForPaymentFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    PaginatedList<Charge> charges =
        univapay
            .listChargesForPayment(
                new StoreId("11e82dbf-7e6a-d146-9423-03ae2c18d764"),
                new SubscriptionId("11e89932-7c5f-78c8-b747-27cf2f8ee9b4"),
                new ScheduledPaymentId("11e89932-7c6a-8376-b749-7b3852eeec56"))
            .build()
            .dispatch();

    assertFalse(charges.getHasMore());

    Charge charge0 = charges.getItems().get(0);

    assertThat(charge0.getId().toString(), is("11e89932-7c77-9bc4-a326-b7d009577283"));
    assertThat(charge0.getStoreId().toString(), is("11e82dbf-7e6a-d146-9423-03ae2c18d764"));
    assertThat(charge0.getSubscriptionId().toString(), is("11e89932-7c5f-78c8-b747-27cf2f8ee9b4"));
    assertNull(charge0.getCaptureAt());
    assertThat(charge0.getChargedAmount(), is(BigInteger.valueOf(3000)));
    assertThat(charge0.getChargedAmountFormatted(), is(BigDecimal.valueOf(3000)));
    assertThat(charge0.getChargedCurrency(), is("JPY"));
    assertNotNull(charge0.getCreatedOn());
    assertNull(charge0.getError());
    assertThat(charge0.getMode(), Matchers.is(ProcessingMode.TEST));
    assertThat(charge0.getRequestedAmount(), is(BigInteger.valueOf(3000)));
    assertThat(charge0.getRequestedAmountFormatted(), is(BigDecimal.valueOf(3000)));
    assertThat(charge0.getRequestedCurrency(), is("JPY"));
    assertThat(charge0.getStatus(), Matchers.is(ChargeStatus.SUCCESSFUL));
    assertThat(
        charge0.getTransactionTokenId().toString(), is("11e89932-7c53-02fa-b745-03bca6731d36"));
    assertThat(charge0.getTransactionTokenType(), Matchers.is(TransactionTokenType.SUBSCRIPTION));
  }
}
