package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.response.UnivapayResponse;
import java.util.UUID;

public class UnivapayCustomerId extends UnivapayResponse {

  public static String metadataKey = "univapay-customer-id";

  @SerializedName("customer_id")
  private UUID univapayCustomerId;

  public UnivapayCustomerId(UUID univapayCustomerId) {
    this.univapayCustomerId = univapayCustomerId;
  }

  public UUID toUUID() {
    return this.univapayCustomerId;
  }

  @Override
  public String toString() {
    if (this.univapayCustomerId == null) {
      return null;
    }
    return this.univapayCustomerId.toString();
  }
}
