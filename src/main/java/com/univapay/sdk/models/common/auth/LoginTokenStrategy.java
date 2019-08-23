package com.univapay.sdk.models.common.auth;

import com.univapay.sdk.types.AuthType;

/** Authentication strategy that allows a merchant to make requests using a login token. */
public class LoginTokenStrategy implements AuthStrategy {

  private String loginToken;

  public LoginTokenStrategy(String loginToken) {
    this.loginToken = loginToken;
  }

  public String getLoginToken() {
    return loginToken;
  }

  @Override
  public AuthHeader getAuthHeader() {
    if (loginToken == null || loginToken.isEmpty()) {
      return AuthHeader.unauthenticated();
    } else {
      return new AuthHeader(loginToken, AuthType.LOGIN_TOKEN);
    }
  }
}
