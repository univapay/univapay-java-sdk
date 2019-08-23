package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.Konbini;

public class KonbiniTransactionData extends PaymentTransactionData {
  private String customerName;

  private Konbini convenienceStore;

  KonbiniTransactionData(String customerName, Konbini convenienceStore, Gateway gateway) {
    super(gateway);
    this.customerName = customerName;
    this.convenienceStore = convenienceStore;
  }

  public String getCustomerName() {
    return customerName;
  }

  public Konbini getConvenienceStore() {
    return convenienceStore;
  }
}
