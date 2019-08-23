package com.univapay.sdk.models.common;

import java.util.UUID;

public class IdempotencyKey {
  private String key = null;

  public IdempotencyKey(String key) {
    this.key = key;
  }

  public IdempotencyKey(UUID uuid) {
    this.key = uuid.toString();
  }

  public String getKey() {
    return key;
  }

  public static IdempotencyKey randomFromUUID() {
    return new IdempotencyKey(UUID.randomUUID().toString());
  }
}
