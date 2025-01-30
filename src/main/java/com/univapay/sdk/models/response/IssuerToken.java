package com.univapay.sdk.models.response;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.CallMethod;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IssuerToken extends UnivapayResponse {

  @SerializedName("call_method")
  private final CallMethod callMethod;

  @SerializedName("issuer_token")
  private final String issuerToken;

  @SerializedName("content_type")
  private final String contentType;

  @SerializedName("payload")
  private Map<String, String> payload;
}
