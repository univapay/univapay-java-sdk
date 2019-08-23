package com.univapay.sdk.models.common;

import java.util.UUID;

public class CancelId extends BaseId {

  public CancelId(String id) {
    super(id);
  }

  public CancelId(UUID id) {
    super(id);
  }
}
