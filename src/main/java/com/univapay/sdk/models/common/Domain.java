package com.univapay.sdk.models.common;

import com.univapay.sdk.utils.TextUtils;

/**
 * This class ensures that domains associated to application tokens satisfy the requirements by the
 * API.
 */
public class Domain {

  private static String ANY_DOMAIN_WILDCARD = "*";

  public static Domain ANY = new Domain(ANY_DOMAIN_WILDCARD);

  private String domain;

  public Domain(String domain) {
    if ((domain.equals(ANY_DOMAIN_WILDCARD)) | TextUtils.URLRegexVerifier(domain)) {
      this.domain = domain;
    } else {
      throw new IllegalArgumentException(String.format("'%s' is not a valid domain", domain));
    }
  }

  public boolean isEmpty() {
    return domain.isEmpty();
  }

  public String asString() {
    return domain;
  }
}
