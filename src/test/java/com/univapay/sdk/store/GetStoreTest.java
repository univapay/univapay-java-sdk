package com.univapay.sdk.store;

import static org.junit.jupiter.api.Assertions.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.store.StoreWithConfiguration;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;

class GetStoreTest extends GenericTest {

  @Test
  void shouldRequestAndReturnStoreInfo() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/11e751a6-15b1-169c-8d58-47c3d241a399",
        jwt,
        200,
        StoreFakeRR.getStoreFakeResponse);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate = parseDate("2017-06-22T16:00:55.436116+09:00");

    univapay
        .getStore(new StoreId("11e751a6-15b1-169c-8d58-47c3d241a399"))
        .build()
        .dispatch(
            new UnivapayCallback<StoreWithConfiguration>() {
              @Override
              public void getResponse(StoreWithConfiguration response) {
                assertEquals("11e751a6-15b1-169c-8d58-47c3d241a399", response.getId().toString());
                assertEquals("UnivaPay", response.getName());
                assertEquals(response.getCreatedOn(), parsedDate);
                assertTrue(response.getConfiguration().getCardConfiguration().getDebitEnabled());
                assertFalse(response.getConfiguration().getCardConfiguration().getPrepaidEnabled());
                assertEquals(
                    "http://www.logo.url", response.getConfiguration().getLogoUrl().toString());
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
