package com.univapay.sdk.models.common;

import java.util.UUID;

public class RefundId extends BaseId {
  @SuppressWarnings("SameParameterValue")
  public RefundId(String id) {
    super(id);
  }

  public RefundId(UUID id) {
    super(id);
  }
}
