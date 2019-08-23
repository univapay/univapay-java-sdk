package com.univapay.sdk.models.common;

import java.util.UUID;

public class WebhookId extends BaseId {

  public WebhookId(String id) {
    super(id);
  }

  public WebhookId(UUID id) {
    super(id);
  }
}
