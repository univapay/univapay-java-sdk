package com.univapay.sdk.applicationtoken;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.applicationtoken.ApplicationToken;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class GetAppTokenTest extends GenericTest {

  @Test
  void shouldRequestAndReturnAppTokenInfo() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/app_tokens",
        jwt,
        200,
        StoreFakeRR.getStoreAppTokenFakeResponse);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2017-06-22T16:00:55.436116+09:00", DateTimeFormatter.ISO_DATE_TIME);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay
        .listAppTokens(new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"))
        .build()
        .dispatch(
            new UnivapayCallback<PaginatedList<ApplicationToken>>() {
              @Override
              public void getResponse(PaginatedList<ApplicationToken> response) {
                assertFalse(response.getHasMore());
                assertEquals(
                    "c0c9176b-0dd8-4644-85f9-adea6a7fca81",
                    response.getItems().get(0).getId().toString());
                assertEquals(
                    "bf75472e-7f2d-4745-a66d-9b96ae031c7a",
                    response.getItems().get(0).getStoreId().toString());
                assertEquals("dWDBXuPjccKAslUsdMSK", response.getItems().get(0).getToken());
                assertEquals(
                    "www.test.com", response.getItems().get(0).getDomains().get(0).asString());
                assertEquals(ProcessingMode.TEST, response.getItems().get(0).getMode());
                assertEquals(response.getItems().get(0).getCreatedOn(), parsedDate);
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                notifyCall();
              }
            });

    waitCall();
  }
}
