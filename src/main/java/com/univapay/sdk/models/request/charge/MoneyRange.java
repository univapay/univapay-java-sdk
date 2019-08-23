package com.univapay.sdk.models.request.charge;

import java.math.BigInteger;

public class MoneyRange {

  private BigInteger minimumAmount;

  private BigInteger maximumAmount;

  public MoneyRange() {}

  public MoneyRange(BigInteger minimumAmount, BigInteger maximumAmount) {
    this.minimumAmount = minimumAmount;
    this.maximumAmount = maximumAmount;
  }

  public MoneyRange withMinimumAmount(BigInteger minimumAmount) {
    this.minimumAmount = minimumAmount;
    return this;
  }

  public MoneyRange withMaximumAmount(BigInteger maximumAmount) {
    this.maximumAmount = maximumAmount;
    return this;
  }

  public BigInteger getMinimumAmount() {
    return minimumAmount;
  }

  public BigInteger getMaximumAmount() {
    return maximumAmount;
  }
}
