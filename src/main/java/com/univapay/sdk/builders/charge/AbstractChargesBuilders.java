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
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.request.charge.CardChargeSearch;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.models.response.transactiontoken.TemporaryTransactionToken;
import com.univapay.sdk.models.response.transactiontoken.TokenAliasKey;
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.types.TransactionTokenType;
import com.univapay.sdk.utils.Backoff;
import com.univapay.sdk.utils.MetadataAdapter;
import com.univapay.sdk.utils.UnivapayCallback;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import com.univapay.sdk.builders.*;
import com.univapay.sdk.models.common.*;
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
    protected MoneyLike money;
    protected Boolean onlyDirectCurrency;
    protected Date captureAt;
    protected Boolean capture;
    protected Boolean ignoreDescriptorOnError = false;
    protected String descriptor;
    protected MetadataMap metadata;

    protected TransactionTokenId getTransactionTokenId() {
      return transactionTokenId;
    }

    public MoneyLike getMoney() {
      return money;
    }

    public Boolean getOnlyDirectCurrency() {
      return onlyDirectCurrency;
    }

    public Date getCaptureAt() {
      return captureAt;
    }

    public Boolean getCapture() {
      return capture;
    }

    public String getDescriptor() {
      return descriptor;
    }

    protected MetadataMap getMetadata() {
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

    public B withCaptureAt(Date captureAt) {
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

    public B withMetadata(MetadataMap metadata) {
      this.metadata = metadata;
      return (B) this;
    }

    public <T> B withMetadata(T metadata, MetadataAdapter<T> adapter) {
      this.metadata = adapter.serialize(metadata);
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

    protected StoreId storeId;
    protected ChargeId chargeId;
    protected MetadataMap metadata;

    public StoreId getStoreId() {
      return storeId;
    }

    public ChargeId getChargeId() {
      return chargeId;
    }

    protected MetadataMap getMetadata() {
      return metadata;
    }

    public AbstractUpdateChargeRequestBuilder(
        Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
      super(retrofit);
      this.storeId = storeId;
      this.chargeId = chargeId;
    }

    public B withMetadata(MetadataMap metadata) {
      this.metadata = metadata;
      return (B) this;
    }

    public <T> B withMetadata(T metadata, MetadataAdapter<T> adapter) {
      this.metadata = adapter.serialize(metadata);
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

  public abstract static class AbstractCreateChargeWithTokenAliasRequestBuilder<
      B extends AbstractCreateChargeWithTokenAliasRequestBuilder,
      E extends Charge,
      M extends TemporaryTransactionToken> {
    protected StoreId storeId;
    protected TokenAliasKey alias;
    protected MoneyLike money;
    protected Boolean onlyDirectCurrency;
    protected Date captureAt;
    protected Boolean capture;
    protected String descriptor;
    protected Boolean ignoreDescriptorOnError = false;
    protected MetadataMap metadata;
    protected IdempotencyKey idempotencyKey;

    protected boolean polling = false;
    protected long timeout = 0;
    protected Backoff backoff = null;

    public StoreId getStoreId() {
      return storeId;
    }

    public TokenAliasKey getAlias() {
      return alias;
    }

    protected MoneyLike getMoney() {
      return money;
    }

    protected Boolean getOnlyDirectCurrency() {
      return onlyDirectCurrency;
    }

    protected Date getCaptureAt() {
      return captureAt;
    }

    protected Boolean getCapture() {
      return capture;
    }

    protected String getDescriptor() {
      return descriptor;
    }

    public Boolean getIgnoreDescriptorOnError() {
      return ignoreDescriptorOnError;
    }

    public boolean isPolling() {
      return polling;
    }

    public long getTimeout() {
      return timeout;
    }

    public Backoff getBackoff() {
      return backoff;
    }

    protected MetadataMap getMetadata() {
      return metadata;
    }

    protected IdempotencyKey getIdempotencyKey() {
      return idempotencyKey;
    }

    public B withOnlyDirectCurrency(Boolean onlyDirectCurrency) {
      this.onlyDirectCurrency = onlyDirectCurrency;
      return (B) this;
    }

    public B withCaptureAt(Date captureAt) {
      this.captureAt = captureAt;
      return (B) this;
    }

    public B withDescriptor(String descriptor, Boolean ignoreDescriptorOnError) {
      this.descriptor = descriptor;
      this.ignoreDescriptorOnError = ignoreDescriptorOnError;
      return (B) this;
    }

    public B withDescriptor(String descriptor) {
      this.descriptor = descriptor;
      return (B) this;
    }

    public B withMetadata(MetadataMap metadata) {
      this.metadata = metadata;
      return (B) this;
    }

    public <T> B withMetadata(T metadata, MetadataAdapter<T> adapter) {
      this.metadata = adapter.serialize(metadata);
      return (B) this;
    }

    public B withIdempotencyKey(IdempotencyKey idempotencyKey) {
      this.idempotencyKey = idempotencyKey;
      return (B) this;
    }

    protected B withPolling(boolean polling, long timeout, Backoff backoff) {
      this.polling = polling;
      this.timeout = timeout;
      this.backoff = backoff;
      return (B) this;
    }

    public B withPolling(long timeout, Backoff backoff) {
      return withPolling(true, timeout, backoff);
    }

    public B withPolling(long timeout) {
      return withPolling(true, timeout, null);
    }

    public B withPolling() {
      return withPolling(true, 0, null);
    }

    public AbstractCreateChargeWithTokenAliasRequestBuilder(
        StoreId storeId, TokenAliasKey alias, MoneyLike money, Boolean capture) {
      this.storeId = storeId;
      this.alias = alias;
      this.money = money;
      this.capture = capture;
    }

    public abstract E dispatch()
        throws IOException, UnivapayException, TimeoutException, InterruptedException;

    protected abstract E pollAndDispatch()
        throws IOException, UnivapayException, TimeoutException, InterruptedException;

    public abstract void dispatch(final UnivapayCallback<E> callback);

    protected abstract void pollAndDispatch(final UnivapayCallback<E> callback);

    protected void checkIsActive(M temporaryTransactionToken) {
      if (!temporaryTransactionToken.getActive()) {
        throw new IllegalStateException("Transaction Token is not active");
      }
    }

    protected void checkIsNotSubscription(M temporaryTransactionToken) {
      if (temporaryTransactionToken.getType() == TransactionTokenType.SUBSCRIPTION) {
        throw new IllegalArgumentException(
            "Can't create a charge with a transaction token of type 'subscription'.");
      }
    }

    protected MoneyLike fillMoney(MoneyLike money, BigInteger tokenAmount, String tokenCurrency) {
      if (money == null && (tokenAmount == null || tokenCurrency == null)) {
        throw new IllegalArgumentException("Set charge amount and currency");
      } else if (tokenAmount != null && tokenCurrency != null) {
        return new MoneyLike(tokenAmount, tokenCurrency);
      }
      return new MoneyLike(money.getAmount(), money.getCurrency());
    }
  }
}
