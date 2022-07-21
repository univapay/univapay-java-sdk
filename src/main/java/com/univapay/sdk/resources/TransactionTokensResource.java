package com.univapay.sdk.resources;

import com.univapay.sdk.constants.UnivapayConstants;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.UnivapayCustomerId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.request.transactiontoken.ConfirmTransactionTokenReq;
import com.univapay.sdk.models.request.transactiontoken.CreateReq;
import com.univapay.sdk.models.request.transactiontoken.UpdateReq;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.transactiontoken.TransactionToken;
import com.univapay.sdk.models.response.transactiontoken.TransactionTokenWithData;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.TransactionTokenType;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.http.*;

public interface TransactionTokensResource {

  @POST("/tokens")
  Call<TransactionTokenWithData> create(
      @Body CreateReq dataToPost,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @GET("/stores/{storeId}/tokens/{tokenId}")
  Call<TransactionTokenWithData> get(
      @Path("storeId") StoreId storeId, @Path("tokenId") TransactionTokenId tokenId);

  @DELETE("/stores/{storeId}/tokens/{tokenId}")
  Call<Void> delete(@Path("storeId") StoreId storeId, @Path("tokenId") TransactionTokenId tokenId);

  @PATCH("/stores/{storeId}/tokens/{tokenId}")
  Call<TransactionTokenWithData> update(
      @Path("storeId") StoreId storeId,
      @Path("tokenId") TransactionTokenId tokenId,
      @Body UpdateReq dataToPatch,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @GET("/stores/{storeId}/tokens")
  Call<PaginatedList<TransactionToken>> list(
      @Path("storeId") StoreId storeId,
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable TransactionTokenId cursor,
      @Query("all") @Nullable Boolean all,
      @Query("search") @Nullable String search,
      @Query("mode") @Nullable ProcessingMode mode,
      @Query("type") @Nullable TransactionTokenType type,
      @Query("customer_id") @Nullable UnivapayCustomerId customerId);

  @GET("/tokens")
  Call<PaginatedList<TransactionToken>> listMerchant(
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable TransactionTokenId cursor,
      @Query("all") @Nullable Boolean all,
      @Query("search") @Nullable String search,
      @Query("mode") @Nullable ProcessingMode mode,
      @Query("type") @Nullable TransactionTokenType type,
      @Query("customer_id") @Nullable UnivapayCustomerId customerId);

  @POST("/stores/{storeId}/tokens/{tokenId}/confirm")
  Call<TransactionTokenWithData> confirm(
      @Path("storeId") StoreId storeId,
      @Path("tokenId") TransactionTokenId tokenId,
      @Body ConfirmTransactionTokenReq dataToPost,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);
}
