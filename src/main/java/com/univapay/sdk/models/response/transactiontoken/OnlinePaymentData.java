package com.univapay.sdk.models.response.transactiontoken;

import com.univapay.sdk.models.common.CallMethod;
import com.univapay.sdk.types.brand.OnlineBrand;

/** This represents the online payment data for the TransactionToken response */
public class OnlinePaymentData {

  private final OnlineBrand brand;
  private final String issuerToken;
  private final CallMethod callMethod;

  public OnlinePaymentData(OnlineBrand brand, String issuerToken, CallMethod callMethod) {

    this.brand = brand;
    this.issuerToken = issuerToken;
    this.callMethod = callMethod;
  }

  public OnlineBrand getBrand() {
    return brand;
  }

  public String getIssuerToken() {
    return issuerToken;
  }

  public CallMethod getCallMethod() {
    return callMethod;
  }
}
