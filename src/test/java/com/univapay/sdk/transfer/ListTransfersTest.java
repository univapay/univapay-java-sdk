package com.univapay.sdk.transfer;

import static org.junit.Assert.*;

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
import java.text.ParseException;
import java.util.Date;
import org.junit.Test;

public class ListTransfersTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnListOfTransfers() throws InterruptedException, ParseException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET", "/transfers", token, 200, TransfersFakeRR.listAllTransfersFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final Date parsedDate = dateParser.parseDateTime("2017-11-01T16:00:55.436116+09:00").toDate();

    univapay
        .listTransfers()
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Transfer>>() {
              @Override
              public void getResponse(PaginatedList<Transfer> response) {
                assertFalse(response.getHasMore());
                assertEquals(
                    response.getItems().get(0).getTransferId().toString(),
                    "45f1a7ac-903e-4c46-a959-5564f4fdc5ca");
                assertEquals(
                    response.getItems().get(0).getBankAccountId().toString(),
                    "6791acdd-d901-49b8-a46f-24a7a39e894f");
                assertEquals(response.getItems().get(0).getAmount(), BigInteger.valueOf(25));
                assertEquals(response.getItems().get(0).getCurrency(), "JPY");
                assertEquals(response.getItems().get(0).getStatus(), TransferStatus.PAID);
                assertEquals(
                    response.getItems().get(0).getMetadata().get("message"), "October transfer");
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);
                assertEquals(
                    response.getItems().get(1).getTransferId().toString(),
                    "45f1a7ac-903e-4c46-a959-5564f4fdc5cb");
                assertEquals(
                    response.getItems().get(1).getBankAccountId().toString(),
                    "6791acdd-d901-49b8-a46f-24a7a39e894e");
                assertEquals(response.getItems().get(1).getAmount(), BigInteger.valueOf(30));
                assertEquals(response.getItems().get(1).getCurrency(), "JPY");
                assertEquals(response.getItems().get(1).getStatus(), TransferStatus.APPROVED);
                assertEquals(
                    response.getItems().get(1).getMetadata().get("message"), "November transfer");
                assertEquals(response.getItems().get(1).getCreatedOn(), parsedDate);
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                fail();
                notifyCall();
              }
            });

    waitCall();
  }
}
