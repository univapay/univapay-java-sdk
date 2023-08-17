package com.univapay.sdk.models.request.cancel;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class CancelPatchData {

  @SerializedName("metadata")
  private Map<String, Object> metadata;

  public CancelPatchData(Map<String, Object> metadata) {
    this.metadata = metadata;
  }

  public Map<String, Object> getMetadata() {
    return metadata;
  }
}
