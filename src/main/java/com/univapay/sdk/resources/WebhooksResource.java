package com.univapay.sdk.resources;

import com.univapay.sdk.constants.UnivapayConstants;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.common.WebhookId;
import com.univapay.sdk.models.request.store.WebhookReq;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.webhook.Webhook;
import com.univapay.sdk.types.CursorDirection;
import javax.annotation.Nullable;
import retrofit2.Call;
import retrofit2.http.*;

/** Resources for managing webhooks associated with the merchant's stores. */
public interface WebhooksResource {
  @GET("/webhooks")
  Call<PaginatedList<Webhook>> list(
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable WebhookId cursor);

  @GET("/webhooks/{webhookId}")
  Call<Webhook> get(@Path("webhookId") WebhookId webhookId);

  @POST("/webhooks")
  Call<Webhook> create(
      @Body WebhookReq dataToPost, @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @PATCH("/webhooks/{webhookId}")
  Call<Webhook> update(
      @Path("webhookId") WebhookId webhookId,
      @Body WebhookReq dataToPost,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @DELETE("/webhooks/{webhookId}")
  Call<Void> delete(@Path("webhookId") WebhookId webhookId);

  @GET("/stores/{storeId}/webhooks")
  Call<PaginatedList<Webhook>> list(
      @Path("storeId") StoreId storeId,
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable WebhookId cursor);

  @POST("/stores/{storeId}/webhooks")
  Call<Webhook> create(
      @Path("storeId") StoreId storeId,
      @Body WebhookReq dataToPost,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @GET("/stores/{storeId}/webhooks/{webhookId}")
  Call<Webhook> get(@Path("storeId") StoreId storeId, @Path("webhookId") WebhookId webhookId);

  @PATCH("/stores/{storeId}/webhooks/{webhookId}")
  Call<Webhook> update(
      @Path("storeId") StoreId storeId,
      @Path("webhookId") WebhookId webhookId,
      @Body WebhookReq dataToPost,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @DELETE("/stores/{storeId}/webhooks/{webhookId}")
  Call<Void> delete(@Path("storeId") StoreId storeId, @Path("webhookId") WebhookId webhookId);
}
