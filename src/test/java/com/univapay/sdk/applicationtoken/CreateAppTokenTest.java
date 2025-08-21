package com.univapay.sdk.applicationtoken;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.Domain;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.applicationtoken.ApplicationToken;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CreateAppTokenTest extends GenericTest {

  @Test
  void shouldPostAppTokenDataAndReturnInfo() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/app_tokens",
        jwt,
        200,
        StoreFakeRR.createStoreAppTokenFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);
    List<Domain> reqDomains = new ArrayList<>();
    reqDomains.add(new Domain("www.test.com"));

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2017-06-22T16:00:55.436116+09:00", DateTimeFormatter.ISO_DATE_TIME);

    univapay
        .createAppToken(new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"), reqDomains)
        .withMode(ProcessingMode.TEST)
        .build()
        .dispatch(
            new UnivapayCallback<ApplicationToken>() {
              @Override
              public void getResponse(ApplicationToken response) {
                assertEquals("90389195-ce76-43de-935b-7f1d417d23df", response.getId().toString());
                assertEquals(
                    "bf75472e-7f2d-4745-a66d-9b96ae031c7a", response.getStoreId().toString());
                assertEquals("qjjSz5NdM4MWcqbnH2xd", response.getToken());
                assertEquals("www.test.com", response.getDomains().get(0).asString());
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
}
