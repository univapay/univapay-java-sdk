package com.univapay.sdk.models.common.auth;

import com.univapay.sdk.types.AuthType;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthHeader {

  private String value;
  private AuthType authType;

  public AuthHeader(String value, AuthType authType) {
    this.value = value;
    this.authType = authType;
  }

  private AuthHeader() {
    this.authType = AuthType.NO_AUTH_HEADER;
  }

  public String getValue() {
    if (authType == AuthType.NO_AUTH_HEADER) {
      return "";
    } else {
      return authType.getPrefix() + " " + value;
    }
  }

  public String getTokenValue() {
    return value;
  }

  public AuthType getAuthType() {
    return authType;
  }

  public static AuthHeader unauthenticated() {
    return new AuthHeader();
  }

  public static Pattern jwtHeaderPattern = Pattern.compile("\\s*Bearer ([a-zA-Z0-9\\-_.]+)");

  /**
   * @param headerValue For example: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
   * @return the token (for example: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9)
   * @throws IOException if the auth header is not properly formatted
   */
  public static String parseValueFromJWTHeader(String headerValue) throws IOException {
    Matcher matcher = jwtHeaderPattern.matcher(headerValue);
    if (matcher.matches()) {
      return matcher.group(1);
    } else
      throw new IOException(
          "Authentication header does not have the correct format for JWT authentication");
  }
}
