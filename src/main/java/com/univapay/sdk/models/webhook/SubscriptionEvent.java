package com.univapay.sdk.models.webhook;

import com.univapay.sdk.models.response.subscription.Subscription;
import com.univapay.sdk.types.PaymentSystemEvent;

public class SubscriptionEvent extends WebhookEvent<Subscription> {
  public SubscriptionEvent(PaymentSystemEvent event, Subscription data) {
    super(event, data);
  }
}
