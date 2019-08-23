package com.univapay.sdk.models.request.store;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.Domain;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class UpdateAppTokenReq {
  @SerializedName("domains")
  private List<String> domains;

  public UpdateAppTokenReq(List<Domain> domains) {
    List<String> strDomains = new ArrayList<>();
    for (Domain domain : domains) {
      strDomains.add(domain.asString());
    }
    this.domains = strDomains;
  }
}
