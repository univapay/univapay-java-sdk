package com.univapay.sdk.models.common;

import java.util.UUID;

public class SubscriptionId extends BaseId {

  public SubscriptionId(String id) {
    super(id);
  }

  public SubscriptionId(UUID id) {
    super(id);
  }
}
