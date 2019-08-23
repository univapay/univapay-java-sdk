package com.univapay.sdk.models.common;

public class EmptyEmailAddress implements UnivapayEmailAddress {
  @Override
  public String serialize() {
    return "";
  }
}
