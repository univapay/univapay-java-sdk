package com.univapay.sdk.builders.cancel;

import com.univapay.sdk.models.common.CancelId;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.request.cancel.CancelCreateData;
import com.univapay.sdk.models.request.cancel.CancelPatchData;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.cancel.Cancel;
import com.univapay.sdk.types.CancelStatus;
import com.univapay.sdk.builders.ResourceMonitor;
import com.univapay.sdk.builders.ResourcePredicate;
import com.univapay.sdk.builders.cancel.AbstractCancelsBuilders.AbstractCreateCancelRequestBuilder;
import com.univapay.sdk.builders.cancel.AbstractCancelsBuilders.AbstractGetCancelRequestBuilder;
import com.univapay.sdk.builders.cancel.AbstractCancelsBuilders.AbstractListAllCancelsRequestBuilder;
import com.univapay.sdk.builders.cancel.AbstractCancelsBuilders.AbstractUpdateCancelRequestBuilder;
import com.univapay.sdk.resources.CancelResource;
import retrofit2.Call;
import retrofit2.Retrofit;

public abstract class CancelsBuilders {

  public static class GetCancelRequestBuilder
      extends AbstractGetCancelRequestBuilder<GetCancelRequestBuilder, CancelResource, Cancel> {

    public GetCancelRequestBuilder(
        Retrofit retrofit, StoreId storeId, ChargeId chargeId, CancelId cancelId) {
      super(retrofit, storeId, chargeId, cancelId);
    }

    @Override
    protected Call<Cancel> getRequest(CancelResource resource) {
      return resource.get(storeId, chargeId, cancelId, polling);
    }
  }

  public static class ListAllCancelsRequestBuilder
      extends AbstractListAllCancelsRequestBuilder<
          ListAllCancelsRequestBuilder, CancelResource, Cancel> {

    public ListAllCancelsRequestBuilder(Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
      super(retrofit, storeId, chargeId);
    }

    @Override
    protected Call<PaginatedList<Cancel>> getRequest(CancelResource resource) {
      return resource.list(storeId, chargeId, getLimit(), getCursorDirection(), getCursor());
    }
  }

  public static class CreateCancelRequestBuilder
      extends AbstractCreateCancelRequestBuilder<
          CreateCancelRequestBuilder, CancelResource, Cancel> {

    public CreateCancelRequestBuilder(Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
      super(retrofit, storeId, chargeId);
    }

    @Override
    protected Call<Cancel> getRequest(CancelResource resource) {
      return resource.create(storeId, chargeId, new CancelCreateData(metadata), idempotencyKey);
    }
  }

  public static class UpdateCancelRequestBuilder
      extends AbstractUpdateCancelRequestBuilder<
          UpdateCancelRequestBuilder, CancelResource, Cancel> {

    public UpdateCancelRequestBuilder(
        Retrofit retrofit, StoreId storeId, ChargeId chargeId, CancelId cancelId) {
      super(retrofit, storeId, chargeId, cancelId);
    }

    @Override
    protected Call<Cancel> getRequest(CancelResource resource) {
      return resource.update(
          storeId, chargeId, cancelId, new CancelPatchData(metadata), idempotencyKey);
    }
  }

  public static ResourceMonitor<Cancel> createRefundCompletionMonitor(
      Retrofit retrofit, StoreId storeId, ChargeId chargeId, CancelId cancelId) {
    return new ResourceMonitor<>(
        new CancelsBuilders.GetCancelRequestBuilder(retrofit, storeId, chargeId, cancelId)
            .withPolling(true),
        new ResourcePredicate<Cancel>() {
          @Override
          public boolean test(Cancel resource) {
            return resource.getCancelStatus() != CancelStatus.PENDING;
          }
        });
  }
}
