package com.univapay.sdk.models.response;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Period;

public class CardLimit {
  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("currency")
  private String currency;

  @SerializedName("amount_formatted")
  private BigDecimal amountFormatted;

  @SerializedName("duration")
  private Period duration;

  public BigInteger getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAmountFormatted() {
    return amountFormatted;
  }

  public Period getDuration() {
    return duration;
  }
}
