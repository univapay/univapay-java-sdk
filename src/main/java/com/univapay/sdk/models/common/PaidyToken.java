package com.univapay.sdk.models.common;

import java.util.Objects;

public class PaidyToken {
  private String paidyToken;

  public PaidyToken(String paidyToken) {
    this.paidyToken = paidyToken;
  }

  public String getPaidyToken() {
    return paidyToken;
  }

  @Override
  public String toString() {
    if (this.paidyToken == null) {
      return null;
    }
    return this.paidyToken;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PaidyToken that = (PaidyToken) o;
    return Objects.equals(paidyToken, that.paidyToken);
  }

  @Override
  public int hashCode() {

    return Objects.hash(paidyToken);
  }
}
