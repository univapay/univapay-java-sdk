package com.univapay.sdk.models.webhook;

import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.PaymentSystemEvent;

public class ChargeEvent extends WebhookEvent<Charge> {
  public ChargeEvent(PaymentSystemEvent event, Charge data) {
    super(event, data);
  }
}
