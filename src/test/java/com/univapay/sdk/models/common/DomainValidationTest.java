package com.univapay.sdk.models.common;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class DomainValidationTest {
  @Test
  public void shouldAllowOkDomain() {
    final String domain = "ok-domain_1234567890.com";
    new Domain(domain);
  }

  @Test
  public void shouldAllowInvertedDomain() {
    final String domain = "com.univapay.example";
    new Domain(domain);
  }

  @Test
  public void shouldAllowAsteriskDomain() {
    final String domain = "*";
    new Domain(domain);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldNotAllowIllegalDomain() {
    final String domain = "invalid/domain";
    try {
      new Domain(domain);
    } catch (Exception e) {
      assertThat(e.getMessage(), is(String.format("'%s' is not a valid domain", domain)));
      throw e;
    }
  }
}
