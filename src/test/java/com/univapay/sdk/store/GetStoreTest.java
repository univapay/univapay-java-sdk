package com.univapay.sdk.store;

import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.store.StoreWithConfiguration;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.text.ParseException;
import java.time.OffsetDateTime;
import org.junit.Assert;
import org.junit.Test;

public class GetStoreTest extends GenericTest {

  @Test
  public void shouldRequestAndReturnStoreInfo() throws InterruptedException, ParseException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "GET",
        "/stores/11e751a6-15b1-169c-8d58-47c3d241a399",
        token,
        200,
        StoreFakeRR.getStoreFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    univapay
        .getStore(new StoreId("11e751a6-15b1-169c-8d58-47c3d241a399"))
        .build()
        .dispatch(
            new UnivapayCallback<StoreWithConfiguration>() {
              @Override
              public void getResponse(StoreWithConfiguration response) {
                Assert.assertEquals(
                    response.getId().toString(), "11e751a6-15b1-169c-8d58-47c3d241a399");
                assertEquals(response.getName(), "UnivaPay");
                assertEquals(response.getCreatedOn(), parsedDate);
                assertTrue(response.getConfiguration().getCardConfiguration().getDebitEnabled());
                assertFalse(response.getConfiguration().getCardConfiguration().getPrepaidEnabled());
                Assert.assertEquals(
                    response.getConfiguration().getLogoUrl().toString(), "http://www.logo.url");
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
