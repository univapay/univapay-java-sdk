package com.univapay.sdk.types;

public enum AuthType {
  LOGIN_TOKEN("Token"),
  APP_TOKEN("ApplicationToken"),
  NO_AUTH_HEADER(""),
  JWT("Bearer");

  private final String prefix;

  public String getPrefix() {
    return prefix;
  }

  AuthType(String prefix) {
    this.prefix = prefix;
  }
}
