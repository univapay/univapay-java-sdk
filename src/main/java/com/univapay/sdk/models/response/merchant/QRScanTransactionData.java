package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.brand.QrCpmBrand;

public class QRScanTransactionData extends PaymentTransactionData<QrCpmBrand> {
  private final String cardholderEmailAddress;

  QRScanTransactionData(String cardholderEmailAddress, Gateway gateway, String brand) {
    super(gateway, brand);
    this.cardholderEmailAddress = cardholderEmailAddress;
  }

  public String getCardholderEmailAddress() {
    return cardholderEmailAddress;
  }

  @Override
  public QrCpmBrand getBrand() {
    return QrCpmBrand.getInstanceByLiteralValueNullable(brand);
  }
}
