package com.univapay.sdk.models.response.merchant;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.RefundId;
import com.univapay.sdk.types.RefundReason;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

public class RefundDetails {
  @SerializedName("refund_id")
  private UUID refundId;

  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("currency")
  private String currency;

  @SerializedName("amount_formatted")
  private BigDecimal amountFormatted;

  @SerializedName("reason")
  private RefundReason reason;

  public RefundId getRefundId() {
    return new RefundId(refundId);
  }

  public BigInteger getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAmountFormatted() {
    return amountFormatted;
  }

  public RefundReason getReason() {
    return reason;
  }
}
