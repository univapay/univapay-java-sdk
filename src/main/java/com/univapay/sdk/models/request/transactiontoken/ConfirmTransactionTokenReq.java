package com.univapay.sdk.models.request.transactiontoken;

import com.google.gson.annotations.SerializedName;

public class ConfirmTransactionTokenReq {

  @SerializedName("confirmation_code")
  private String confirmationCode;

  public ConfirmTransactionTokenReq(String confirmationCode) {
    this.confirmationCode = confirmationCode;
  }
}
