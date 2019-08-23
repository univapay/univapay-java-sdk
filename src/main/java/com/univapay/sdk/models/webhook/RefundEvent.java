package com.univapay.sdk.models.webhook;

import com.univapay.sdk.types.PaymentSystemEvent;
import com.univapay.sdk.models.response.refund.Refund;

public class RefundEvent extends WebhookEvent<Refund> {
  public RefundEvent(PaymentSystemEvent event, Refund data) {
    super(event, data);
  }
}
