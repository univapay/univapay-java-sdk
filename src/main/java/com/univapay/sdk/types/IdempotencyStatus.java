package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum IdempotencyStatus {
  @SerializedName("successfully_stored")
  SUCCESSFULLY_STORED,
  @SerializedName("not_stored")
  NOT_STORED,
  @SerializedName("retrieved_idempotent_response")
  RETRIEVED_IDEMPOTENT_RESPONSE,
  @SerializedName("conflicting_key")
  CONFLICTING_KEY,
  @SerializedName("error")
  ERROR,
  @SerializedName("no_status")
  NO_STATUS,
  @SerializedName("disabled")
  DISABLED
}
