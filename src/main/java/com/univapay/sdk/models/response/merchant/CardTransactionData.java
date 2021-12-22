package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Gateway;

public class CardTransactionData extends PaymentTransactionData<CardBrand> {

  private final String cardHolderName;

  CardTransactionData(String cardHolderName, Gateway gateway, String brand) {
    super(gateway, brand);
    this.cardHolderName = cardHolderName;
  }

  public String getCardHolderName() {
    return cardHolderName;
  }

  public CardBrand getCardBrand() {
    return getBrand();
  }

  @Override
  public CardBrand getBrand() {
    return CardBrand.forBrandName(this.brand);
  }
}
