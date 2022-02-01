package com.univapay.sdk.models.request.store;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.CallMethod;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.OsType;
import java.math.BigInteger;
import javax.annotation.Nonnull;

public class GetDynamicBrandInfoForm {

  @SerializedName("amount")
  private RequestMoneyInformation requestedMoney;

  @SerializedName("call_method")
  private CallMethod callMethod;

  @SerializedName("os_type")
  private OsType osType;

  public RequestMoneyInformation getRequestedMoney() {
    return requestedMoney;
  }

  public void setRequestedMoney(RequestMoneyInformation requestedMoney) {
    this.requestedMoney = requestedMoney;
  }

  public CallMethod getCallMethod() {
    return callMethod;
  }

  public void setCallMethod(CallMethod callMethod) {
    this.callMethod = callMethod;
  }

  public OsType getOsType() {
    return osType;
  }

  public void setOsType(OsType osType) {
    this.osType = osType;
  }

  public static class RequestMoneyInformation {

    @SerializedName("value")
    private final BigInteger amount;

    @SerializedName("currency")
    private final String currency;

    public BigInteger getAmount() {
      return amount;
    }

    public String getCurrency() {
      return currency;
    }

    public RequestMoneyInformation(@Nonnull MoneyLike input) {
      this.amount = input.getAmount();
      this.currency = input.getCurrency();
    }
  }
}
