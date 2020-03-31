package com.univapay.sdk.pagination;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.LedgerId;
import com.univapay.sdk.models.common.TransferId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.LedgersFakeRR;
import java.io.IOException;
import org.junit.Test;

public class LedgersPaginationTest extends GenericTest {

  @Test
  public void shouldRequestListOfLedgersWithPaginationParams()
      throws IOException, UnivapayException {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/transfers/8e5269b8-ec32-11e6-88ca-fb17c7a225eb/ledgers?limit=20&cursor_direction=asc&cursor=f8c86f7a-18f3-11e7-93ff-6feb8d2883ae",
        token,
        204,
        "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay
        .listLedgers(new TransferId("8e5269b8-ec32-11e6-88ca-fb17c7a225eb"))
        .setLimit(20)
        .setCursorDirection(CursorDirection.ASC)
        .setCursor(new LedgerId("f8c86f7a-18f3-11e7-93ff-6feb8d2883ae"))
        .build()
        .dispatch();

    verify(
        getRequestedFor(
            urlEqualTo(
                "/transfers/8e5269b8-ec32-11e6-88ca-fb17c7a225eb/ledgers?limit=20&cursor_direction=asc&cursor=f8c86f7a-18f3-11e7-93ff-6feb8d2883ae")));
  }

  @Test
  public void shouldRequestNext() {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/transfers/653ef5a3-73f2-408a-bac5-7058835f7700/ledgers",
        token,
        200,
        LedgersFakeRR.listLedgersFakeResponse);

    UnivapaySDK payments = createTestInstance(AuthType.LOGIN_TOKEN);

    payments
        .listLedgers(new TransferId("653ef5a3-73f2-408a-bac5-7058835f7700"))
        .asIterable()
        .iterator()
        .next();
  }
}
