package com.univapay.sdk.models.webhook;

import com.univapay.sdk.types.PaymentSystemEvent;
import com.univapay.sdk.models.response.cancel.Cancel;

public class CancelEvent extends WebhookEvent<Cancel> {
  public CancelEvent(PaymentSystemEvent event, Cancel data) {
    super(event, data);
  }
}
