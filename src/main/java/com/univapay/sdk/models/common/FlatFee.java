package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import java.math.BigInteger;

public class FlatFee {

  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("currency")
  private String currency;

  public BigInteger getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public MoneyLike getMoney() {
    return new MoneyLike(amount, currency);
  }

  public FlatFee(BigInteger amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  @Override
  public boolean equals(Object obj) {
    Boolean equals;
    try {
      MoneyLike other = ((FlatFee) obj).getMoney();
      equals = other.equals(getMoney());
    } catch (Throwable t) {
      equals = false;
    }

    return equals;
  }
}
