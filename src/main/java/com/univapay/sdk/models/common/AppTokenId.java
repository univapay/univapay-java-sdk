package com.univapay.sdk.models.common;

import java.util.UUID;

public class AppTokenId extends BaseId {
  public AppTokenId(String id) {
    super(id);
  }

  public AppTokenId(UUID id) {
    super(id);
  }
}
