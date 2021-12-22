package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.brand.PaidyBrand;

public class PaidyTransactionData extends PaymentTransactionData<PaidyBrand> {

  private final String cardholderEmailAddress;

  private final String cardholderPhoneNumber;

  PaidyTransactionData(
      String cardholderEmailAddress, String cardholderPhoneNumber, Gateway gateway, String brand) {
    super(gateway, brand);
    this.cardholderEmailAddress = cardholderEmailAddress;
    this.cardholderPhoneNumber = cardholderPhoneNumber;
  }

  public String getCardholderEmailAddress() {
    return cardholderEmailAddress;
  }

  public String getCardholderPhoneNumber() {
    return cardholderPhoneNumber;
  }

  public PaidyBrand getBrand() {
    return PaidyBrand.getInstanceByLiteralValueNullable(brand);
  }
}
