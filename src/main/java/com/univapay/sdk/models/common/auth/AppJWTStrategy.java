package com.univapay.sdk.models.common.auth;

import com.univapay.sdk.types.AuthType;

public class AppJWTStrategy implements AuthStrategy {

  private String token;
  private String secret;

  public AppJWTStrategy(String token, String secret) {
    this.token = token;
    this.secret = secret;
  }

  public AppJWTStrategy(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public String getSecret() {
    return secret;
  }

  @Override
  public AuthHeader getAuthHeader() {

    if (token == null || token.isEmpty()) {
      return AuthHeader.unauthenticated();
    } else {
      if (secret == null || secret.isEmpty()) {
        return new AuthHeader(token, AuthType.JWT);
      } else {
        return new AuthHeader(secret + "." + token, AuthType.JWT);
      }
    }
  }
}
