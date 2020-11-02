package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.brand.QrCpmBrand;

public class QrScanPaymentData {

  @SerializedName("gateway")
  private final Gateway gateway;

  @SerializedName("brand")
  private final QrCpmBrand brand;

  public QrScanPaymentData(Gateway gateway, QrCpmBrand brand) {
    this.gateway = gateway;
    this.brand = brand;
  }

  public Gateway getGateway() {
    return gateway;
  }

  public QrCpmBrand getBrand() {
    return brand;
  }
}
