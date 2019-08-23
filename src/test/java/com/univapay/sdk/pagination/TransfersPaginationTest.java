package com.univapay.sdk.pagination;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.TransferId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.transfer.Transfer;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.TransfersFakeRR;
import org.junit.Test;

public class TransfersPaginationTest extends GenericTest {
  @Test
  public void shouldRequestTransfersWithPaginationParams() throws InterruptedException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/transfers?limit=1&cursor_direction=asc&cursor=45f1a7ac-903e-4c46-a959-5564f4fdc5cb",
        token,
        200,
        TransfersFakeRR.listAllTransfersFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay
        .listTransfers()
        .setLimit(1)
        .setCursorDirection(CursorDirection.ASC)
        .setCursor(new TransferId("45f1a7ac-903e-4c46-a959-5564f4fdc5cb"))
        .build()
        .dispatch(new ExpectSuccessCallback<PaginatedList<Transfer>>());

    waitCall();
  }

  @Test
  public void shouldRequestNext() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET", "/transfers", token, 200, TransfersFakeRR.listAllTransfersFakeResponse);

    UnivapaySDK payments = createTestInstance(AuthType.LOGIN_TOKEN);

    payments.listTransfers().asIterable().iterator().next();
  }
}
