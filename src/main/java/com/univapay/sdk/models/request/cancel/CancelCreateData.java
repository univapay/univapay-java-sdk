package com.univapay.sdk.models.request.cancel;

import com.google.gson.annotations.SerializedName;
import java.util.Map;
import lombok.Getter;

@Getter
public class CancelCreateData {
  @SerializedName("metadata")
  private Map<String, Object> metadata;

  public CancelCreateData(Map<String, Object> metadata) {
    this.metadata = metadata;
  }
}
