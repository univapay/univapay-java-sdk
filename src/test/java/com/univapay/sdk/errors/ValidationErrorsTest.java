package com.univapay.sdk.errors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class ValidationErrorsTest extends GenericTest {

  @Test
  void shouldParseValidationErrorsSuccessfully() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/authenticate",
        null,
        400,
        ErrorsFakeRR.invalidFormatFakeResponse,
        ErrorsFakeRR.invalidFormatFakeRequest);
    UnivapaySDK univapay = createTestInstance(AuthType.JWT);
    assertThrows(
        IllegalArgumentException.class,
        () -> univapay.getLoginToken("newaccount", "c").build().dispatch());
  }

  @Test
  void shouldHandleAnEmptyResponseBody() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT("GET", "/stores", null, 520, "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);
    try {
      univapay.listStores().build().dispatch();
    } catch (UnivapayException e) {
      assertNull(e.getBody());
      assertEquals(520, e.getHttpStatusCode());
    }
  }

  @Test
  void shouldHandleAnNullBody() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT("GET", "/stores", null, 401, (String) null);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);
    try {
      univapay.listStores().build().dispatch();
    } catch (UnivapayException e) {
      assertNull(e.getBody());
      assertEquals(401, e.getHttpStatusCode());
    }
  }

  @Test
  void shouldHandleAnEmptyBody() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT("GET", "/stores", null, 500, "");

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);
    try {
      univapay.listStores().build().dispatch();
    } catch (UnivapayException e) {
      assertNull(e.getBody());
      assertEquals(500, e.getHttpStatusCode());
    }
  }

  @Test
  void shouldHandleAnEmptyBodyWhenCreatingToken() throws Exception {
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
    assertEquals(500, exception.getHttpStatusCode());
  }

  @Test
  void shouldHandleAnEmptyBodyWhenCreatingTokenAsync() throws Exception {
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
    assertEquals(500, exception.getHttpStatusCode());
  }

  @Test
  void shouldProduceDetailMessage() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/authenticate",
        null,
        400,
        ErrorsFakeRR.invalidFormatFakeResponse,
        ErrorsFakeRR.invalidFormatFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    try {

      univapay.getLoginToken("test@univapay.com", "c").dispatch();
      fail("Must throw the exception");
    } catch (UnivapayException e) {

      assertThat(e.getHttpStatusCode(), is(400));
      assertThat(e.getBody().getCode(), is("VALIDATION_ERROR"));
      assertThat(e.getBody().getErrors().get(0).getField(), is("password"));
      assertThat(e.getBody().getErrors().get(0).getReason(), is("INVALID_FORMAT_LENGTH"));

      // Some logging frameworks ignore the toString and use the `message` field
      assertEquals(
          "{HTTPStatus: 400 Bad Request, UnivapayError: {code:VALIDATION_ERROR, status:error, details:[password:INVALID_FORMAT_LENGTH]}}",
          e.getMessage());
    }
  }
}
