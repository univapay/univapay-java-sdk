package com.univapay.sdk.models.response.merchant.transaction;

import com.univapay.sdk.models.response.merchant.PaymentTransactionData;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.brand.OnlineBrand;

public class OnlineTransactionData extends PaymentTransactionData<OnlineBrand> {

  private final String cardholderEmailAddress;

  public OnlineTransactionData(String cardholderEmailAddress, Gateway gateway, String brand) {
    super(gateway, brand);
    this.cardholderEmailAddress = cardholderEmailAddress;
  }

  @Override
  public OnlineBrand getBrand() {
    return OnlineBrand.getInstanceByLiteralValueNullable(brand);
  }

  public String getCardholderEmailAddress() {
    return cardholderEmailAddress;
  }
}
