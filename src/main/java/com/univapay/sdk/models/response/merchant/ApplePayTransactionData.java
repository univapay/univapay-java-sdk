package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Gateway;

public class ApplePayTransactionData extends CardTransactionData {
  ApplePayTransactionData(String cardHolderName, CardBrand cardBrand, Gateway gateway) {
    super(cardHolderName, cardBrand, gateway);
  }
}
