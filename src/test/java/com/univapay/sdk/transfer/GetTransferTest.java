package com.univapay.sdk.transfer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.TransferId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.transfer.Transfer;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.TransferStatus;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.metadataadapter.MetadataFloatAdapter;
import com.univapay.sdk.utils.mockcontent.TransfersFakeRR;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import org.joda.time.LocalDate;
import org.junit.Test;

public class GetTransferTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnTransferData() throws InterruptedException, ParseException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/transfers/c179c19f-5ccc-4f66-9f79-1a9ed8466098",
        token,
        200,
        TransfersFakeRR.getTransferFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final Date parsedDate = dateParser.parseDateTime("2017-11-01T16:00:55.436116+09:00").toDate();

    univapay
        .getTransfer(new TransferId("c179c19f-5ccc-4f66-9f79-1a9ed8466098"))
        .build()
        .dispatch(
            new UnivapayCallback<Transfer>() {
              @Override
              public void getResponse(Transfer response) {
                assertEquals(
                    response.getTransferId().toString(), "c179c19f-5ccc-4f66-9f79-1a9ed8466098");
                assertEquals(
                    response.getBankAccountId().toString(), "f03c16de-30a0-4616-a8d8-39ba1c415e29");
                assertEquals(response.getAmount(), BigInteger.valueOf(12795009));
                assertEquals(response.getAmountFormatted(), BigDecimal.valueOf(127950.09));
                assertEquals(response.getCurrency(), "EUR");
                assertEquals(response.getStatus(), TransferStatus.PROCESSING);
                assertEquals(response.getFrom(), LocalDate.parse("2017-11-01"));
                assertEquals(response.getTo(), LocalDate.parse("2017-12-01"));
                assertEquals(response.getNote(), "some note");
                assertNull(response.getErrorCode());
                assertNull(response.getErrorText());
                assertEquals(response.getMetadata().get("message"), "monthly transfer");
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
  public void shouldRequestAndReturnTransferMetadata() throws IOException, UnivapayException {
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

    assertThat(response.getMetadata().get("float"), is("10.3"));
    MetadataFloatAdapter adapter = new MetadataFloatAdapter();
    assertThat(response.getMetadata(adapter).get("float"), is(Float.valueOf("10.3")));
  }
}
