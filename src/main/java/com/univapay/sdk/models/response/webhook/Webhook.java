package com.univapay.sdk.models.response.webhook;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.MerchantId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.WebhookId;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.types.PaymentSystemEvent;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Webhook extends UnivapayResponse implements SimpleModel<WebhookId> {
  @SerializedName("id")
  private UUID id;

  @SerializedName("merchant_id")
  private UUID merchantId;

  @SerializedName("store_id")
  private UUID storeId;

  @SerializedName("triggers")
  private List<PaymentSystemEvent> triggers;

  @SerializedName("url")
  private URL url;

  @SerializedName("created_on")
  private Date createdOn;

  @SerializedName("updated_on")
  private Date updatedOn;

  public WebhookId getId() {
    return new WebhookId(id);
  }

  public MerchantId getMerchantId() {
    return new MerchantId(merchantId);
  }

  public StoreId getStoreId() {
    return new StoreId(storeId);
  }

  public List<PaymentSystemEvent> getTriggers() {
    return triggers;
  }

  public URL getUrl() {
    return url;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public Date getUpdatedOn() {
    return updatedOn;
  }
}
