package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The {@code data} block of an external card transaction token response. {@code card} and {@code
 * billing} are only present once the external charge has succeeded.
 */
@Data
@AllArgsConstructor
public class ExternalCardPaymentData {
  @SerializedName("external")
  ExternalReferenceData external;

  @SerializedName("card")
  ExternalCardData card;

  @SerializedName("billing")
  TransactionTokenBillingData billing;
}
