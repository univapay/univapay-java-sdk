package com.univapay.sdk.builders.batch_charge;

import com.univapay.sdk.builders.ResourceMonitor;
import com.univapay.sdk.builders.charge.ChargesBuilders;
import com.univapay.sdk.builders.charge.ChargesBuilders.CreateChargeRequestBuilder;
import com.univapay.sdk.builders.charge.ChargesBuilders.GetChargeRequestBuilder;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.charge.Charge;
import retrofit2.Retrofit;

public class BatchCreateCharge
    extends AbstractBatchCreateCharge<
    Charge,
    CreateChargeRequestBuilder,
    GetChargeRequestBuilder> {

  public BatchCreateCharge(Retrofit retrofit, int createMaxRetry, int statusCheckTimeout) {
    super(retrofit, createMaxRetry, statusCheckTimeout);
  }

  @Override
  public ResourceMonitor<Charge> createChargeCompletionMonitor(
      Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
    return ChargesBuilders.createChargeCompletionMonitor(retrofit, storeId, chargeId);
  }
}
