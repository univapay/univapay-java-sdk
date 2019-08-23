package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;

public class TransactionTokenWithData extends TransactionToken {

  @SerializedName("data")
  private PaymentData data;

  public PaymentData getData() {
    return data;
  }
}
