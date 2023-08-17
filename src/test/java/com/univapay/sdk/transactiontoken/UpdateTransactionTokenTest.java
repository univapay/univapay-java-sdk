package com.univapay.sdk.transactiontoken;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenWithData;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class UpdateTransactionTokenTest extends GenericTest {
  @Test
  public void shouldUpdateTransactionTokenInfo() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00",
        jwt,
        200,
        StoreFakeRR.updateTransactionTokenFakeResponse,
        StoreFakeRR.updateTransactionTokenFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final String email = "test@email.com";

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("float", 10.3);

    TransactionTokenWithData response =
        univapay
            .updateTransactionToken(
                new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"),
                new TransactionTokenId("004b391f-1c98-43f8-87de-28b21aaaca00"))
            .withEmail(email)
            .withMetadata(requestMetadata)
            .withCvv(123)
            .build()
            .dispatch();

    assertThat(response.getEmail(), is(email));
    assertThat(response.getMetadata().get("float"), is(10.3));
  }

  @Test
  public void shouldUpdateTransactionTokenInfoUniqueMetadata()
      throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00",
        jwt,
        200,
        StoreFakeRR.updateTransactionTokenFakeResponse,
        StoreFakeRR.updateTransactionTokenMetadataFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    Map<String, Object> requestMetadata = new HashMap<>();
    requestMetadata.put("float", 10.3);

    TransactionTokenWithData response =
        univapay
            .updateTransactionToken(
                new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"),
                new TransactionTokenId("004b391f-1c98-43f8-87de-28b21aaaca00"))
            .withMetadata(requestMetadata)
            .build()
            .dispatch();
    assertThat(response.getMetadata().get("float"), is(10.3));
  }

  @Test
  public void shouldRemoveEmailFromQrTransactionToken() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "PATCH",
        "/stores/bf75472e-7f2d-4745-a66d-9b96ae031c7a/tokens/004b391f-1c98-43f8-87de-28b21aaaca00",
        jwt,
        200,
        StoreFakeRR.updateNoMailTransactionTokenFakeResponse,
        StoreFakeRR.updateNoMailTransactionTokenFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    TransactionTokenWithData response =
        univapay
            .updateTransactionToken(
                new StoreId("bf75472e-7f2d-4745-a66d-9b96ae031c7a"),
                new TransactionTokenId("004b391f-1c98-43f8-87de-28b21aaaca00"))
            .removeEmail()
            .build()
            .dispatch();

    assertThat(response.getEmail(), is(nullValue()));
  }
}
