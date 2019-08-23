package com.univapay.sdk.models.request.charge;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.MoneyLike;
import java.math.BigInteger;

@SuppressWarnings("FieldCanBeLocal")
public class CaptureReq {
  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("currency")
  private String currency;

  public CaptureReq(MoneyLike money) {
    this.amount = money.getAmount();
    this.currency = money.getCurrency();
  }
}
