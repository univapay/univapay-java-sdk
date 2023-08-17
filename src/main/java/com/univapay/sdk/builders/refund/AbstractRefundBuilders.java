package com.univapay.sdk.builders.refund;

import com.univapay.sdk.builders.IdempotentRetrofitRequestBuilder;
import com.univapay.sdk.builders.Polling;
import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilderPaginated;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.RefundId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.request.refund.RefundCreateData;
import com.univapay.sdk.models.response.refund.Refund;
import com.univapay.sdk.types.RefundReason;
import java.math.BigInteger;
import java.util.Map;
import retrofit2.Retrofit;

public abstract class AbstractRefundBuilders {

  public abstract static class AbstractListRefundsRequestBuilder<
          B extends AbstractListRefundsRequestBuilder, R, M extends Refund>
      extends RetrofitRequestBuilderPaginated<M, R, B, RefundId> {

    protected StoreId storeId;
    protected ChargeId chargeId;
    protected String metadataSearch;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected ChargeId getChargeId() {
      return chargeId;
    }

    public B withMetadataSearch(String search) {
      this.metadataSearch = search;
      return (B) this;
    }

    public AbstractListRefundsRequestBuilder(
        Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
      super(retrofit);
      this.storeId = storeId;
      this.chargeId = chargeId;
    }
  }

  public abstract static class AbstractGetRefundRequestBuilder<
          B extends AbstractGetRefundRequestBuilder, R, M extends Refund>
      extends RetrofitRequestBuilder<M, R> implements Polling<B> {

    protected StoreId storeId;
    protected ChargeId chargeId;
    protected RefundId refundId;
    protected Boolean polling;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected ChargeId getChargeId() {
      return chargeId;
    }

    protected RefundId getRefundId() {
      return refundId;
    }

    public AbstractGetRefundRequestBuilder(
        Retrofit retrofit, StoreId storeId, ChargeId chargeId, RefundId refundId) {
      super(retrofit);
      this.storeId = storeId;
      this.chargeId = chargeId;
      this.refundId = refundId;
    }

    public B withPolling(boolean polling) {
      this.polling = polling;
      return (B) this;
    }
  }

  public abstract static class AbstractCreateRefundRequestBuilder<
          B extends AbstractCreateRefundRequestBuilder, R, M extends Refund>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    protected StoreId storeId;
    protected ChargeId chargeId;
    protected BigInteger amount;
    protected String currency;
    protected RefundReason reason;
    protected String message;
    protected Map<String, Object> metadata;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected ChargeId getChargeId() {
      return chargeId;
    }

    protected BigInteger getAmount() {
      return amount;
    }

    protected String getCurrency() {
      return currency;
    }

    protected RefundReason getReason() {
      return reason;
    }

    protected String getMessage() {
      return message;
    }

    protected Map<String, Object> getMetadata() {
      return metadata;
    }

    public AbstractCreateRefundRequestBuilder(
        Retrofit retrofit,
        StoreId storeId,
        ChargeId chargeId,
        BigInteger amount,
        String currency,
        RefundReason reason) {
      super(retrofit);
      this.storeId = storeId;
      this.chargeId = chargeId;
      this.amount = amount;
      this.currency = currency;
      this.reason = reason;
    }

    public B withMetadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return (B) this;
    }

    public B withMessage(String message) {
      this.message = message;
      return (B) this;
    }

    RefundCreateData getData() {
      return new RefundCreateData(amount, currency, reason, message, metadata);
    }
  }
}
