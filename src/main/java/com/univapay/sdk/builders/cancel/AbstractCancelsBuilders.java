package com.univapay.sdk.builders.cancel;

import com.univapay.sdk.builders.IdempotentRetrofitRequestBuilder;
import com.univapay.sdk.builders.Polling;
import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilderPaginated;
import com.univapay.sdk.models.common.CancelId;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.cancel.Cancel;
import com.univapay.sdk.types.CancelStatus;
import java.util.Map;
import retrofit2.Retrofit;

public abstract class AbstractCancelsBuilders {

  public abstract static class AbstractGetCancelRequestBuilder<
          B extends AbstractGetCancelRequestBuilder, R, M extends Cancel>
      extends RetrofitRequestBuilder<M, R> implements Polling<B> {
    protected StoreId storeId;
    protected ChargeId chargeId;
    protected CancelId cancelId;
    protected Boolean polling;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected ChargeId getChargeId() {
      return chargeId;
    }

    protected CancelId getCancelId() {
      return cancelId;
    }

    public AbstractGetCancelRequestBuilder(
        Retrofit retrofit, StoreId storeId, ChargeId chargeId, CancelId cancelId) {
      super(retrofit);
      this.storeId = storeId;
      this.chargeId = chargeId;
      this.cancelId = cancelId;
    }

    @Override
    public B withPolling(boolean polling) {
      this.polling = polling;
      return (B) this;
    }
  }

  public abstract static class AbstractListAllCancelsRequestBuilder<
          B extends AbstractListAllCancelsRequestBuilder, R, M extends Cancel>
      extends RetrofitRequestBuilderPaginated<M, R, B, CancelId> {
    protected StoreId storeId;
    protected ChargeId chargeId;

    public AbstractListAllCancelsRequestBuilder(
        Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
      super(retrofit);
      this.storeId = storeId;
      this.chargeId = chargeId;
    }
  }

  public abstract static class AbstractCreateCancelRequestBuilder<
          B extends AbstractCreateCancelRequestBuilder, R, M extends Cancel>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {
    protected StoreId storeId;
    protected ChargeId chargeId;
    protected Map<String, Object> metadata;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected ChargeId getChargeId() {
      return chargeId;
    }

    protected Map<String, Object> getMetadata() {
      return metadata;
    }

    public AbstractCreateCancelRequestBuilder(
        Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
      super(retrofit);
      this.storeId = storeId;
      this.chargeId = chargeId;
    }

    public B withMetadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return (B) this;
    }
  }

  public abstract static class AbstractUpdateCancelRequestBuilder<
          B extends AbstractUpdateCancelRequestBuilder, R, M extends Cancel>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {
    protected StoreId storeId;
    protected ChargeId chargeId;
    protected CancelId cancelId;
    protected Map<String, Object> metadata;
    protected CancelStatus status;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected ChargeId getChargeId() {
      return chargeId;
    }

    protected CancelId getCancelId() {
      return cancelId;
    }

    public AbstractUpdateCancelRequestBuilder(
        Retrofit retrofit, StoreId storeId, ChargeId chargeId, CancelId cancelId) {
      super(retrofit);
      this.storeId = storeId;
      this.chargeId = chargeId;
      this.cancelId = cancelId;
    }

    public B withMetadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return (B) this;
    }
  }
}
