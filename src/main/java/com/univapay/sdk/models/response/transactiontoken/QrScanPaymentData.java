package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.QRBrand;

public class QrScanPaymentData {
  @SerializedName("gateway")
  private Gateway gateway;

  @SerializedName("brand")
  private QRBrand brand;

  public QrScanPaymentData(Gateway gateway, QRBrand brand) {
    this.gateway = gateway;
    this.brand = brand;
  }

  public Gateway getGateway() {
    return gateway;
  }

  public QRBrand getBrand() {
    return brand;
  }
}
