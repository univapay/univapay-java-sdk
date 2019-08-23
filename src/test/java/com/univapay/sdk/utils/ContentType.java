package com.univapay.sdk.utils;

public enum ContentType {
  APPLICATION_JSON("application/json"),
  APPLICATION_IMAGE("application/image");

  private String contentTypeStr;

  ContentType(String contentTypeStr) {
    this.contentTypeStr = contentTypeStr;
  }

  public static final String header = "Content-Type";

  @Override
  public String toString() {
    return contentTypeStr;
  }
}
