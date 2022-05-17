package com.univapay.sdk.utils.mockcontent;

public class IssuerTokensFakeRR {
  public static final String getIssuerTokenFakeResponse =
      JsonLoader.loadJson("responses/issuerToken/get-issuer-token.json");

  public static final String getIssuerTokenForAlipayPlusAppResponse =
      JsonLoader.loadJson("responses/issuerToken/get-issuer-token-alipay-plus-app.json");
}
