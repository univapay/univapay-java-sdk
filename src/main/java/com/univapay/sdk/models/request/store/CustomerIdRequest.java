package com.univapay.sdk.models.request.store;

import com.google.gson.annotations.SerializedName;

public class CustomerIdRequest {

  @SerializedName("customer_id")
  String customerId;

  public String getCustomerId() {
    return customerId;
  }

  public CustomerIdRequest(String customerId) {
    this.customerId = customerId;
  }
}
