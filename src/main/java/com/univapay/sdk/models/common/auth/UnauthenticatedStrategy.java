package com.univapay.sdk.models.common.auth;

/** Utility strategy that is used when making requests without providing credentials. */
public class UnauthenticatedStrategy implements AuthStrategy {

  @Override
  public AuthHeader getAuthHeader() {
    return AuthHeader.unauthenticated();
  }
}
