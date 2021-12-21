package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Gateway;

public class CardTransactionData extends PaymentTransactionData<CardBrand> {

  private final String cardHolderName;
  private final CardBrand cardBrand;

  CardTransactionData(String cardHolderName, Gateway gateway, String brand, CardBrand cardBrand) {
    super(gateway, brand);
    this.cardHolderName = cardHolderName;
    this.cardBrand = cardBrand;
  }

  public String getCardHolderName() {
    return cardHolderName;
  }

  public CardBrand getCardBrand() {
    return getBrand();
  }

  @Override
  public CardBrand getBrand() {
    CardBrand brand = CardBrand.forBrandName(this.brand);

    // For backwards compat, try to read from the brand fields, if not, try the deprecated field
    if (brand == null || CardBrand.UNKNOWN.equals(brand)) {
      return cardBrand;
    } else {
      return brand;
    }
  }
}
