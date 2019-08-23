package com.univapay.sdk.models.response.store;

import com.google.gson.annotations.SerializedName;

public class WidgetColors {
  @SerializedName("main_background")
  private String mainBackground;

  @SerializedName("secondary_background")
  private String secondaryBackground;

  @SerializedName("main_color")
  private String mainColor;

  @SerializedName("main_text")
  private String mainText;

  @SerializedName("primary_text")
  private String primaryText;

  @SerializedName("secondary_text")
  private String secondaryText;

  @SerializedName("base_text")
  private String baseText;

  public String getMainBackground() {
    return mainBackground;
  }

  public String getSecondaryBackground() {
    return secondaryBackground;
  }

  public String getMainColor() {
    return mainColor;
  }

  public String getMainText() {
    return mainText;
  }

  public String getPrimaryText() {
    return primaryText;
  }

  public String getSecondaryText() {
    return secondaryText;
  }

  public String getBaseText() {
    return baseText;
  }

  public WidgetColors(
      String mainBackground,
      String secondaryBackground,
      String mainColor,
      String mainText,
      String primaryText,
      String secondaryText,
      String baseText) {
    this.mainBackground = mainBackground;
    this.secondaryBackground = secondaryBackground;
    this.mainColor = mainColor;
    this.mainText = mainText;
    this.primaryText = primaryText;
    this.secondaryText = secondaryText;
    this.baseText = baseText;
  }
}
