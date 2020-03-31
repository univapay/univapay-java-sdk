package com.univapay.sdk.models.webhook;

import com.univapay.sdk.models.response.refund.Refund;
import com.univapay.sdk.types.PaymentSystemEvent;

public class RefundEvent extends WebhookEvent<Refund> {
  public RefundEvent(PaymentSystemEvent event, Refund data) {
    super(event, data);
  }
}
