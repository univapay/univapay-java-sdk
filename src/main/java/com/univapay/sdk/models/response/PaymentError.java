package com.univapay.sdk.models.response;

import com.google.gson.annotations.SerializedName;

public class PaymentError {
  @SerializedName("code")
  private Long code;

  @SerializedName("message")
  private String message;

  @SerializedName("detail")
  private String detail;

  public Long getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getDetail() {
    return detail;
  }
}
