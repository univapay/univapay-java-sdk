package com.univapay.sdk.models.response.transactiontoken;

import com.univapay.sdk.models.common.CallMethod;
import com.univapay.sdk.types.Gateway;

/** This represents the online payment data for the TransactionToken response */
public class OnlinePaymentData {

  private final Gateway gateway;
  private final String issuerToken;
  private final CallMethod callMethod;

  public OnlinePaymentData(Gateway gateway, String issuerToken, CallMethod callMethod) {
    this.gateway = gateway;
    this.issuerToken = issuerToken;
    this.callMethod = callMethod;
  }

  public Gateway getGateway() {
    return gateway;
  }

  public String getIssuerToken() {
    return issuerToken;
  }

  public CallMethod getCallMethod() {
    return callMethod;
  }
}
