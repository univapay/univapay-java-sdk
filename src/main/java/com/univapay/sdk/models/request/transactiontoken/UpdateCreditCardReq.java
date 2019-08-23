package com.univapay.sdk.models.request.transactiontoken;

import com.google.gson.annotations.SerializedName;

public class UpdateCreditCardReq {
  @SerializedName("cvv")
  Integer cvv;

  public UpdateCreditCardReq(Integer cvv) {
    this.cvv = cvv;
  }
}
