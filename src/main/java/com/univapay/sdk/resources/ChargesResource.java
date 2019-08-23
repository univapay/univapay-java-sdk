package com.univapay.sdk.resources;

import com.univapay.sdk.constants.UnivapayConstants;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.request.charge.CaptureReq;
import com.univapay.sdk.models.request.charge.ChargesReq;
import com.univapay.sdk.models.request.charge.PatchReq;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.types.CursorDirection;
import java.util.Map;
import javax.annotation.Nullable;
import retrofit2.Call;
import retrofit2.http.*;

public interface ChargesResource {

  @GET("/charges")
  Call<PaginatedList<Charge>> listAllCharges(
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable ChargeId cursor,
      @QueryMap @Nullable Map<String, String> propertySearch,
      @Query("metadata") @Nullable String metadataSearch);

  @GET("/stores/{storeId}/charges")
  Call<PaginatedList<Charge>> listAllStoreCharges(
      @Path("storeId") StoreId storeId,
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable ChargeId cursor,
      @QueryMap @Nullable Map<String, String> propertySearch,
      @Query("metadata") @Nullable String metadataSearch);

  @GET("/stores/{storeId}/charges/{id}")
  Call<Charge> getStoreCharge(
      @Path("storeId") StoreId storeId,
      @Path("id") ChargeId id,
      @Query("polling") @Nullable Boolean polling);

  @POST("/charges")
  Call<Charge> createCharge(
      @Body ChargesReq requestBody,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @PATCH("/stores/{storeId}/charges/{id}")
  Call<Charge> updateCharge(
      @Path("storeId") StoreId storeId,
      @Path("id") ChargeId id,
      @Body PatchReq requestBody,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @POST("/stores/{storeId}/charges/{id}/capture")
  Call<Void> capture(
      @Path("storeId") StoreId storeId,
      @Path("id") ChargeId id,
      @Body CaptureReq requestBody,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);
}
