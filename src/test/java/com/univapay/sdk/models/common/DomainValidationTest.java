package com.univapay.sdk.models.common;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class DomainValidationTest {
  @Test
  void shouldAllowOkDomain() {
    final String domain = "ok-domain_1234567890.com";
    new Domain(domain);
  }

  @Test
  void shouldAllowInvertedDomain() {
    final String domain = "com.univapay.example";
    new Domain(domain);
  }

  @Test
  void shouldAllowAsteriskDomain() {
    final String domain = "*";
    new Domain(domain);
  }

  @Test
  void shouldNotAllowIllegalDomain() {
    final String domain = "invalid/domain";
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          try {
            new Domain(domain);
          } catch (Exception e) {
            assertThat(e.getMessage(), is(String.format("'%s' is not a valid domain", domain)));
            throw e;
          }
        });
  }
}
