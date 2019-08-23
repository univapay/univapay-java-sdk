package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum BankAccountStatus {
  @SerializedName("new")
  NEW,
  @SerializedName("verified")
  VERIFIED,
  @SerializedName("unable_to_verify")
  UNABLE_TO_VERIFY,
  @SerializedName("errored")
  ERRORED
}
