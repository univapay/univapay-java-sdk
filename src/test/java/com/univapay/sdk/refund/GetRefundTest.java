package com.univapay.sdk.refund;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class GetRefundTest extends GenericTest {

  @Test
  void shouldRequestAndReturnRefundInfo() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/"
            + "6791acdd-d901-49b8-a46f-24a7a39e894f/refunds/45f1a7ac-903e-4c46-a959-5564f4fdc5ca",
        jwt,
        200,
        ChargesFakeRR.getRefundFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2017-06-22T16:00:55.436116+09:00", DateTimeFormatter.ISO_DATE_TIME);

    Refund response =
        univapay
            .getRefund(
                new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
                new ChargeId("6791acdd-d901-49b8-a46f-24a7a39e894f"),
                new RefundId("45f1a7ac-903e-4c46-a959-5564f4fdc5ca"))
            .build()
            .dispatch();

    assertEquals("45f1a7ac-903e-4c46-a959-5564f4fdc5ca", response.getId().toString());
    assertEquals("653ef5a3-73f2-408a-bac5-7058835f7700", response.getStoreId().toString());
    assertEquals("6791acdd-d901-49b8-a46f-24a7a39e894f", response.getChargeId().toString());
    assertEquals(RefundStatus.SUCCESSFUL, response.getStatus());
    assertEquals(response.getAmount(), BigInteger.valueOf(15));
    assertEquals("JPY", response.getCurrency());
    assertEquals(response.getAmountFormatted(), BigDecimal.valueOf(15));
    assertEquals(RefundReason.CUSTOMER_REQUEST, response.getReason());
    assertEquals("10% off", response.getMessage());
    assertEquals("504547895", response.getMetadata().get("cod"));
    assertEquals("ticket flight", response.getMetadata().get("prod"));
    assertNull(response.getError());
    assertEquals(ProcessingMode.TEST, response.getMode());
    assertEquals(response.getCreatedOn(), parsedDate);
  }
}
