package com.univapay.sdk.applicationtoken;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class CreateAppTokenTest extends GenericTest {

  @Test
  public void shouldPostAppTokenDataAndReturnInfo() throws InterruptedException, ParseException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/app_tokens",
        token,
        200,
        StoreFakeRR.createStoreAppTokenFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);
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
                assertEquals(response.getId().toString(), "90389195-ce76-43de-935b-7f1d417d23df");
                assertEquals(
                    response.getStoreId().toString(), "bf75472e-7f2d-4745-a66d-9b96ae031c7a");
                assertEquals(response.getToken(), "qjjSz5NdM4MWcqbnH2xd");
                assertEquals(response.getDomains().get(0).asString(), "www.test.com");
                assertEquals(response.getMode(), ProcessingMode.TEST);
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
