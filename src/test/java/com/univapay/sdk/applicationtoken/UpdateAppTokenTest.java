package com.univapay.sdk.applicationtoken;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.AppTokenId;
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
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class UpdateAppTokenTest extends GenericTest {

  @Test
  void shouldUpdateAndReturnAppTokenInfo() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/"
            + "app_tokens/90389195-ce76-43de-935b-7f1d417d23df",
        jwt,
        200,
        StoreFakeRR.updateStoreAppTokenFakeResponse);

    final OffsetDateTime parsedCreatedOn =
        OffsetDateTime.parse("2017-06-22T16:00:55.436116+09:00", DateTimeFormatter.ISO_DATE_TIME);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    List<Domain> newDomains =
        Arrays.asList(new Domain("www.something.com"), new Domain("www.somethingelse.com"));
    univapay
        .updateAppToken(
            new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"),
            new AppTokenId("90389195-ce76-43de-935b-7f1d417d23df"),
            newDomains)
        .build()
        .dispatch(
            new UnivapayCallback<ApplicationToken>() {
              @Override
              public void getResponse(ApplicationToken response) {
                assertEquals("90389195-ce76-43de-935b-7f1d417d23df", response.getId().toString());
                assertEquals(
                    "bf75472e-7f2d-4745-a66d-9b96ae031c7a", response.getStoreId().toString());
                assertEquals("qjjSz5NdM4MWcqbnH2xd", response.getToken());
                assertEquals(2, response.getDomains().size());
                assertEquals("www.something.com", response.getDomains().get(0).asString());
                assertEquals("www.somethingelse.com", response.getDomains().get(1).asString());
                assertEquals(ProcessingMode.TEST, response.getMode());
                assertEquals(response.getCreatedOn(), parsedCreatedOn);
                notifyCall();
              }

              @Override
              public void getFailure(Throwable error) {
                fail();
              }
            });

    waitCall();
  }
}
