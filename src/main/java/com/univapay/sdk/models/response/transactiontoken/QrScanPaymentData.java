package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.brand.QrCpmBrand;
import lombok.Data;

@Data
public class QrScanPaymentData {

  @SerializedName("brand")
  private final QrCpmBrand brand;

  public QrCpmBrand getBrand() {
    return brand;
  }
}
