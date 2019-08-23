package com.univapay.sdk.models.common;

import java.util.UUID;

public class ChargeId extends BaseId {

  public ChargeId(String id) {
    super(id);
  }

  public ChargeId(UUID id) {
    super(id);
  }
}
