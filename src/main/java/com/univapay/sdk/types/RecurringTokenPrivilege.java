package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum RecurringTokenPrivilege {
  @SerializedName("none")
  NONE,
  @SerializedName("bounded")
  BOUNDED,
  @SerializedName("infinite")
  INFINITE
}
