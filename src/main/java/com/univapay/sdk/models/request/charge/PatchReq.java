package com.univapay.sdk.models.request.charge;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

@SuppressWarnings("FieldCanBeLocal")
public class PatchReq {
  @SerializedName("metadata")
  private Map<String, Object> metadata;

  public PatchReq(Map<String, Object> metadata) {
    this.metadata = metadata;
  }
}
