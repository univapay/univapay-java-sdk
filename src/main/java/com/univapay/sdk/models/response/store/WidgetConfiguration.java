package com.univapay.sdk.models.response.store;

import com.google.gson.annotations.SerializedName;

public class WidgetConfiguration {

  @SerializedName("colors")
  private WidgetColors colors;

  public WidgetColors getColors() {
    return colors;
  }
}
