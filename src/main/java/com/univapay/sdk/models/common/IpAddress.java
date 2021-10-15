package com.univapay.sdk.models.common;

import java.util.Objects;
import org.apache.commons.validator.routines.InetAddressValidator;

public class IpAddress {
  private final String ipAddress;

  public IpAddress(String ipAddress) {
    if (!ipAddress.isEmpty()) {
      InetAddressValidator validator = InetAddressValidator.getInstance();
      if (!validator.isValid(ipAddress)) {
        throw new IllegalArgumentException("Invalid IP Address: " + ipAddress);
      }
    }
    this.ipAddress = ipAddress;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IpAddress ipAddress1 = (IpAddress) o;
    return Objects.equals(ipAddress, ipAddress1.ipAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ipAddress);
  }

  @Override
  public String toString() {
    return ipAddress;
  }
}
