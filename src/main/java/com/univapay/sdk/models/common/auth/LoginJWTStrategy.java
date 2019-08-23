package com.univapay.sdk.models.common.auth;

import com.univapay.sdk.types.AuthType;

/** Authentication strategy that allows a merchant to make requests using a JWT (Json Web Token). */
public class LoginJWTStrategy implements AuthStrategy {

  private String token;

  public LoginJWTStrategy(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  @Override
  public AuthHeader getAuthHeader() {

    if (token == null || token.isEmpty()) {
      return AuthHeader.unauthenticated();
    } else {
      return new AuthHeader(token, AuthType.JWT);
    }
  }

  public LoginJWTStrategy refresh(String refreshToken) {
    this.token = refreshToken;
    return this;
  }
}
