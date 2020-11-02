package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.brand.OnlineBrand;

public class OnlinePayment implements PaymentData {

  /** This defines which service will be used when creating the TransactionToken & Charge */
  @SerializedName("brand")
  private final OnlineBrand brand;

  @Override
  public PaymentTypeName getPaymentType() {
    return PaymentTypeName.ONLINE;
  }

  public OnlinePayment(OnlineBrand brand) {
    this.brand = brand;
  }

  public OnlineBrand getBrand() {
    return brand;
  }
}
