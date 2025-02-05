package com.univapay.sdk.models.common.threeDs;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.RedirectId;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ChargeThreeDsData {

  @SerializedName("mode")
  private ChargeThreeDsMode mode;

  @SerializedName("redirect_endpoint")
  private String redirectEndpoint;

  @SerializedName("redirect_id")
  private UUID redirectId;

  public RedirectId getRedirectId() {
    if (redirectId != null) {
      return new RedirectId(redirectId);
    } else {
      return null;
    }
  }
}
