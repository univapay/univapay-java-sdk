package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.types.PaymentTypeName;

public class QrScanData implements PaymentData {
  @SerializedName("scanned_qr")
  private String scannedQr;

  public String getScannedQr() {
    return scannedQr;
  }

  public QrScanData(String scannedQr) {
    this.scannedQr = scannedQr;
  }

  @Override
  public PaymentTypeName getPaymentType() {
    return PaymentTypeName.QR_SCAN;
  }
}
