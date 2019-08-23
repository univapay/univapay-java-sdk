package com.univapay.sdk.models.webhook;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.PaymentSystemEvent;

public class WebhookEvent<T> {

  public WebhookEvent(PaymentSystemEvent event, T data) {
    this.event = event;
    this.data = data;
  }

  @SerializedName("event")
  private PaymentSystemEvent event;

  @SerializedName("data")
  private T data;

  public PaymentSystemEvent getEvent() {
    return event;
  }

  public T getData() {
    return data;
  }
}
