package com.univapay.sdk.builders.charge;

import com.univapay.sdk.builders.DescriptorRetry;
import com.univapay.sdk.builders.IdempotentRetrofitRequestBuilder;
import com.univapay.sdk.builders.Polling;
import com.univapay.sdk.builders.Request;
import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilderPaginated;
import com.univapay.sdk.builders.RetrofitRequestCaller;
import com.univapay.sdk.builders.RetryUtils;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.common.threeDs.ChargeThreeDsCreateData;
import com.univapay.sdk.models.common.threeDs.ChargeThreeDsMode;
import com.univapay.sdk.models.request.charge.CardChargeSearch;
import com.univapay.sdk.models.response.charge.Charge;
import java.time.OffsetDateTime;
import java.util.Map;
import lombok.Getter;
import retrofit2.Retrofit;

public abstract class AbstractChargesBuilders {

  public abstract static class AbstractListChargesRequestBuilder<
          B extends AbstractListChargesRequestBuilder, R, M extends Charge>
      extends RetrofitRequestBuilderPaginated<M, R, B, ChargeId> {

    protected StoreId storeId;
    protected CardChargeSearch propertySearch = new CardChargeSearch();
    protected String metadataSearch;

    protected StoreId getStoreId() {
      return storeId;
    }

    public AbstractListChargesRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    public AbstractListChargesRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit);
      this.storeId = storeId;
    }

    public B withCardChargeSearch(CardChargeSearch search) {
      this.propertySearch = search;
      return (B) this;
    }

    public B withMetadataSearch(String search) {
      this.metadataSearch = search;
      return (B) this;
    }
  }

  public abstract static class AbstractGetChargeRequestBuilder<
          B extends AbstractGetChargeRequestBuilder, R, M extends Charge>
      extends RetrofitRequestBuilder<M, R> implements Polling<B> {

    protected StoreId storeId;
    protected ChargeId chargeId;
    protected Boolean polling;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected ChargeId getChargeId() {
      return chargeId;
    }

    public AbstractGetChargeRequestBuilder(Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
      super(retrofit);
      this.storeId = storeId;
      this.chargeId = chargeId;
    }

    public B withPolling(boolean polling) {
      this.polling = polling;
      return (B) this;
    }
  }

  public abstract static class AbstractCreateChargeRequestBuilder<
          B extends AbstractCreateChargeRequestBuilder, R, M extends Charge>
      extends IdempotentRetrofitRequestBuilder<M, R, B> implements DescriptorRetry<B, M> {

    protected TransactionTokenId transactionTokenId;
    @Getter protected MoneyLike money;
    @Getter protected Boolean onlyDirectCurrency;
    @Getter protected OffsetDateTime captureAt;
    @Getter protected Boolean capture;
    protected Boolean ignoreDescriptorOnError = false;
    @Getter protected String descriptor;
    protected Map<String, Object> metadata;
    protected ChargeThreeDsCreateData threeDs;

    protected TransactionTokenId getTransactionTokenId() {
      return transactionTokenId;
    }

    protected Map<String, Object> getMetadata() {
      return metadata;
    }

    public AbstractCreateChargeRequestBuilder(
        Retrofit retrofit,
        TransactionTokenId transactionTokenId,
        MoneyLike money,
        Boolean capture) {
      super(retrofit);
      this.transactionTokenId = transactionTokenId;
      this.money = money;
      this.capture = capture;
    }

    public B withOnlyDirectCurrency(Boolean onlyDirectCurrency) {
      this.onlyDirectCurrency = onlyDirectCurrency;
      return (B) this;
    }

    public B withCaptureAt(OffsetDateTime captureAt) {
      this.captureAt = captureAt;
      return (B) this;
    }

    public B withDescriptor(String descriptor) {
      this.descriptor = descriptor;
      return (B) this;
    }

    public B withDescriptor(String descriptor, Boolean ignoreDescriptorOnError) {
      this.descriptor = descriptor;
      this.ignoreDescriptorOnError = ignoreDescriptorOnError;
      return (B) this;
    }

    public B withMetadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return (B) this;
    }

    public B withThreeDs(String redirectEndpoint) {
      this.threeDs =
          new ChargeThreeDsCreateData(null, redirectEndpoint, null, null, null, null, null, null);
      return (B) this;
    }

    public B withThreeDs(ChargeThreeDsMode mode, String redirectEndpoint) {
      this.threeDs =
          new ChargeThreeDsCreateData(mode, redirectEndpoint, null, null, null, null, null, null);
      return (B) this;
    }

    public B withThreeDs(ChargeThreeDsMode mode) {
      this.threeDs = new ChargeThreeDsCreateData(mode, null, null, null, null, null, null, null);
      return (B) this;
    }

    public B withProvidedThreeDs(
        String authenticationValue,
        String eci,
        String dsTransactionId,
        String serverTransactionId,
        String messageVersion,
        String transactionStatus) {
      this.threeDs =
          new ChargeThreeDsCreateData(
              null,
              null,
              authenticationValue,
              eci,
              dsTransactionId,
              serverTransactionId,
              messageVersion,
              transactionStatus);
      return (B) this;
    }

    @Override
    public Request<M> build() {

      if (descriptor != null && ignoreDescriptorOnError) {
        Request<M> request = new RetrofitRequestCaller<>(retrofit, createCall());
        return retryIgnoringDescriptor(request);
      } else return super.build();
    }

    @Override
    public Request<M> retryIgnoringDescriptor(Request<M> originalRequest) {
      return RetryUtils.retryIgnoringDescriptor(originalRequest, this);
    }
  }

  public abstract static class AbstractUpdateChargeRequestBuilder<
          B extends AbstractUpdateChargeRequestBuilder, R, M extends Charge>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    @Getter protected StoreId storeId;
    @Getter protected ChargeId chargeId;
    protected Map<String, Object> metadata;

    public AbstractUpdateChargeRequestBuilder(
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

  public abstract static class AbstractCaptureAuthorizedChargeRequestBuilder<
          B extends AbstractCaptureAuthorizedChargeRequestBuilder, R>
      extends IdempotentRetrofitRequestBuilder<Void, R, B> {

    protected StoreId storeId;
    protected ChargeId chargeId;
    protected MoneyLike money;

    public MoneyLike getMoney() {
      return money;
    }

    public StoreId getStoreId() {
      return storeId;
    }

    public ChargeId getChargeId() {
      return chargeId;
    }

    public AbstractCaptureAuthorizedChargeRequestBuilder(
        Retrofit retrofit, StoreId storeId, ChargeId chargeId, MoneyLike money) {
      super(retrofit);
      this.storeId = storeId;
      this.chargeId = chargeId;
      this.money = money;
    }
  }
}
