package com.univapay.sdk.types;

import java.util.UUID;

public class PaymentType {
  private UUID paymentTypeId;
  private PaymentTypeName typeName;
  private boolean active;

  public UUID getPaymentTypeID() {
    return paymentTypeId;
  }

  public PaymentTypeName typeName() {
    return typeName;
  }

  public boolean getActive() {
    return active;
  }
}
