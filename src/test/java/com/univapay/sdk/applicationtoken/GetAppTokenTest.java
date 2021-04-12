package com.univapay.sdk.applicationtoken;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Test;

public class GetAppTokenTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnAppTokenInfo() throws InterruptedException, ParseException {
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
                    response.getItems().get(0).getId().toString(),
                    "c0c9176b-0dd8-4644-85f9-adea6a7fca81");
                assertEquals(
                    response.getItems().get(0).getStoreId().toString(),
                    "bf75472e-7f2d-4745-a66d-9b96ae031c7a");
                assertEquals(response.getItems().get(0).getToken(), "dWDBXuPjccKAslUsdMSK");
                assertEquals(
                    response.getItems().get(0).getDomains().get(0).asString(), "www.test.com");
                assertEquals(response.getItems().get(0).getMode(), ProcessingMode.TEST);
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
