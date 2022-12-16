package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.charge.CvvAuthorization;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardPaymentData {
  @SerializedName("card")
  TransactionTokenCardData card;

  @SerializedName("billing")
  TransactionTokenBillingData billing;

  @SerializedName("cvv_authorize")
  CvvAuthorization cvvAuthorization;
}
