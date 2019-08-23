package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Gateway;

public class CardTransactionData extends PaymentTransactionData {
  private String cardHolderName;

  private CardBrand cardBrand;

  CardTransactionData(String cardHolderName, CardBrand cardBrand, Gateway gateway) {
    super(gateway);
    this.cardHolderName = cardHolderName;
    this.cardBrand = cardBrand;
  }

  public String getCardHolderName() {
    return cardHolderName;
  }

  public CardBrand getCardBrand() {
    return cardBrand;
  }
}
