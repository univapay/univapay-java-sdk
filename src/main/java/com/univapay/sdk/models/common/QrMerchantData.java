package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.brand.QrMpmBrand;

public class QrMerchantData implements PaymentData {

  @SerializedName("brand")
  private QrMpmBrand brand;

  public QrMerchantData(QrMpmBrand brand) {
    this.brand = brand;
  }

  @Override
  public PaymentTypeName getPaymentType() {
    return PaymentTypeName.QR_MERCHANT;
  }

  public QrMpmBrand getBrand() {
    return brand;
  }
}
