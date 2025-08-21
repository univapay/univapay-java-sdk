package com.univapay.sdk.refund;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.refund.Refund;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.ChargesFakeRR;
import org.junit.jupiter.api.Test;

class ListRefundsTest extends GenericTest {

  @Test
  void shouldRequestAndReturnListOfRefunds() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges"
            + "/6791acdd-d901-49b8-a46f-24a7a39e894f/refunds",
        jwt,
        200,
        ChargesFakeRR.listAllRefundsFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .listRefunds(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new ChargeId("6791acdd-d901-49b8-a46f-24a7a39e894f"))
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<Refund>>() {
              @Override
              public void getResponse(PaginatedList<Refund> response) {
                assertFalse(response.getHasMore());
                assertEquals(
                    "04ea4e3e-3f19-43d3-8593-fed3aba06770",
                    response.getItems().get(0).getId().toString());
                assertEquals(
                    "45f1a7ac-903e-4c46-a959-5564f4fdc5ca",
                    response.getItems().get(1).getId().toString());
                assertEquals(
                    "There was a processing error",
                    response.getItems().get(0).getError().getMessage());
                assertNull(response.getItems().get(1).getError());
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
  void shouldPerformSearch() throws Exception {
    final String fullPath =
        "/stores/653ef5a3-73f2-408a-bac5-7058835f7700/charges"
            + "/6791acdd-d901-49b8-a46f-24a7a39e894f/refunds?metadata=surf%20board";

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET", fullPath, jwt, 200, ChargesFakeRR.listAllRefundsFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .listRefunds(
            new StoreId("653ef5a3-73f2-408a-bac5-7058835f7700"),
            new ChargeId("6791acdd-d901-49b8-a46f-24a7a39e894f"))
        .withMetadataSearch("surf board")
        .build()
        .dispatch();

    verify(getRequestedFor(urlEqualTo(fullPath)));
  }
}
