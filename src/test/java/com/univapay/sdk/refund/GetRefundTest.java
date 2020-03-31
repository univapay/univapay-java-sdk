package com.univapay.sdk.refund;

import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.RefundId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.refund.Refund;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.RefundReason;
import com.univapay.sdk.types.RefundStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

public class GetRefundTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnRefundInfo() throws InterruptedException, ParseException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/"
            + "6791acdd-d901-49b8-a46f-24a7a39e894f/refunds/45f1a7ac-903e-4c46-a959-5564f4fdc5ca",
        token,
        200,
        ChargesFakeRR.getRefundFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final Date parsedDate = dateParser.parseDateTime("2017-06-22T16:00:55.436116+09:00").toDate();

    univapay
        .getRefund(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new ChargeId("6791acdd-d901-49b8-a46f-24a7a39e894f"),
            new RefundId("45f1a7ac-903e-4c46-a959-5564f4fdc5ca"))
        .build()
        .dispatch(
            new UnivapayCallback<Refund>() {
              @Override
              public void getResponse(Refund response) {
                assertEquals(response.getId().toString(), "45f1a7ac-903e-4c46-a959-5564f4fdc5ca");
                assertEquals(
                    response.getStoreId().toString(), "653ef5a3-73f2-408a-bac5-7058835f7700");
                assertEquals(
                    response.getChargeId().toString(), "6791acdd-d901-49b8-a46f-24a7a39e894f");
                Assert.assertEquals(response.getStatus(), RefundStatus.SUCCESSFUL);
                assertEquals(response.getAmount(), BigInteger.valueOf(15));
                assertEquals(response.getCurrency(), "JPY");
                assertEquals(response.getAmountFormatted(), BigDecimal.valueOf(15));
                assertEquals(response.getReason(), RefundReason.CUSTOMER_REQUEST);
                assertEquals(response.getMessage(), "10% off");
                assertEquals(
                    Integer.parseInt(response.getMetadata().get("cod").toString()), 504547895);
                assertEquals(response.getMetadata().get("prod"), "ticket flight");
                assertNull(response.getError());
                assertEquals(response.getMode(), ProcessingMode.TEST);
                assertEquals(response.getCreatedOn(), parsedDate);
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                fail(error.getMessage());
                notifyCall();
              }
            });

    waitCall();
  }
}
