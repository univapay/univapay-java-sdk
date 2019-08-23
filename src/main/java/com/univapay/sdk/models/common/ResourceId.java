package com.univapay.sdk.models.common;

import java.util.UUID;

public class ResourceId extends BaseId {

  public ResourceId(String id) {
    super(id);
  }

  public ResourceId(UUID id) {
    super(id);
  }
}
