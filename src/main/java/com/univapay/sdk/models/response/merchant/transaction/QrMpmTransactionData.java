package com.univapay.sdk.models.response.merchant.transaction;

import com.univapay.sdk.models.response.merchant.PaymentTransactionData;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.brand.QrMpmBrand;

public class QrMpmTransactionData extends PaymentTransactionData<QrMpmBrand> {

  private final String cardholderEmailAddress;

  public QrMpmTransactionData(String cardholderEmailAddress, Gateway gateway, String brand) {
    super(gateway, brand);
    this.cardholderEmailAddress = cardholderEmailAddress;
  }

  @Override
  public QrMpmBrand getBrand() {
    return QrMpmBrand.getInstanceByLiteralValueNullable(brand);
  }

  public String getCardholderEmailAddress() {
    return cardholderEmailAddress;
  }
}
