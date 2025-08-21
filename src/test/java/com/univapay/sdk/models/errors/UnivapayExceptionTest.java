package com.univapay.sdk.models.errors;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class UnivapayExceptionTest {
  @Test
  void toString_shouldGenerateHttpResult() {
    String actual = new UnivapayException(404, "Not Found", null).toString();
    assertTrue(actual.contains("404 Not Found"));
  }

  @Test
  void toString_shouldUnivapayErrorMessage() {
    String actual =
        new UnivapayException(400, "Bad Request", new UnivapayErrorBody("CODE", "STATUS", null))
            .toString();

    assertTrue(actual.contains("code:CODE, status:STATUS, details:null"));
  }

  @Test
  void toString_shouldUnivapayErrorDetails() {
    List<DetailedError> details = new ArrayList<>();
    details.add(new DetailedError("email", "invalid-format"));
    details.add(new DetailedError("password", "too-short"));
    String actual =
        new UnivapayException(400, "Bad Request", new UnivapayErrorBody("CODE", "STATUS", details))
            .toString();

    assertTrue(actual.contains("email:invalid-format"));
  }
}
