package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum CardSubBrand {
  @SerializedName("none")
  NONE,
  @SerializedName("visa_electron")
  VISA_ELECTRON,
  @SerializedName("dankort")
  DANKORT,
  @SerializedName("diners_club_carte_blanche")
  DINERS_CLUB_CARTE_BLANCHE,
  @SerializedName("diners_club_international")
  DINERS_CLUB_INTERNATIONAL,
  @SerializedName("diners_club_us_canada")
  DINERS_CLUB_US_CANADA
}
