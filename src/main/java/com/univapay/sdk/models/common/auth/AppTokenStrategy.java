package com.univapay.sdk.models.common.auth;

import com.univapay.sdk.types.AuthType;

/**
 * Authentication strategy that allows a merchant to make requests using an application token and an
 * optional secret.
 */
public class AppTokenStrategy implements AuthStrategy {
  private String appToken;
  private String secret;

  public AppTokenStrategy(String appToken, String secret) {
    this.appToken = appToken;
    this.secret = secret;
  }

  public AppTokenStrategy(String appToken) {
    this.appToken = appToken;
  }

  public String getAppToken() {
    return appToken;
  }

  public String getSecret() {
    return secret;
  }

  @Override
  public AuthHeader getAuthHeader() {
    if (appToken == null || appToken.isEmpty()) {
      return AuthHeader.unauthenticated();
    } else {
      if (secret == null || secret.isEmpty()) {
        return new AuthHeader(appToken, AuthType.APP_TOKEN);
      } else {
        return new AuthHeader(appToken + "|" + secret, AuthType.APP_TOKEN);
      }
    }
  }
}
