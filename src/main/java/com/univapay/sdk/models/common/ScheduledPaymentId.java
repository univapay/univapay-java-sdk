package com.univapay.sdk.models.common;

import java.util.UUID;

public class ScheduledPaymentId extends BaseId {

  public ScheduledPaymentId(String id) {
    super(id);
  }

  public ScheduledPaymentId(UUID id) {
    super(id);
  }
}
