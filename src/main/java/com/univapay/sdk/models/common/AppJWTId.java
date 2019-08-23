package com.univapay.sdk.models.common;

import java.util.UUID;

public class AppJWTId extends BaseId {

  public AppJWTId(String id) {
    super(id);
  }

  public AppJWTId(UUID id) {
    super(id);
  }
}
