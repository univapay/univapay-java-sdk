package com.univapay.sdk.transfer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.TransferId;
import com.univapay.sdk.models.response.transfer.Transfer;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.TransferStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.TransfersFakeRR;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class GetTransferTest extends GenericTest {

  @Test
  void shouldRequestAndReturnTransferData() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/transfers/c179c19f-5ccc-4f66-9f79-1a9ed8466098",
        jwt,
        200,
        TransfersFakeRR.getTransferFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2017-11-01T16:00:55.436116+09:00", DateTimeFormatter.ISO_DATE_TIME);

    univapay
        .getTransfer(new TransferId("c179c19f-5ccc-4f66-9f79-1a9ed8466098"))
        .build()
        .dispatch(
            new UnivapayCallback<Transfer>() {
              @Override
              public void getResponse(Transfer response) {
                assertEquals(
                    "c179c19f-5ccc-4f66-9f79-1a9ed8466098", response.getTransferId().toString());
                assertEquals(
                    "f03c16de-30a0-4616-a8d8-39ba1c415e29", response.getBankAccountId().toString());
                assertEquals(response.getAmount(), BigInteger.valueOf(12795009));
                assertEquals(response.getAmountFormatted(), BigDecimal.valueOf(127950.09));
                assertEquals("EUR", response.getCurrency());
                assertEquals(TransferStatus.PROCESSING, response.getStatus());
                assertEquals(response.getFrom(), LocalDate.parse("2017-11-01"));
                assertEquals(response.getTo(), LocalDate.parse("2017-12-01"));
                assertEquals("some note", response.getNote());
                assertNull(response.getErrorCode());
                assertNull(response.getErrorText());
                assertEquals("monthly transfer", response.getMetadata().get("message"));
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
  void shouldRequestAndReturnTransferMetadata() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/transfers/c179c19f-5ccc-4f66-9f79-1a9ed8466098",
        jwt,
        200,
        TransfersFakeRR.getTransferMetadataFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    Transfer response =
        univapay
            .getTransfer(new TransferId("c179c19f-5ccc-4f66-9f79-1a9ed8466098"))
            .build()
            .dispatch();

    assertThat(response.getMetadata().get("float"), is(10.3));
  }
}
