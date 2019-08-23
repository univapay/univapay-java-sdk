package com.univapay.sdk.models.response.authentication;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.MerchantId;
import com.univapay.sdk.models.common.auth.LoginJWTStrategy;
import com.univapay.sdk.models.common.auth.LoginTokenStrategy;
import com.univapay.sdk.models.response.UnivapayResponse;
import java.util.UUID;

public class LoginTokenInfo extends UnivapayResponse {
  @SerializedName("jwt")
  private LoginJWTStrategy jwt;

  @SerializedName("token")
  private String token;

  @SerializedName("merchant_id")
  private UUID merchantId;

  public LoginJWTStrategy getJWTAuthStrategy() {
    return jwt;
  }

  public String getToken() {
    return token;
  }

  public LoginTokenStrategy getLoginTokenAuthStrategy() {
    return new LoginTokenStrategy(token);
  }

  public MerchantId getMerchantId() {
    return new MerchantId(merchantId);
  }
}
