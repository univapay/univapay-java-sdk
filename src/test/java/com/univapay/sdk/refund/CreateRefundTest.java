package com.univapay.sdk.refund;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.MoneyLike;
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
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CreateRefundTest extends GenericTest {

  @Test
  void shouldPostAndReturnRefundData() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/6791acdd-d901-49b8-a46f-24a7a39e894f/refunds",
        jwt,
        201,
        ChargesFakeRR.createRefundFakeResponse,
        ChargesFakeRR.createRefundFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("cod", "504547895");
    requestMetadata.put("prod", "ticket flight");

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2017-06-22T16:00:55.436116+09:00", DateTimeFormatter.ISO_DATE_TIME);

    univapay
        .createRefund(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new ChargeId("6791acdd-d901-49b8-a46f-24a7a39e894f"),
            BigInteger.valueOf(15),
            "JPY",
            RefundReason.CUSTOMER_REQUEST)
        .withMetadata(requestMetadata)
        .withMessage("10% off")
        .build()
        .dispatch(
            new UnivapayCallback<Refund>() {
              @Override
              public void getResponse(Refund response) {

                assertEquals("677471f5-2781-458b-9797-2a3548dccc5a", response.getId().toString());
                assertEquals(
                    "653ef5a3-73f2-408a-bac5-7058835f7700", response.getStoreId().toString());
                assertEquals(
                    "6791acdd-d901-49b8-a46f-24a7a39e894f", response.getChargeId().toString());
                assertEquals(RefundStatus.PENDING, response.getStatus());
                assertEquals(response.getAmount(), BigInteger.valueOf(15));
                assertEquals("JPY", response.getCurrency());
                assertEquals(response.getAmountFormatted(), BigDecimal.valueOf(15));
                assertEquals(RefundReason.CUSTOMER_REQUEST, response.getReason());
                assertEquals("10% off", response.getMessage());
                assertNull(response.getError());
                assertEquals("504547895", response.getMetadata().get("cod"));
                assertEquals("ticket flight", response.getMetadata().get("prod"));
                assertEquals(ProcessingMode.TEST, response.getMode());
                assertEquals(response.getCreatedOn(), parsedDate);
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
  void shouldCreateRefundWithMoneyAsParameter() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/6791acdd-d901-49b8-a46f-24a7a39e894f/refunds",
        jwt,
        201,
        ChargesFakeRR.createRefundFakeResponse,
        ChargesFakeRR.createRefundFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("cod", "504547895");
    requestMetadata.put("prod", "ticket flight");

    univapay
        .createRefund(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new ChargeId("6791acdd-d901-49b8-a46f-24a7a39e894f"),
            new MoneyLike(BigInteger.valueOf(15), "JPY"),
            RefundReason.CUSTOMER_REQUEST)
        .withMetadata(requestMetadata)
        .withMessage("10% off")
        .build()
        .dispatch();
  }

  @Test
  void CreateRefundWithoutMetadataTest() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/6791acdd-d901-49b8-a46f-24a7a39e894f/refunds",
        jwt,
        201,
        ChargesFakeRR.createRefundWithoutMetadataFakeResponse,
        ChargesFakeRR.createRefundWithoutMetadataFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2017-06-22T16:00:55.436116+09:00", DateTimeFormatter.ISO_DATE_TIME);

    univapay
        .createRefund(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new ChargeId("6791acdd-d901-49b8-a46f-24a7a39e894f"),
            BigInteger.valueOf(15),
            "JPY",
            RefundReason.CUSTOMER_REQUEST)
        .withMessage("10% off")
        .build()
        .dispatch(
            new UnivapayCallback<Refund>() {
              @Override
              public void getResponse(Refund response) {

                assertEquals("677471f5-2781-458b-9797-2a3548dccc5a", response.getId().toString());
                assertEquals(
                    "6791acdd-d901-49b8-a46f-24a7a39e894f", response.getChargeId().toString());
                assertEquals(RefundStatus.PENDING, response.getStatus());
                assertEquals(response.getAmount(), BigInteger.valueOf(15));
                assertEquals("JPY", response.getCurrency());
                assertEquals(RefundReason.CUSTOMER_REQUEST, response.getReason());
                assertEquals("10% off", response.getMessage());
                assertNull(response.getError());
                assertEquals(ProcessingMode.TEST, response.getMode());
                assertEquals(response.getCreatedOn(), parsedDate);
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
  void shouldPostRefundUniqueMetadata() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges/6791acdd-d901-49b8-a46f-24a7a39e894f/refunds",
        jwt,
        200,
        ChargesFakeRR.createRefundMetadataFloatFakeResponse,
        ChargesFakeRR.createRefundMetadataFloatFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("float", 10.3);

    Refund response =
        univapay
            .createRefund(
                new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
                new ChargeId("6791acdd-d901-49b8-a46f-24a7a39e894f"),
                BigInteger.valueOf(15),
                "JPY",
                RefundReason.CUSTOMER_REQUEST)
            .withMetadata(requestMetadata)
            .build()
            .dispatch();
    assertThat(response.getMetadata().get("float"), is(10.3));
  }
}
