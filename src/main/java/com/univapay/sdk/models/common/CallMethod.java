package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;

/** Representation of the method that is used to consume the issuerToken */
public enum CallMethod {

  /** The issuer token is a HTTP endpoint, invoked with GET */
  @SerializedName("http_get")
  HTTP_GET,
  /** The issuer token is a HTTP endpoint, invoked with POST */
  @SerializedName("http_post")
  HTTP_POST,
  /** The issuer token should be forwarded to the service's provided SDK */
  @SerializedName("sdk")
  SDK
}
