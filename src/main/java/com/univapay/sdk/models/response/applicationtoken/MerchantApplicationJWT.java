package com.univapay.sdk.models.response.applicationtoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.MerchantRole;
import java.util.Set;

public class MerchantApplicationJWT extends ApplicationJWT {

  @SerializedName("roles")
  private Set<MerchantRole> roles;

  public Set<MerchantRole> getRoles() {
    return roles;
  }
}
