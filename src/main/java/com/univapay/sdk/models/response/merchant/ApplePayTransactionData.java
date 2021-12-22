package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.types.Gateway;

public class ApplePayTransactionData extends CardTransactionData {
  ApplePayTransactionData(String cardHolderName, Gateway gateway, String brand) {
    super(cardHolderName, gateway, brand);
  }
}
