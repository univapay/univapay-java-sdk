package com.univapay.sdk.models.request.cancel;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class CancelCreateData {
  @SerializedName("metadata")
  private Map<String, String> metadata;

  public CancelCreateData(Map<String, String> metadata) {
    this.metadata = metadata;
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }
}
