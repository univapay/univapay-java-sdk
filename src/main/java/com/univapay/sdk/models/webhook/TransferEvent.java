package com.univapay.sdk.models.webhook;

import com.univapay.sdk.models.response.transfer.Transfer;
import com.univapay.sdk.types.PaymentSystemEvent;

public class TransferEvent extends WebhookEvent<Transfer> {
  public TransferEvent(PaymentSystemEvent event, Transfer data) {
    super(event, data);
  }
}
