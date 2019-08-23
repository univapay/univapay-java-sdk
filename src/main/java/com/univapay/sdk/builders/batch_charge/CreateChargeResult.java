package com.univapay.sdk.builders.batch_charge;

import com.univapay.sdk.models.response.charge.Charge;

public class CreateChargeResult<M extends Charge> {
  public M charge;
  public Exception createChargeException;
  public Exception statusCheckException;

  CreateChargeResult(M charge) {
    this.charge = charge;
  }

  CreateChargeResult(Exception createChargeException) {
    this.createChargeException = createChargeException;
  }
}
