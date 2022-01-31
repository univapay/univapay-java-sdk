package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;

public enum VirtualBankMatchAmount {
    @SerializedName("exact")
    Exact,
    @SerializedName("maximum")
    Maximum,
    @SerializedName("minimum")
    Minimum
  }