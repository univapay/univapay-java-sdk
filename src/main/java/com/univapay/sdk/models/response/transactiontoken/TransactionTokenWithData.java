package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class TransactionTokenWithData extends TransactionToken {

  @SerializedName("data")
  private PaymentData data;
}
