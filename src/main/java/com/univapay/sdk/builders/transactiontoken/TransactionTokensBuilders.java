package com.univapay.sdk.builders.transactiontoken;

import com.univapay.sdk.builders.ResourceMonitor;
import com.univapay.sdk.builders.transactiontoken.AbstractTransactionTokensBuilders.*;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.UnivapayEmailAddress;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.common.charge.CvvAuthorization;
import com.univapay.sdk.models.common.charge.CvvAuthorizationStatus;
import com.univapay.sdk.models.common.threeDs.TransactionToken3dsData;
import com.univapay.sdk.models.common.threeDs.TransactionToken3dsStatus;
import com.univapay.sdk.models.request.transactiontoken.ConfirmTransactionTokenReq;
import com.univapay.sdk.models.request.transactiontoken.CreateReq;
import com.univapay.sdk.models.request.transactiontoken.PaymentData;
import com.univapay.sdk.models.request.transactiontoken.UpdateReq;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.transactiontoken.TransactionToken;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenWithData;
import com.univapay.sdk.resources.TransactionTokensResource;
import com.univapay.sdk.types.TransactionTokenType;
import com.univapay.sdk.utils.Backoff;
import com.univapay.sdk.utils.ExponentialBackoff;
import java.util.Optional;
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
          new CreateReq(email, type, usageLimit, metadata, useConfirmation, ipAddress, paymentData);

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

  public static ResourceMonitor<TransactionTokenWithData> cvvAuthorizationCompletionMonitor(
      Retrofit retrofit, StoreId storeId, TransactionTokenId transactionTokenId) {

    return new ResourceMonitor<TransactionTokenWithData>(
        new TransactionTokensBuilders.GetTransactionTokenRequestBuilder(
            retrofit, storeId, transactionTokenId),
        resource -> {

          // Keep querying until is not enabled or enabled and not pending
          return Optional.ofNullable(resource.getData())
              .map(value -> value.asCardPaymentData())
              .flatMap(
                  value -> { // This is the .or() implementation that is not available at Java8
                    if (value.getCvvAuthorization() != null) {
                      return Optional.of(value.getCvvAuthorization());
                    } else {
                      return Optional.of(new CvvAuthorization(null, null, null));
                    }
                  })
              .filter(
                  value -> {
                    boolean isEnabled = !Optional.ofNullable(value.getEnabled()).orElse(false);
                    return isEnabled || value.getStatus() != CvvAuthorizationStatus.PENDING;
                  })
              .isPresent();
        });
  }

  public static ResourceMonitor<? extends TransactionTokenWithData>
      transactionToken3dsAwaitingMonitor(
          Retrofit retrofit, StoreId storeId, TransactionTokenId transactionTokenId) {
    return new ResourceMonitor<TransactionTokenWithData>(
        new TransactionTokensBuilders.GetTransactionTokenRequestBuilder(
            retrofit, storeId, transactionTokenId),
        resource -> {

          // Keep querying until is not enabled or enabled and not pending
          return Optional.ofNullable(resource.getData())
              .map(value -> value.asCardPaymentData())
              .flatMap(
                  value -> { // This is the .or() implementation that is not available at Java8
                    if (value.getThreeDs() != null) {
                      return Optional.of(value.getThreeDs());
                    } else {
                      return Optional.of(new TransactionToken3dsData(null, null, null, null, null));
                    }
                  })
              .filter(
                  value -> {
                    boolean isEnabled = !Optional.ofNullable(value.getEnabled()).orElse(false);
                    return isEnabled || value.getStatus() != TransactionToken3dsStatus.PENDING;
                  })
              .isPresent();
        }) {

      @Override
      protected Backoff createBackoff() {
        // Make a faster backoff for the Transaction Token Three DS processing
        return new ExponentialBackoff(0, 1_000, 2, 0.5);
      }
    };
  }
}
