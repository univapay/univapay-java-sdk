package com.univapay.sdk.builders;

import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.charge.Charge;
import retrofit2.Retrofit;

public interface CreateChargeCompletionMonitor<M extends Charge> {

  ResourceMonitor<M> createChargeCompletionMonitor(
      Retrofit retrofit, StoreId storeId, ChargeId chargeId);
}
