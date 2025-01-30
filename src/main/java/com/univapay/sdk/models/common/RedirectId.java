package com.univapay.sdk.models.common;

import java.util.UUID;

public class RedirectId extends BaseId {

  public RedirectId(String id) {
    super(id);
  }

  public RedirectId(UUID id) {
    super(id);
  }
}
