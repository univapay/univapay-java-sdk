package com.univapay.sdk.models.response;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.CallMethod;

public class IssuerToken extends UnivapayResponse {

  @SerializedName("call_method")
  private final CallMethod callMethod;

  @SerializedName("issuer_token")
  private final String issuerToken;

  public IssuerToken(CallMethod callMethod, String issuerToken) {
    this.callMethod = callMethod;
    this.issuerToken = issuerToken;
  }

  public String getIssuerToken() {
    return issuerToken;
  }

  public CallMethod getCallMethod() {
    return callMethod;
  }
}
