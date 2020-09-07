package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.PaymentTypeName;

public class OnlinePayment implements PaymentData {

  /** This defines which service will be used when creating the TransactionToken & Charge */
  @SerializedName("gateway")
  private final Gateway gateway;

  @Override
  public PaymentTypeName getPaymentType() {
    return PaymentTypeName.ONLINE;
  }

  public OnlinePayment(Gateway gateway) {
    this.gateway = gateway;
  }

  public Gateway getGateway() {
    return gateway;
  }
}
