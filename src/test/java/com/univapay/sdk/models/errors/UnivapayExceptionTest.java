package com.univapay.sdk.models.errors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class UnivapayExceptionTest {
  @Test
  public void toString_shouldGenerateHttpResult() {
    String actual = new UnivapayException(404, "Not Found", null).toString();
    assertThat(actual, containsString("404 Not Found"));
  }

  @Test
  public void toString_shouldUnivapayErrorMessage() {
    String actual =
        new UnivapayException(400, "Bad Request", new UnivapayErrorBody("CODE", "STATUS", null))
            .toString();

    assertThat(actual, containsString("code:CODE, status:STATUS, details:null"));
  }

  @Test
  public void toString_shouldUnivapayErrorDetails() {
    List<DetailedError> details = new ArrayList<>();
    details.add(new DetailedError("email", "invalid-format"));
    details.add(new DetailedError("password", "too-short"));
    String actual =
        new UnivapayException(400, "Bad Request", new UnivapayErrorBody("CODE", "STATUS", details))
            .toString();

    assertThat(actual, containsString("email:invalid-format"));
  }
}
