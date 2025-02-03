package com.univapay.sdk.models.common.threeDs;

import com.google.gson.annotations.SerializedName;

public enum ChargeThreeDsMode {
  // Normal flow
  @SerializedName("normal")
  NORMAL,
  // If is able to execute, it will require
  @SerializedName("if_available")
  IF_AVAILABLE,
  // Will require and fail the charge if can't execute
  @SerializedName("require")
  REQUIRE,
  // Will execute even if the recurring token three ds is successful
  @SerializedName("force")
  FORCE,
  // Will skip the three ds, but require special permission to be used
  @SerializedName("skip")
  SKIP,
  // Will use the three ds data provided
  @SerializedName("provided")
  PROVIDED
}
