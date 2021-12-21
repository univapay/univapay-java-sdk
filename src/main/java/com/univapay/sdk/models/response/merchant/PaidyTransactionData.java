package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.models.response.transactiontoken.PhoneNumber;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.brand.PaidyBrand;

public class PaidyTransactionData extends PaymentTransactionData<PaidyBrand> {

  private final String cardholderEmailAddress;

  private final PhoneNumber cardholderPhoneNumber;

  PaidyTransactionData(
      String cardholderEmailAddress,
      PhoneNumber cardholderPhoneNumber,
      Gateway gateway,
      String brand) {
    super(gateway, brand);
    this.cardholderEmailAddress = cardholderEmailAddress;
    this.cardholderPhoneNumber = cardholderPhoneNumber;
  }

  public String getCardholderEmailAddress() {
    return cardholderEmailAddress;
  }

  public PhoneNumber getCardholderPhoneNumber() {
    return cardholderPhoneNumber;
  }

  public PaidyBrand getBrand() {
    return PaidyBrand.getInstanceByLiteralValueNullable(brand);
  }
}
