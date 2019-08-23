package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.Gateway;

public class QrMerchantPaymentData {
  @SerializedName("qr_image_url")
  private String qrImageUrl;

  @SerializedName("industry")
  private String industry;

  @SerializedName("gateway")
  private Gateway gateway;

  public QrMerchantPaymentData(String qrImageUrl, String industry, Gateway gateway) {
    this.qrImageUrl = qrImageUrl;
    this.industry = industry;
    this.gateway = gateway;
  }

  public String getQrImageUrl() {
    return qrImageUrl;
  }

  public String getIndustry() {
    return industry;
  }

  public Gateway getGateway() {
    return gateway;
  }
}
