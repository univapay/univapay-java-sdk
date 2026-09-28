package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum CardType {
  @SerializedName("credit")
  CREDIT,
  @SerializedName("debit")
  DEBIT,
  @SerializedName("charge_card")
  CHARGE_CARD,
  @SerializedName("unknown")
  UNKNOWN
}
