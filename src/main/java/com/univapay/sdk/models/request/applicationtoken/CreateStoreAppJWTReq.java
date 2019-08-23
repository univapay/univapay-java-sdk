package com.univapay.sdk.models.request.applicationtoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.Domain;
import com.univapay.sdk.types.ProcessingMode;
import java.util.List;

public class CreateStoreAppJWTReq {

  @SerializedName("mode")
  private ProcessingMode mode;

  @SerializedName("domains")
  private List<Domain> domains;

  public ProcessingMode getMode() {
    return mode;
  }

  public List<Domain> getDomains() {
    return domains;
  }

  public CreateStoreAppJWTReq(ProcessingMode mode) {
    this.mode = mode;
  }

  public CreateStoreAppJWTReq(ProcessingMode mode, List<Domain> domains) {
    this.mode = mode;
    this.domains = domains;
  }
}
