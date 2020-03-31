package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.PaymentTypeName;

public class QrMerchantData implements PaymentData {

  @SerializedName("qr_image_url")
  private String qrImageUrl;

  @SerializedName("industry")
  private String industry;

  @SerializedName("gateway")
  private Gateway gateway;

  public QrMerchantData(String industry, Gateway gateway) {
    this.industry = industry;
    this.gateway = gateway;
  }

  @Override
  public PaymentTypeName getPaymentType() {
    return PaymentTypeName.QR_MERCHANT;
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
