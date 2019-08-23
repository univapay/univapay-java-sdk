package com.univapay.sdk.resources;

import com.univapay.sdk.constants.UnivapayConstants;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.common.ResourceId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.request.merchant.MerchantsReq;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.merchant.MerchantVerificationData;
import com.univapay.sdk.models.response.merchant.MerchantWithConfiguration;
import com.univapay.sdk.models.response.merchant.Transaction;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.TransactionStatus;
import com.univapay.sdk.types.TransactionType;
import javax.annotation.Nullable;
import retrofit2.Call;
import retrofit2.http.*;

/** Resource for managing the merchant's information and history. */
public interface MerchantsResource {

  @GET("/verification")
  Call<MerchantVerificationData> getVerification();

  @POST("/verification")
  Call<MerchantVerificationData> createVerification(
      @Body MerchantsReq dataToPost,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @PATCH("/verification")
  Call<MerchantVerificationData> updateVerification(
      @Body MerchantsReq dataToPatch,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @GET("/transaction_history")
  Call<PaginatedList<Transaction>> getTransactionHistory(
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable ResourceId cursor,
      @Query("from") @Nullable String from,
      @Query("to") @Nullable String to,
      @Query("status") @Nullable TransactionStatus status,
      @Query("type") @Nullable TransactionType type,
      @Query("mode") @Nullable ProcessingMode mode,
      @Query("search") @Nullable String search,
      @Query("all") @Nullable Boolean all);

  @GET("/stores/{storeId}/transaction_history")
  Call<PaginatedList<Transaction>> getStoreTransactionHistory(
      @Path("storeId") StoreId storeId,
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable ResourceId cursor,
      @Query("from") @Nullable String from,
      @Query("to") @Nullable String to,
      @Query("status") @Nullable TransactionStatus status,
      @Query("type") @Nullable TransactionType type,
      @Query("mode") @Nullable ProcessingMode mode,
      @Query("search") @Nullable String search,
      @Query("all") @Nullable Boolean all);

  @GET("/me")
  Call<MerchantWithConfiguration> me();
}
