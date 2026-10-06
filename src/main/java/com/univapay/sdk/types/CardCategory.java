package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum CardCategory {
  @SerializedName("classic")
  CLASSIC,
  @SerializedName("corporate")
  CORPORATE,
  @SerializedName("prepaid")
  PREPAID,
  @SerializedName("black")
  BLACK,
  @SerializedName("centurion")
  CENTURION,
  @SerializedName("charge_card")
  CHARGE_CARD,
  @SerializedName("credit")
  CREDIT,
  @SerializedName("debit")
  DEBIT,
  @SerializedName("personal")
  PERSONAL,
  @SerializedName("signature")
  SIGNATURE,
  @SerializedName("standard")
  STANDARD,
  @SerializedName("gold")
  GOLD,
  @SerializedName("titanium")
  TITANIUM,
  @SerializedName("platinum")
  PLATINUM,
  @SerializedName("atm")
  ATM,
  @SerializedName("electron")
  ELECTRON,
  @SerializedName("maestro")
  MAESTRO,
  @SerializedName("world")
  WORLD,
  @SerializedName("business")
  BUSINESS,
  @SerializedName("infinite")
  INFINITE
}
