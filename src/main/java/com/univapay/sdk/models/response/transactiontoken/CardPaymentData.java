package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;

public class CardPaymentData {
  @SerializedName("card")
  TransactionTokenCardData card;

  @SerializedName("billing")
  TransactionTokenBillingData billing;

  public TransactionTokenCardData getCard() {
    return card;
  }

  public TransactionTokenBillingData getBilling() {
    return billing;
  }

  public CardPaymentData(TransactionTokenCardData card, TransactionTokenBillingData billing) {
    this.card = card;
    this.billing = billing;
  }
}
