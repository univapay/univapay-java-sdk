package com.univapay.sdk.builders.transactiontoken;

import com.univapay.sdk.builders.transactiontoken.AbstractTransactionTokensBuilders.AbstractConfirmTransactionTokenRequestBuilder;
import com.univapay.sdk.builders.transactiontoken.AbstractTransactionTokensBuilders.AbstractCreateTemporaryTokenAliasRequestBuilder;
import com.univapay.sdk.builders.transactiontoken.AbstractTransactionTokensBuilders.AbstractCreateTransactionTokenRequestBuilder;
import com.univapay.sdk.builders.transactiontoken.AbstractTransactionTokensBuilders.AbstractDeleteTemporaryTokenAliasRequestBuilder;
import com.univapay.sdk.builders.transactiontoken.AbstractTransactionTokensBuilders.AbstractDeleteTransactionTokenRequestBuilder;
import com.univapay.sdk.builders.transactiontoken.AbstractTransactionTokensBuilders.AbstractGetTemporaryTokenAliasAsImageRequestBuilder;
import com.univapay.sdk.builders.transactiontoken.AbstractTransactionTokensBuilders.AbstractGetTemporaryTokenAliasRequestBuilder;
import com.univapay.sdk.builders.transactiontoken.AbstractTransactionTokensBuilders.AbstractGetTransactionTokenRequestBuilder;
import com.univapay.sdk.builders.transactiontoken.AbstractTransactionTokensBuilders.AbstractListTransactionTokensMerchantRequestBuilder;
import com.univapay.sdk.builders.transactiontoken.AbstractTransactionTokensBuilders.AbstractListTransactionTokensRequestBuilder;
import com.univapay.sdk.builders.transactiontoken.AbstractTransactionTokensBuilders.AbstractUpdateTransactionTokenRequestBuilder;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.UnivapayEmailAddress;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.request.transactiontoken.*;
import com.univapay.sdk.models.request.transactiontoken.ConfirmTransactionTokenReq;
import com.univapay.sdk.models.request.transactiontoken.CreateReq;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.models.request.transactiontoken.TemporaryTokenAliasDisplayReq;
import com.univapay.sdk.models.request.transactiontoken.TemporaryTokenAliasReq;
import com.univapay.sdk.models.request.transactiontoken.UpdateReq;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.UnivapayBinaryData;
import com.univapay.sdk.models.response.transactiontoken.*;
import com.univapay.sdk.models.response.transactiontoken.TemporaryTransactionToken;
import com.univapay.sdk.models.response.transactiontoken.TokenAliasKey;
import com.univapay.sdk.models.response.transactiontoken.TransactionToken;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenAlias;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenWithData;
import com.univapay.sdk.resources.TransactionTokensResource;
import com.univapay.sdk.types.TemporaryTokenAliasMedia;
import com.univapay.sdk.types.TransactionTokenType;
import retrofit2.Call;
import retrofit2.Retrofit;

public abstract class TransactionTokensBuilders {

  public static class DeleteTransactionTokenRequestBuilder
      extends AbstractDeleteTransactionTokenRequestBuilder<
          DeleteTransactionTokenRequestBuilder, TransactionTokensResource> {

    public DeleteTransactionTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, TransactionTokenId transactionTokenId) {
      super(retrofit, storeId, transactionTokenId);
    }

    @Override
    protected Call<Void> getRequest(TransactionTokensResource resource) {
      return resource.delete(storeId, transactionTokenId);
    }
  }

  public static class GetTransactionTokenRequestBuilder
      extends AbstractGetTransactionTokenRequestBuilder<
          GetTransactionTokenRequestBuilder, TransactionTokensResource, TransactionTokenWithData> {

    public GetTransactionTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, TransactionTokenId transactionTokenId) {
      super(retrofit, storeId, transactionTokenId);
    }

    @Override
    protected Call<TransactionTokenWithData> getRequest(TransactionTokensResource resource) {
      return resource.get(storeId, transactionTokenId);
    }
  }

  public static class CreateTransactionTokenRequestBuilder
      extends AbstractCreateTransactionTokenRequestBuilder<
          CreateTransactionTokenRequestBuilder,
          TransactionTokensResource,
          TransactionTokenWithData> {

    public CreateTransactionTokenRequestBuilder(
        Retrofit retrofit,
        UnivapayEmailAddress email,
        PaymentData paymentData,
        TransactionTokenType type) {
      super(retrofit, email, paymentData, type);
    }

    @Override
    protected Call<TransactionTokenWithData> getRequest(TransactionTokensResource resource) {
      CreateReq dataToPost =
          new CreateReq(email, type, usageLimit, metadata, useConfirmation, paymentData);

      return resource.create(dataToPost, idempotencyKey);
    }
  }

  public static class UpdateTransactionTokenRequestBuilder
      extends AbstractUpdateTransactionTokenRequestBuilder<
          UpdateTransactionTokenRequestBuilder,
          TransactionTokensResource,
          TransactionTokenWithData> {

    public UpdateTransactionTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, TransactionTokenId transactionTokenId) {
      super(retrofit, storeId, transactionTokenId);
    }

    @Override
    protected Call<TransactionTokenWithData> getRequest(TransactionTokensResource resource) {
      UpdateReq dataToPost = new UpdateReq(email, metadata, cvv);
      return resource.update(storeId, transactionTokenId, dataToPost, idempotencyKey);
    }
  }

  public static class ListTransactionTokensRequestBuilder
      extends AbstractListTransactionTokensRequestBuilder<
          ListTransactionTokensRequestBuilder, TransactionTokensResource, TransactionToken> {

    public ListTransactionTokensRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit, storeId);
    }

    @Override
    protected Call<PaginatedList<TransactionToken>> getRequest(TransactionTokensResource resource) {
      return resource.list(
          storeId,
          getLimit(),
          getCursorDirection(),
          getCursor(),
          all,
          search,
          mode,
          type,
          customerId);
    }
  }

  public static class ListTransactionTokensMerchantRequestBuilder
      extends AbstractListTransactionTokensMerchantRequestBuilder<
          ListTransactionTokensMerchantRequestBuilder,
          TransactionTokensResource,
          TransactionToken> {

    public ListTransactionTokensMerchantRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    @Override
    protected Call<PaginatedList<TransactionToken>> getRequest(TransactionTokensResource resource) {
      return resource.listMerchant(
          getLimit(), getCursorDirection(), getCursor(), all, search, mode, type, customerId);
    }
  }

  public static class CreateTemporaryTokenAliasRequestBuilder
      extends AbstractCreateTemporaryTokenAliasRequestBuilder<
          CreateTemporaryTokenAliasRequestBuilder,
          TransactionTokensResource,
          TransactionTokenAlias> {

    public CreateTemporaryTokenAliasRequestBuilder(
        Retrofit retrofit, TransactionTokenId transactionTokenId) {
      super(retrofit, transactionTokenId);
    }

    @Override
    protected Call<TransactionTokenAlias> getRequest(TransactionTokensResource resource) {

      TemporaryTokenAliasReq requestData;
      if (money == null) {
        requestData = new TemporaryTokenAliasReq(transactionTokenId, validUntil, metadata);
      } else {
        requestData = new TemporaryTokenAliasReq(transactionTokenId, validUntil, metadata, money);
      }

      return resource.createAlias(requestData, getIdempotencyKey());
    }
  }

  public static class GetTemporaryTokenAliasRequestBuilder
      extends AbstractGetTemporaryTokenAliasRequestBuilder<
          GetTemporaryTokenAliasRequestBuilder,
          TransactionTokensResource,
          TemporaryTransactionToken> {

    public GetTemporaryTokenAliasRequestBuilder(
        Retrofit retrofit, StoreId storeId, TokenAliasKey aliasKey) {
      super(retrofit, storeId, aliasKey);
    }

    @Override
    protected Call<TemporaryTransactionToken> getRequest(TransactionTokensResource resource) {
      return resource.getAlias(storeId, aliasKey);
    }
  }

  public static class GetTemporaryTokenAliasAsImageRequestBuilder
      extends AbstractGetTemporaryTokenAliasAsImageRequestBuilder<
          GetTemporaryTokenAliasAsImageRequestBuilder,
          TransactionTokensResource,
          UnivapayBinaryData> {

    public GetTemporaryTokenAliasAsImageRequestBuilder(
        Retrofit retrofit, StoreId storeId, TokenAliasKey aliasKey) {
      super(retrofit, storeId, aliasKey);
    }

    @Override
    protected Call<UnivapayBinaryData> getRequest(TransactionTokensResource resource) {
      TemporaryTokenAliasDisplayReq requestData =
          new TemporaryTokenAliasDisplayReq(TemporaryTokenAliasMedia.QR, size, logoType, color);
      return resource.getAliasAsImage(storeId, aliasKey, requestData);
    }
  }

  public static class DeleteTemporaryTokenAliasRequestBuilder
      extends AbstractDeleteTemporaryTokenAliasRequestBuilder<
          DeleteTemporaryTokenAliasRequestBuilder, TransactionTokensResource, Void> {

    public DeleteTemporaryTokenAliasRequestBuilder(
        Retrofit retrofit, StoreId storeId, TokenAliasKey aliasKey) {
      super(retrofit, storeId, aliasKey);
    }

    @Override
    protected Call<Void> getRequest(TransactionTokensResource resource) {
      return resource.deleteAlias(storeId, aliasKey);
    }
  }

  public static class ConfirmTransactionTokenRequestBuilder
      extends AbstractConfirmTransactionTokenRequestBuilder<
          ConfirmTransactionTokenRequestBuilder,
          TransactionTokensResource,
          TransactionTokenWithData> {

    public ConfirmTransactionTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, TransactionTokenId tokenId, String confirmationCode) {
      super(retrofit, storeId, tokenId, confirmationCode);
    }

    @Override
    protected Call<TransactionTokenWithData> getRequest(TransactionTokensResource resource) {
      return resource.confirm(
          storeId, tokenId, new ConfirmTransactionTokenReq(confirmationCode), idempotencyKey);
    }
  }
}
