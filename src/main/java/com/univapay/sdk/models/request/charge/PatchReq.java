package com.univapay.sdk.models.request.charge;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.MetadataMap;

@SuppressWarnings("FieldCanBeLocal")
public class PatchReq {
  @SerializedName("metadata")
  private MetadataMap metadata;

  public PatchReq(MetadataMap metadata) {
    this.metadata = metadata;
  }
}
