package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.Konbini;

public class KonbiniTransactionData extends PaymentTransactionData<Konbini> {

  private final String customerName;

  KonbiniTransactionData(String customerName, Gateway gateway, String brand) {
    super(gateway, brand);
    this.customerName = customerName;
  }

  public String getCustomerName() {
    return customerName;
  }

  public Konbini getConvenienceStore() {
    return getBrand();
  }

  @Override
  public Konbini getBrand() {
    return Konbini.getInstanceByLiteralValueNullable(brand);
  }
}
