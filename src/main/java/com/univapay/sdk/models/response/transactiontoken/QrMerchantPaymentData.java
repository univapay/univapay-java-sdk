package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.brand.QrMpmBrand;

public class QrMerchantPaymentData {
  @SerializedName("qr_image_url")
  private String qrImageUrl;

  @SerializedName("brand")
  private QrMpmBrand brand;

  public QrMerchantPaymentData(String qrImageUrl, QrMpmBrand brand) {
    this.qrImageUrl = qrImageUrl;

    this.brand = brand;
  }

  public String getQrImageUrl() {
    return qrImageUrl;
  }

  public QrMpmBrand getBrand() {
    return brand;
  }
}
