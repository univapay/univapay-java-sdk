package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum BankAccountType {
  @SerializedName("savings")
  SAVINGS,
  @SerializedName("checking")
  CHECKING
}
