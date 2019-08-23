package com.univapay.sdk.models.request.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.TemporaryTokenAliasMedia;
import com.univapay.sdk.types.TemporaryTokenAliasQRLogo;

public class TemporaryTokenAliasDisplayReq {

  @SerializedName("media")
  private TemporaryTokenAliasMedia media;

  @SerializedName("size")
  private Integer size;

  @SerializedName("logo")
  private TemporaryTokenAliasQRLogo logo;

  @SerializedName("color")
  private String color;

  public TemporaryTokenAliasDisplayReq(
      TemporaryTokenAliasMedia media, Integer size, TemporaryTokenAliasQRLogo logo, String color) {
    this.media = media;
    this.size = size;
    this.logo = logo;
    this.color = color;
  }

  public TemporaryTokenAliasMedia getMedia() {
    return media;
  }

  public Integer getSize() {
    return size;
  }

  public TemporaryTokenAliasQRLogo getLogo() {
    return logo;
  }

  public String getColor() {
    return color;
  }
}
