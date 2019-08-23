package com.univapay.sdk.models.response.transactiontoken;

import java.util.regex.Pattern;

public class TokenAliasKey {

  private String key;

  public String getKey() {
    return key;
  }

  public TokenAliasKey(String key) {
    this.key = key;
  }

  @Override
  public boolean equals(Object obj) {
    Boolean equals;
    try {
      TokenAliasKey other = (TokenAliasKey) obj;
      equals = other.getKey().equals(this.key);
    } catch (Throwable t) {
      equals = false;
    }
    return equals;
  }

  @Override
  public String toString() {
    return this.key;
  }

  private static String CANT_PARSE_AS_ALIAS =
      "The data is not formatted as a temporary token alias";

  private static Pattern regex = Pattern.compile("[0-9]+");

  public static TokenAliasKey parse(String aliasObjectString) {
    if (isValidAlias(aliasObjectString)) {
      return new TokenAliasKey(aliasObjectString);
    } else throw new IllegalArgumentException(CANT_PARSE_AS_ALIAS);
  }

  private static boolean isValidAlias(String alias) {
    return regex.matcher(alias).matches();
  }
}
