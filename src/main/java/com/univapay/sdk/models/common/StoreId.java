package com.univapay.sdk.models.common;

import java.util.UUID;

public class StoreId extends BaseId {
  public StoreId(String id) {
    super(id);
  }

  public StoreId(UUID id) {
    super(id);
  }
}
