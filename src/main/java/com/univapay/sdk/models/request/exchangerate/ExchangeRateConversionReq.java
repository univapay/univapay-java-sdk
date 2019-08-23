package com.univapay.sdk.models.request.exchangerate;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.MoneyLike;
import java.math.BigInteger;

public class ExchangeRateConversionReq {
  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("currency")
  private String currency;

  @SerializedName("to")
  private String to;

  public BigInteger getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public String getTo() {
    return to;
  }

  public ExchangeRateConversionReq(MoneyLike moneyToConvert, String targetCurrency) {
    this.amount = moneyToConvert.getAmount();
    this.currency = moneyToConvert.getCurrency();
    this.to = targetCurrency;
  }
}
