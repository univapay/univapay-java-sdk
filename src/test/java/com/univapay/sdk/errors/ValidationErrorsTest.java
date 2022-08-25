package com.univapay.sdk.errors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.QrScanData;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenWithData;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.TransactionTokenType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.UnivapayCallback;
import com.univapay.sdk.utils.mockcontent.ErrorsFakeRR;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class ValidationErrorsTest extends GenericTest {

  @Test(expected = IllegalArgumentException.class)
  public void shouldParseValidationErrorsSuccessfully() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/authenticate",
        null,
        400,
        ErrorsFakeRR.invalidFormatFakeResponse,
        ErrorsFakeRR.invalidFormatFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    univapay.getLoginToken("newaccount", "c").build().dispatch();
  }

  @Test
  public void shouldHandleAnEmptyResponseBody() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT("GET", "/stores", null, 520, "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);
    try {
      univapay.listStores().build().dispatch();
    } catch (UnivapayException e) {
      assertNull(e.getBody());
      assertEquals(e.getHttpStatusCode(), 520);
    }
  }

  @Test
  public void shouldHandleAnNullBody() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT("GET", "/stores", null, 401, (String) null);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);
    try {
      univapay.listStores().build().dispatch();
    } catch (UnivapayException e) {
      assertNull(e.getBody());
      assertEquals(e.getHttpStatusCode(), 401);
    }
  }

  @Test
  public void shouldHandleAnEmptyBody() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT("GET", "/stores", null, 500, "");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);
    try {
      univapay.listStores().build().dispatch();
    } catch (UnivapayException e) {
      assertNull(e.getBody());
      assertEquals(e.getHttpStatusCode(), 500);
    }
  }

  @Test
  public void shouldHandleAnEmptyBodyWhenCreatingToken() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT("POST", "/tokens", null, 500, "\"\"");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    UnivapayException exception =
        assertThrows(
            UnivapayException.class,
            () ->
                univapay
                    .createTransactionToken(
                        new QrScanData("TEST QR"), TransactionTokenType.ONE_TIME)
                    .dispatch());

    assertNull(exception.getBody());
    assertEquals(exception.getHttpStatusCode(), 500);
  }

  @Test
  public void shouldHandleAnEmptyBodyWhenCreatingTokenAsync() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT("POST", "/tokens", null, 500, "\"\"");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    CompletableFuture<UnivapayException> execution = new CompletableFuture<>();

    univapay
        .createTransactionToken(new QrScanData("TEST QR"), TransactionTokenType.ONE_TIME)
        .dispatch(
            new UnivapayCallback<TransactionTokenWithData>() {
              @Override
              public void getResponse(TransactionTokenWithData response) {
                execution.completeExceptionally(new AssertionError("Should never be parse-able"));
              }

              @Override
              public void getFailure(Throwable error) {
                if (error instanceof UnivapayException) {
                  execution.complete((UnivapayException) error);
                } else {
                  execution.completeExceptionally(
                      new AssertionError("Must be UnivapayException, but was ", error));
                }
              }
            });

    UnivapayException exception = execution.get(10, TimeUnit.SECONDS);

    assertNull(exception.getBody());
    assertEquals(exception.getHttpStatusCode(), 500);
  }
}
