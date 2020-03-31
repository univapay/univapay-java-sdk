package com.univapay.sdk.models.webhook;

import com.univapay.sdk.models.response.cancel.Cancel;
import com.univapay.sdk.types.PaymentSystemEvent;

public class CancelEvent extends WebhookEvent<Cancel> {
  public CancelEvent(PaymentSystemEvent event, Cancel data) {
    super(event, data);
  }
}
