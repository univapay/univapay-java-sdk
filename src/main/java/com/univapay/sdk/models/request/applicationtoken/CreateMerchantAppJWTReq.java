package com.univapay.sdk.models.request.applicationtoken;

import com.univapay.sdk.types.MerchantRole;
import java.util.Set;

public class CreateMerchantAppJWTReq {
  private Set<MerchantRole> roles;

  public Set<MerchantRole> getRoles() {
    return roles;
  }

  public CreateMerchantAppJWTReq(Set<MerchantRole> roles) {
    this.roles = roles;
  }
}
