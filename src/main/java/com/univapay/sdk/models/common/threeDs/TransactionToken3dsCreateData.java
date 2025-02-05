package com.univapay.sdk.models.common.threeDs;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionToken3dsCreateData {

  @SerializedName("enabled")
  private Boolean enabled;

  @SerializedName("redirect_endpoint")
  private String redirectEndpoint;
}
