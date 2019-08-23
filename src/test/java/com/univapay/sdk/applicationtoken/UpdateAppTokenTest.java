package com.univapay.sdk.applicationtoken;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import org.junit.Test;

public class UpdateAppTokenTest extends GenericTest {

  @Test
  public void shouldUpdateAndReturnAppTokenInfo()
      throws InterruptedException, IOException, ParseException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "PATCH",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/"
            + "app_tokens/90389195-ce76-43de-935b-7f1d417d23df",
        token,
        200,
        StoreFakeRR.updateStoreAppTokenFakeResponse);

    final Date parsedCreatedOn =
        dateParser.parseDateTime("2017-06-22T16:00:55.436116+09:00").toDate();

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

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
                assertEquals(response.getId().toString(), "90389195-ce76-43de-935b-7f1d417d23df");
                assertEquals(
                    response.getStoreId().toString(), "bf75472e-7f2d-4745-a66d-9b96ae031c7a");
                assertEquals(response.getToken(), "qjjSz5NdM4MWcqbnH2xd");
                assertEquals(response.getDomains().size(), 2);
                assertEquals(response.getDomains().get(0).asString(), "www.something.com");
                assertEquals(response.getDomains().get(1).asString(), "www.somethingelse.com");
                assertEquals(response.getMode(), ProcessingMode.TEST);
                assertEquals(response.getCreatedOn(), parsedCreatedOn);
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
