package com.univapay.sdk.models.request.refund;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.types.RefundReason;
import java.math.BigInteger;

@SuppressWarnings("FieldCanBeLocal")
public class RefundCreateData {
  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("currency")
  private String currency;

  @SerializedName("reason")
  private RefundReason reason;

  @SerializedName("message")
  private String message;

  @SerializedName("metadata")
  private MetadataMap metadata;

  public RefundCreateData(
      BigInteger amount,
      String currency,
      RefundReason reason,
      String message,
      MetadataMap metadata) {
    this.amount = amount;
    this.currency = currency;
    this.reason = reason;
    this.message = message;
    this.metadata = metadata;
  }
}
