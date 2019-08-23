package com.univapay.sdk.models.request.store;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.PaymentSystemEvent;
import java.net.URL;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class WebhookReq {
  @SerializedName("url")
  private URL url;

  @SerializedName("triggers")
  private List<PaymentSystemEvent> triggers;

  public WebhookReq(URL url, List<PaymentSystemEvent> triggers) {
    this.url = url;
    this.triggers = triggers;
  }
}
