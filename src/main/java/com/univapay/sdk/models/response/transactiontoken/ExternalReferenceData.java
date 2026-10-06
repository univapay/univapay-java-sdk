package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.Gateway;

/** Reference data needed to locate a transaction in an external card gateway. */
public class ExternalReferenceData {

  @SerializedName("gateway")
  private Gateway gateway;

  @SerializedName("gateway_transaction_id")
  private String gatewayTransactionId;

  public ExternalReferenceData(Gateway gateway, String gatewayTransactionId) {
    this.gateway = gateway;
    this.gatewayTransactionId = gatewayTransactionId;
  }

  public Gateway getGateway() {
    return gateway;
  }

  public String getGatewayTransactionId() {
    return gatewayTransactionId;
  }
}
