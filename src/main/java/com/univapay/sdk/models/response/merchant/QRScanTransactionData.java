package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.types.Gateway;

public class QRScanTransactionData extends PaymentTransactionData {
  private String cardholderEmailAddress;

  QRScanTransactionData(String cardholderEmailAddress, Gateway gateway) {
    super(gateway);
    this.cardholderEmailAddress = cardholderEmailAddress;
  }

  public String getCardholderEmailAddress() {
    return cardholderEmailAddress;
  }
}
