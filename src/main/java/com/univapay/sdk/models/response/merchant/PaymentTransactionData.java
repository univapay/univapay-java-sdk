package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.types.Gateway;

public class PaymentTransactionData {
  protected Gateway gateway;

  protected PaymentTransactionData(Gateway gateway) {
    this.gateway = gateway;
  }

  public Gateway getGateway() {
    return gateway;
  }
}
