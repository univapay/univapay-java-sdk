package com.univapay.sdk.models.response.applicationtoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.StoreId;
import java.util.UUID;

public class StoreApplicationJWT extends ApplicationJWT {

  @SerializedName("store_id")
  private UUID storeId;

  public StoreId getStoreId() {
    return new StoreId(storeId);
  }
}
