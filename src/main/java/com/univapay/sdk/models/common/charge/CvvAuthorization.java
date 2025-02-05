package com.univapay.sdk.models.common.charge;

import com.google.gson.annotations.SerializedName;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

/** This models the actual CVV Authorization response */
@Data
@AllArgsConstructor
public class CvvAuthorization {

  @SerializedName("enabled")
  private Boolean enabled;

  @SerializedName("charge_id")
  private UUID chargeId;

  @SerializedName("status")
  private CvvAuthorizationStatus status;
}
