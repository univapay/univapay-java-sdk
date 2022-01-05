package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.types.Gateway;

public abstract class PaymentTransactionData<T> {

  protected final Gateway gateway;

  /** Return the String representation of a Brand */
  protected final String brand;

  protected PaymentTransactionData(Gateway gateway, String brand) {
    this.gateway = gateway;
    this.brand = brand;
  }

  /**
   * Returns the gateway that the charge was processed with Planned removal, will return null at
   * some point, avoid using it, refer to the Brand field
   *
   * @return the gateway, can be null
   */
  @Deprecated
  public Gateway getGateway() {
    return gateway;
  }

  public abstract T getBrand();
}
