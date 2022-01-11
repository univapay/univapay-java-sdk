package com.univapay.sdk.resources;

import com.univapay.sdk.constants.UnivapayConstants;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.common.RefundId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.request.refund.RefundCreateData;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.refund.Refund;
import com.univapay.sdk.types.CursorDirection;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.http.*;

/** Resource for managing refunds. */
public interface RefundsResource {

  @GET("/stores/{storeId}/charges/{chargeId}/refunds")
  Call<PaginatedList<Refund>> list(
      @Path("storeId") StoreId storeId,
      @Path("chargeId") ChargeId chargeId,
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable RefundId cursor,
      @Query("metadata") @Nullable String metadataSearch);

  @GET("/stores/{storeId}/charges/{chargeId}/refunds/{refundId}")
  Call<Refund> get(
      @Path("storeId") StoreId storeId,
      @Path("chargeId") ChargeId chargeId,
      @Path("refundId") RefundId refundId,
      @Query("polling") @Nullable Boolean polling);

  @POST("/stores/{storeId}/charges/{chargeId}/refunds")
  Call<Refund> create(
      @Path("storeId") StoreId storeId,
      @Path("chargeId") ChargeId chargeId,
      @Body RefundCreateData dataToPost,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);
}
