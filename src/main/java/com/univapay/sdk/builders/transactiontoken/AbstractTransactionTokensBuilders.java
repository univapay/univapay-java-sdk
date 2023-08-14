package com.univapay.sdk.builders.transactiontoken;

import com.univapay.sdk.builders.IdempotentRetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilderPaginated;
import com.univapay.sdk.models.common.EmailAddress;
import com.univapay.sdk.models.common.EmptyEmailAddress;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.UnivapayCustomerId;
import com.univapay.sdk.models.common.UnivapayEmailAddress;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.models.response.transactiontoken.TransactionToken;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenWithData;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.RecurringTokenInterval;
import com.univapay.sdk.types.TransactionTokenType;
import java.util.HashMap;
import java.util.Map;
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
    protected Map<String, String> metadata = new HashMap<>();
    protected Boolean useConfirmation;
    protected String ipAddress;

    protected String getEmail() {
      if (email == null) {
        return null;
      }
      return email.serialize();
    }

    protected RecurringTokenInterval getUsageLimit() {
      return usageLimit;
    }

    protected Map<String, String> getMetadata() {
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

    protected String getIpAddress() {
      return ipAddress;
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

    public B withMetadata(Map<String, String> metadata) {
      this.metadata = metadata;
      return (B) this;
    }

    public B withUseConfirmation(Boolean useConfirmation) {
      this.useConfirmation = useConfirmation;
      return (B) this;
    }

    public B withIpAddress(String ipAddress) {
      this.ipAddress = ipAddress;
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
    protected Map<String, String> metadata = new HashMap<>();
    protected Integer cvv;
    protected StoreId storeId;
    protected TransactionTokenId transactionTokenId;

    protected String getEmail() {
      if (email == null) {
        return null;
      }
      return email.serialize();
    }

    protected Map<String, String> getMetadata() {
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

    public B withMetadata(Map<String, String> metadata) {
      this.metadata = metadata;
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
