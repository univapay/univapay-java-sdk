package com.univapay.sdk.models.common;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class IpAddressValidationTest {
  private final List<String> INVALID_IP_ADDRESSES = Arrays.asList("1234567890", "aaaaaaaaaaaaa");

  private final List<String> VALID_IP_ADDRESSES =
      Arrays.asList("172.11.1.111", "2001:0db8:85a3:0000:0000:8a2e:0370:7334", "");

  @Test
  public void shouldThrownIllegalArgumentException() {
    for (String ipAddress : INVALID_IP_ADDRESSES) {
      try {
        new IpAddress(ipAddress);
      } catch (IllegalArgumentException ignored) {
        continue;
      }
      fail("This IP Address should be invalid but passed: " + ipAddress);
    }
  }

  @Test
  public void shouldPassCorrectIpAddress() {
    for (String ipAddress : VALID_IP_ADDRESSES) {
      new IpAddress(ipAddress);
    }
  }
}
