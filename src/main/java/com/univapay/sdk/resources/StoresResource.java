package com.univapay.sdk.resources;

import com.univapay.sdk.constants.UnivapayConstants;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.Domain;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.UnivapayCustomerId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.request.store.CustomerIdRequest;
import com.univapay.sdk.models.request.store.GetDynamicBrandInfoForm;
import com.univapay.sdk.models.request.store.StoreCreateData;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.store.CheckoutInfo;
import com.univapay.sdk.models.response.store.DynamicBrandInfo;
import com.univapay.sdk.models.response.store.Store;
import com.univapay.sdk.models.response.store.StoreWithConfiguration;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.types.Gateway;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.http.*;

public interface StoresResource {

  @GET("/stores")
  Call<PaginatedList<Store>> list(
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable StoreId cursor,
      @Query("search") @Nullable String search);

  @POST("/stores")
  Call<StoreWithConfiguration> create(
      @Body StoreCreateData dataToPost,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @GET("/stores/{storeId}")
  Call<StoreWithConfiguration> get(@Path("storeId") StoreId storeId);

  @PATCH("/stores/{storeId}")
  Call<StoreWithConfiguration> update(
      @Path("storeId") StoreId storeId,
      @Body StoreCreateData dataToPost,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @DELETE("/stores/{storeId}")
  Call<Void> delete(@Path("storeId") StoreId storeId);

  @GET("/checkout_info")
  Call<CheckoutInfo> getCheckoutInfo(@Query("origin") Domain origin);

  @POST("/checkout_info/gateway/{gateway}")
  Call<DynamicBrandInfo> getDynamicBrandInfo(
      @Path("gateway") Gateway gateway, @Body GetDynamicBrandInfoForm form);

  @POST("/stores/{storeId}/create_customer_id")
  Call<UnivapayCustomerId> createCustomerId(
      @Path("storeId") StoreId storeId, @Body CustomerIdRequest userId);
}
