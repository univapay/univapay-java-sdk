package com.univapay.sdk.builders.transactiontoken;

import com.univapay.sdk.builders.IdempotentRetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilderPaginated;
import com.univapay.sdk.models.common.EmailAddress;
import com.univapay.sdk.models.common.EmptyEmailAddress;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.UnivapayCustomerId;
import com.univapay.sdk.models.common.UnivapayEmailAddress;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.models.response.UnivapayBinaryData;
import com.univapay.sdk.models.response.transactiontoken.TemporaryTransactionToken;
import com.univapay.sdk.models.response.transactiontoken.TokenAliasKey;
import com.univapay.sdk.models.response.transactiontoken.TransactionToken;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenAlias;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenWithData;
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.RecurringTokenInterval;
import com.univapay.sdk.types.TemporaryTokenAliasQRLogo;
import com.univapay.sdk.types.TransactionTokenType;
import com.univapay.sdk.utils.MetadataAdapter;
import java.util.Date;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.response.transactiontoken.*;
import com.univapay.sdk.types.*;
import retrofit2.Retrofit;

public abstract class AbstractTransactionTokensBuilders {

  public abstract static class AbstractDeleteTransactionTokenRequestBuilder<
          B extends AbstractDeleteTransactionTokenRequestBuilder, R>
      extends RetrofitRequestBuilder<Void, R> {

    protected StoreId storeId;
    protected TransactionTokenId transactionTokenId;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected TransactionTokenId getTransactionTokenId() {
      return transactionTokenId;
    }

    public AbstractDeleteTransactionTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, TransactionTokenId transactionTokenId) {
      super(retrofit);
      this.storeId = storeId;
      this.transactionTokenId = transactionTokenId;
    }
  }

  public abstract static class AbstractGetTransactionTokenRequestBuilder<
          B extends AbstractGetTransactionTokenRequestBuilder,
          R,
          M extends TransactionTokenWithData>
      extends RetrofitRequestBuilder<M, R> {

    protected StoreId storeId;
    protected TransactionTokenId transactionTokenId;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected TransactionTokenId getTransactionTokenId() {
      return transactionTokenId;
    }

    public AbstractGetTransactionTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, TransactionTokenId transactionTokenId) {
      super(retrofit);
      this.storeId = storeId;
      this.transactionTokenId = transactionTokenId;
    }
  }

  public abstract static class AbstractCreateTransactionTokenRequestBuilder<
          B extends AbstractCreateTransactionTokenRequestBuilder,
          R,
          M extends TransactionTokenWithData>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    protected UnivapayEmailAddress email;
    protected RecurringTokenInterval usageLimit;
    protected PaymentData paymentData;
    protected TransactionTokenType type;
    protected MetadataMap metadata = new MetadataMap();
    protected Boolean useConfirmation;

    protected String getEmail() {
      if (email == null) {
        return null;
      }
      return email.serialize();
    }

    protected RecurringTokenInterval getUsageLimit() {
      return usageLimit;
    }

    protected MetadataMap getMetadata() {
      return metadata;
    }

    protected PaymentData getPaymentData() {
      return paymentData;
    }

    protected TransactionTokenType getType() {
      return type;
    }

    public Boolean getUseConfirmation() {
      return useConfirmation;
    }

    public AbstractCreateTransactionTokenRequestBuilder(
        Retrofit retrofit,
        UnivapayEmailAddress email,
        PaymentData paymentData,
        TransactionTokenType type) {
      super(retrofit);
      this.paymentData = paymentData;
      this.email = email;
      this.type = type;
    }

    public B withUsageLimit(RecurringTokenInterval usageLimit) {
      this.usageLimit = usageLimit;
      return (B) this;
    }

    public B withMetadata(MetadataMap metadata) {
      this.metadata.putAll(metadata);
      return (B) this;
    }

    public <T> B withMetadata(T metadata, MetadataAdapter<T> adapter) {
      this.metadata.putAll(adapter.serialize(metadata));
      return (B) this;
    }

    public B withUseConfirmation(Boolean useConfirmation) {
      this.useConfirmation = useConfirmation;
      return (B) this;
    }

    public B withCustomerId(UnivapayCustomerId customerId) {
      this.metadata.put(UnivapayCustomerId.metadataKey, customerId.toString());
      return (B) this;
    }
  }

  public abstract static class AbstractUpdateTransactionTokenRequestBuilder<
          B extends AbstractUpdateTransactionTokenRequestBuilder,
          R,
          M extends TransactionTokenWithData>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    protected UnivapayEmailAddress email;
    protected MetadataMap metadata = new MetadataMap();
    protected Integer cvv;
    protected StoreId storeId;
    protected TransactionTokenId transactionTokenId;

    protected String getEmail() {
      if (email == null) {
        return null;
      }
      return email.serialize();
    }

    protected MetadataMap getMetadata() {
      return metadata;
    }

    protected Integer getCvv() {
      return cvv;
    }

    protected StoreId getStoreId() {
      return storeId;
    }

    protected TransactionTokenId getTransactionTokenId() {
      return transactionTokenId;
    }

    public AbstractUpdateTransactionTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, TransactionTokenId transactionTokenId) {
      super(retrofit);
      this.storeId = storeId;
      this.transactionTokenId = transactionTokenId;
    }

    public B withEmail(String email) {
      this.email = new EmailAddress(email);
      return (B) this;
    }

    /**
     * Update an email to null value.
     *
     * @return a request builder
     */
    public B removeEmail() {
      this.email = new EmptyEmailAddress();
      return (B) this;
    }

    public B withMetadata(MetadataMap metadata) {
      this.metadata.putAll(metadata);
      return (B) this;
    }

    public <T> B withMetadata(T metadata, MetadataAdapter<T> adapter) {
      this.metadata.putAll(adapter.serialize(metadata));
      return (B) this;
    }

    public B withCustomerId(UnivapayCustomerId customerId) {
      this.metadata.put(UnivapayCustomerId.metadataKey, customerId.toString());
      return (B) this;
    }

    public B withCvv(Integer cvv) {
      this.cvv = cvv;
      return (B) this;
    }
  }

  public abstract static class AbstractListTransactionTokensRequestBuilder<
          B extends AbstractListTransactionTokensRequestBuilder, R, M extends TransactionToken>
      extends RetrofitRequestBuilderPaginated<M, R, B, TransactionTokenId> {
    protected StoreId storeId;
    protected Boolean all;
    protected String search;
    protected UnivapayCustomerId customerId;
    protected ProcessingMode mode;
    protected TransactionTokenType type;

    public AbstractListTransactionTokensRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit);
      this.storeId = storeId;
    }

    public Boolean getAll() {
      return all;
    }

    public String getSearch() {
      return search;
    }

    public UnivapayCustomerId getCustomerId() {
      return customerId;
    }

    public ProcessingMode getMode() {
      return mode;
    }

    public TransactionTokenType getType() {
      return type;
    }

    protected StoreId getStoreId() {
      return storeId;
    }

    public B withListAll(Boolean all) {
      this.all = all;
      return (B) this;
    }

    public B withSearch(String searchString) {
      this.search = searchString;
      return (B) this;
    }

    public B withCustomerId(UnivapayCustomerId customerId) {
      this.customerId = customerId;
      return (B) this;
    }

    public B withMode(ProcessingMode mode) {
      this.mode = mode;
      return (B) this;
    }

    public B withType(TransactionTokenType type) {
      this.type = type;
      return (B) this;
    }
  }

  public abstract static class AbstractListTransactionTokensMerchantRequestBuilder<
          B extends AbstractListTransactionTokensMerchantRequestBuilder,
          R,
          M extends TransactionToken>
      extends RetrofitRequestBuilderPaginated<M, R, B, TransactionTokenId> {

    protected Boolean all;
    protected String search;
    protected UnivapayCustomerId customerId;
    protected ProcessingMode mode;
    protected TransactionTokenType type;

    public AbstractListTransactionTokensMerchantRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    protected String getSearch() {
      return search;
    }

    protected UnivapayCustomerId getCustomerId() {
      return customerId;
    }

    protected ProcessingMode getMode() {
      return mode;
    }

    public TransactionTokenType getType() {
      return type;
    }

    public Boolean getAll() {
      return all;
    }

    public B withListAll(Boolean all) {
      this.all = all;
      return (B) this;
    }

    public B withSearch(String searchString) {
      this.search = searchString;
      return (B) this;
    }

    public B withCustomerId(UnivapayCustomerId customerId) {
      this.customerId = customerId;
      return (B) this;
    }

    public B withMode(ProcessingMode mode) {
      this.mode = mode;
      return (B) this;
    }

    public B withType(TransactionTokenType type) {
      this.type = type;
      return (B) this;
    }
  }

  public abstract static class AbstractCreateTemporaryTokenAliasRequestBuilder<
          B extends AbstractCreateTemporaryTokenAliasRequestBuilder,
          R,
          M extends TransactionTokenAlias>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    protected TransactionTokenId transactionTokenId;
    protected Date validUntil;
    protected MoneyLike money;
    protected MetadataMap metadata;

    public AbstractCreateTemporaryTokenAliasRequestBuilder(
        Retrofit retrofit, TransactionTokenId transactionTokenId) {
      super(retrofit);
      this.transactionTokenId = transactionTokenId;
    }

    public TransactionTokenId getTransactionTokenId() {
      return transactionTokenId;
    }

    public Date getValidUntil() {
      return validUntil;
    }

    public MoneyLike getMoney() {
      return money;
    }

    public MetadataMap getMetadata() {
      return metadata;
    }

    public B withValidUntil(Date validUntil) {
      this.validUntil = validUntil;
      return (B) this;
    }

    public B withMoney(MoneyLike money) {
      this.money = money;
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
  }

  public abstract static class AbstractGetTemporaryTokenAliasRequestBuilder<
          B extends AbstractGetTemporaryTokenAliasRequestBuilder,
          R,
          M extends TemporaryTransactionToken>
      extends RetrofitRequestBuilder<M, R> {

    protected StoreId storeId;
    protected TokenAliasKey aliasKey;

    public AbstractGetTemporaryTokenAliasRequestBuilder(
        Retrofit retrofit, StoreId storeId, TokenAliasKey aliasKey) {
      super(retrofit);
      this.storeId = storeId;
      this.aliasKey = aliasKey;
    }

    public StoreId getStoreId() {
      return storeId;
    }

    public TokenAliasKey getAliasKey() {
      return aliasKey;
    }
  }

  public abstract static class AbstractGetTemporaryTokenAliasAsImageRequestBuilder<
          B extends AbstractGetTemporaryTokenAliasAsImageRequestBuilder,
          R,
          M extends UnivapayBinaryData>
      extends RetrofitRequestBuilder<M, R> {

    protected StoreId storeId;
    protected TokenAliasKey aliasKey;
    protected Integer size;
    protected TemporaryTokenAliasQRLogo logoType;
    protected String color;

    public AbstractGetTemporaryTokenAliasAsImageRequestBuilder(
        Retrofit retrofit, StoreId storeId, TokenAliasKey aliasKey) {
      super(retrofit);
      this.storeId = storeId;
      this.aliasKey = aliasKey;
    }

    public StoreId getStoreId() {
      return storeId;
    }

    public TokenAliasKey getAliasKey() {
      return aliasKey;
    }

    public Integer getSize() {
      return size;
    }

    public TemporaryTokenAliasQRLogo getLogoType() {
      return logoType;
    }

    public String getColor() {
      return color;
    }

    public B withSize(Integer size) {
      this.size = size;
      return (B) this;
    }

    public B withLogoType(TemporaryTokenAliasQRLogo logoType) {
      this.logoType = logoType;
      return (B) this;
    }

    public B withColor(String color) {
      this.color = color;
      return (B) this;
    }
  }

  public abstract static class AbstractDeleteTemporaryTokenAliasRequestBuilder<
          B extends AbstractDeleteTemporaryTokenAliasRequestBuilder, R, M extends Void>
      extends RetrofitRequestBuilder<M, R> {

    protected StoreId storeId;
    protected TokenAliasKey aliasKey;

    public AbstractDeleteTemporaryTokenAliasRequestBuilder(
        Retrofit retrofit, StoreId storeId, TokenAliasKey aliasKey) {
      super(retrofit);
      this.storeId = storeId;
      this.aliasKey = aliasKey;
    }

    public StoreId getStoreId() {
      return storeId;
    }

    public TokenAliasKey getAliasId() {
      return aliasKey;
    }
  }

  public abstract static class AbstractConfirmTransactionTokenRequestBuilder<
          B extends AbstractConfirmTransactionTokenRequestBuilder,
          R,
          M extends TransactionTokenWithData>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    protected StoreId storeId;
    protected TransactionTokenId tokenId;
    protected String confirmationCode;

    public AbstractConfirmTransactionTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, TransactionTokenId tokenId, String confirmationCode) {
      super(retrofit);
      this.storeId = storeId;
      this.tokenId = tokenId;
      this.confirmationCode = confirmationCode;
    }

    public StoreId getStoreId() {
      return storeId;
    }

    public TransactionTokenId getTokenId() {
      return tokenId;
    }

    public String getConfirmationCode() {
      return confirmationCode;
    }
  }
}
