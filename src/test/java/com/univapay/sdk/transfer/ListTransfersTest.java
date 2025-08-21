package com.univapay.sdk.transfer;

import static org.junit.jupiter.api.Assertions.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.transfer.Transfer;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.TransferStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.TransfersFakeRR;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class ListTransfersTest extends GenericTest {

  @Test
  void shouldRequestAndReturnListOfTransfers() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET", "/transfers", jwt, 200, TransfersFakeRR.listAllTransfersFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2017-11-01T16:00:55.436116+09:00", DateTimeFormatter.ISO_DATE_TIME);

    univapay
        .listTransfers()
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Transfer>>() {
              @Override
              public void getResponse(PaginatedList<Transfer> response) {
                assertFalse(response.getHasMore());
                assertEquals(
                    "45f1a7ac-903e-4c46-a959-5564f4fdc5ca",
                    response.getItems().get(0).getTransferId().toString());
                assertEquals(
                    "6791acdd-d901-49b8-a46f-24a7a39e894f",
                    response.getItems().get(0).getBankAccountId().toString());
                assertEquals(response.getItems().get(0).getAmount(), BigInteger.valueOf(25));
                assertEquals("JPY", response.getItems().get(0).getCurrency());
                assertEquals(TransferStatus.PAID, response.getItems().get(0).getStatus());
                assertEquals(
                    "October transfer", response.getItems().get(0).getMetadata().get("message"));
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);
                assertEquals(
                    "45f1a7ac-903e-4c46-a959-5564f4fdc5cb",
                    response.getItems().get(1).getTransferId().toString());
                assertEquals(
                    "6791acdd-d901-49b8-a46f-24a7a39e894e",
                    response.getItems().get(1).getBankAccountId().toString());
                assertEquals(response.getItems().get(1).getAmount(), BigInteger.valueOf(30));
                assertEquals("JPY", response.getItems().get(1).getCurrency());
                assertEquals(TransferStatus.APPROVED, response.getItems().get(1).getStatus());
                assertEquals(
                    "November transfer", response.getItems().get(1).getMetadata().get("message"));
                assertEquals(response.getItems().get(1).getCreatedOn(), parsedDate);
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {

                notifyCall();
                fail();
              }
            });

    waitCall();
  }
}
