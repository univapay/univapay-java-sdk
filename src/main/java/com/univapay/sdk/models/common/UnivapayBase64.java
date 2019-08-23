package com.univapay.sdk.models.common;

import java.util.Objects;

public class UnivapayBase64 {
  private final String base64;

  public UnivapayBase64(String base64) {
    this.base64 = base64;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UnivapayBase64 that = (UnivapayBase64) o;
    return Objects.equals(base64, that.base64);
  }

  @Override
  public int hashCode() {
    return Objects.hash(base64);
  }

  @Override
  public String toString() {
    return base64;
  }
}
