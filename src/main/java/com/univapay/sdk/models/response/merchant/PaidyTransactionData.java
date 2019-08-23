package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.models.response.transactiontoken.PhoneNumber;

public class PaidyTransactionData extends PaymentTransactionData {
  private String cardholderEmailAddress;

  private PhoneNumber cardholderPhoneNumber;

  PaidyTransactionData(
      String cardholderEmailAddress, PhoneNumber cardholderPhoneNumber, Gateway gateway) {
    super(gateway);
    this.cardholderEmailAddress = cardholderEmailAddress;
    this.cardholderPhoneNumber = cardholderPhoneNumber;
  }

  public String getCardholderEmailAddress() {
    return cardholderEmailAddress;
  }

  public PhoneNumber getCardholderPhoneNumber() {
    return cardholderPhoneNumber;
  }
}
