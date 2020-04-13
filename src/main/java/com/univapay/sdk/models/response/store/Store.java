package com.univapay.sdk.models.response.store;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Store extends UnivapayResponse implements SimpleModel<StoreId> {
  @SerializedName("id")
  private UUID id;

  @SerializedName("name")
  private String name;

  @SerializedName("created_on")
  private OffsetDateTime createdOn;

  public StoreId getId() {
    return new StoreId(id);
  }

  public String getName() {
    return name;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }
}
