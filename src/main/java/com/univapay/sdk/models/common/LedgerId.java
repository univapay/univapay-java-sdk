package com.univapay.sdk.models.common;

import java.util.UUID;

public class LedgerId extends BaseId {
  public LedgerId(String id) {
    super(id);
  }

  public LedgerId(UUID id) {
    super(id);
  }
}
