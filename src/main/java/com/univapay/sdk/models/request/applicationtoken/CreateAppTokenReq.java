package com.univapay.sdk.models.request.applicationtoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.Domain;
import com.univapay.sdk.types.ProcessingMode;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class CreateAppTokenReq {
  @SerializedName("mode")
  private ProcessingMode mode;

  @SerializedName("domains")
  private List<Domain> domains;

  public CreateAppTokenReq(ProcessingMode mode, List<Domain> domains) {
    this.mode = mode;
    this.domains = domains;
  }
}
