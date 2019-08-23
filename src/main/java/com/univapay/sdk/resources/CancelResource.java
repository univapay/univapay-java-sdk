package com.univapay.sdk.resources;

import com.univapay.sdk.constants.UnivapayConstants;
import com.univapay.sdk.models.common.CancelId;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.request.cancel.CancelCreateData;
import com.univapay.sdk.models.request.cancel.CancelPatchData;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.cancel.Cancel;
import com.univapay.sdk.types.CursorDirection;
import javax.annotation.Nullable;
import retrofit2.Call;
import retrofit2.http.*;

public interface CancelResource {

  @GET("/stores/{storeId}/charges/{chargeId}/cancels")
  Call<PaginatedList<Cancel>> list(
      @Path("storeId") StoreId storeId,
      @Path("chargeId") ChargeId chargeId,
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable CancelId cursor);

  @GET("/stores/{storeId}/charges/{chargeId}/cancels/{cancelId}")
  Call<Cancel> get(
      @Path("storeId") StoreId storeId,
      @Path("chargeId") ChargeId chargeId,
      @Path("cancelId") CancelId cancelId,
      @Query("polling") @Nullable Boolean polling);

  @POST("/stores/{storeId}/charges/{chargeId}/cancels")
  Call<Cancel> create(
      @Path("storeId") StoreId storeId,
      @Path("chargeId") ChargeId chargeId,
      @Body CancelCreateData requestBody,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @PATCH("/stores/{storeId}/charges/{chargeId}/cancels/{cancelId}")
  Call<Cancel> update(
      @Path("storeId") StoreId storeId,
      @Path("chargeId") ChargeId chargeId,
      @Path("cancelId") CancelId cancelId,
      @Body CancelPatchData requestBody,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);
}
