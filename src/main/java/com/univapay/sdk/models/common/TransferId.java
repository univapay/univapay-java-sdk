package com.univapay.sdk.models.common;

import java.util.UUID;

public class TransferId extends BaseId {
  public TransferId(String id) {
    super(id);
  }

  public TransferId(UUID id) {
    super(id);
  }
}
