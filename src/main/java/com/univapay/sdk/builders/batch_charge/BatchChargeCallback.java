package com.univapay.sdk.builders.batch_charge;

import com.univapay.sdk.models.response.charge.Charge;

public interface BatchChargeCallback<T extends Charge> {

  void processResult(CreateChargeResult<T> chargeResult);
}
