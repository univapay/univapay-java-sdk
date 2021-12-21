package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.Konbini;

public class KonbiniTransactionData extends PaymentTransactionData<Konbini> {

  private final String customerName;
  private final Konbini convenienceStore;

  KonbiniTransactionData(
      String customerName, Gateway gateway, String brand, Konbini convenienceStore) {
    super(gateway, brand);
    this.customerName = customerName;
    this.convenienceStore = convenienceStore;
  }

  public String getCustomerName() {
    return customerName;
  }

  public Konbini getConvenienceStore() {
    return getBrand();
  }

  @Override
  public Konbini getBrand() {
    Konbini konbini = Konbini.getInstanceByLiteralValueNullable(brand);

    if (konbini == null) {
      return convenienceStore;
    } else {
      return konbini;
    }
  }
}
