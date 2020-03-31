package com.univapay.sdk.transactiontoken;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenWithData;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGeneratorWithAppTokenSecret;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.io.IOException;
import org.junit.Test;

public class ConfirmTransactionTokenTest extends GenericTest {

  @Test
  public void shouldConfirmTransactionToken() throws IOException, UnivapayException {
    MockRRGeneratorWithAppTokenSecret mockRRGenerator = new MockRRGeneratorWithAppTokenSecret();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/stores/11e8ed6b-6237-578a-991b-57a25a9c1725/tokens/11e8ed6c-c920-994c-ae3c-5324be320d7c/confirm",
        appToken,
        secret,
        200,
        StoreFakeRR.confirmTransactionTokenFakeResponse,
        StoreFakeRR.confirmTransactionTokenFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.APP_TOKEN);

    TransactionTokenWithData token =
        univapay
            .confirmTransactionToken(
                new StoreId("11e8ed6b-6237-578a-991b-57a25a9c1725"),
                new TransactionTokenId("11e8ed6c-c920-994c-ae3c-5324be320d7c"),
                "1111")
            .build()
            .dispatch();
    assertThat(token.getId().toString(), is("11e8ed6c-c920-994c-ae3c-5324be320d7c"));
    assertThat(token.getStoreId().toString(), is("11e8ed6b-6237-578a-991b-57a25a9c1725"));
    assertThat(token.getConfirmed(), is(true));
  }
}
