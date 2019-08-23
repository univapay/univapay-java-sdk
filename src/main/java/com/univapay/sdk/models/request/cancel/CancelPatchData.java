package com.univapay.sdk.models.request.cancel;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.MetadataMap;

public class CancelPatchData {

  @SerializedName("metadata")
  private MetadataMap metadata;

  public CancelPatchData(MetadataMap metadata) {
    this.metadata = metadata;
  }

  public MetadataMap getMetadata() {
    return metadata;
  }
}
