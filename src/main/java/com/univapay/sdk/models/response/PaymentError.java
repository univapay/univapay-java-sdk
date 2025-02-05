package com.univapay.sdk.models.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class PaymentError {
  @SerializedName("code")
  private Long code;

  @SerializedName("message")
  private String message;

  @SerializedName("detail")
  private String detail;
}
