package com.univapay.sdk.resources;

import com.univapay.sdk.constants.UnivapayConstants;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.common.ScheduledPaymentId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.request.subscription.ScheduledPaymentPatchData;
import com.univapay.sdk.models.request.subscription.SubscriptionCreateData;
import com.univapay.sdk.models.request.subscription.SubscriptionPlanSimulationRequest;
import com.univapay.sdk.models.request.subscription.SubscriptionUpdateData;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.PaymentsPlan;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.models.response.subscription.FullSubscription;
import com.univapay.sdk.models.response.subscription.ScheduledPayment;
import com.univapay.sdk.models.response.subscription.Subscription;
import com.univapay.sdk.types.CursorDirection;
import javax.annotation.Nullable;
import com.univapay.sdk.models.common.*;
import retrofit2.Call;
import retrofit2.http.*;

/** Resource for managing subscriptions. */
public interface SubscriptionsResource {
  @GET("/subscriptions")
  Call<PaginatedList<Subscription>> listAllSubscriptions(
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable SubscriptionId cursor);

  @GET("/stores/{storeId}/subscriptions")
  Call<PaginatedList<Subscription>> listAllSubscriptions(
      @Path("storeId") StoreId storeId,
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable SubscriptionId cursor);

  @GET("/stores/{storeId}/subscriptions/{subscriptionId}")
  Call<FullSubscription> getSubscription(
      @Path("storeId") StoreId storeId,
      @Path("subscriptionId") SubscriptionId subscriptionId,
      @Query("polling") @Nullable Boolean polling);

  @POST("/subscriptions")
  Call<FullSubscription> createSubscription(
      @Body SubscriptionCreateData dataToPost,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @PATCH("/stores/{storeId}/subscriptions/{subscriptionId}")
  Call<FullSubscription> updateSubscription(
      @Path("storeId") StoreId storeId,
      @Path("subscriptionId") SubscriptionId subscriptionId,
      @Body SubscriptionUpdateData dataToPost,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @GET("/stores/{storeId}/subscriptions/{subscriptionId}/payments")
  Call<PaginatedList<ScheduledPayment>> listPayments(
      @Path("storeId") StoreId storeId,
      @Path("subscriptionId") SubscriptionId subscriptionId,
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable ScheduledPaymentId cursor);

  @GET("/stores/{storeId}/subscriptions/{subscriptionId}/payments/{paymentId}")
  Call<ScheduledPayment> getPayment(
      @Path("storeId") StoreId storeId,
      @Path("subscriptionId") SubscriptionId subscriptionId,
      @Path("paymentId") ScheduledPaymentId paymentId);

  @GET("/stores/{storeId}/subscriptions/{subscriptionId}/payments/{paymentId}/charges")
  Call<PaginatedList<Charge>> listChargesForPayment(
      @Path("storeId") StoreId storeId,
      @Path("subscriptionId") SubscriptionId subscriptionId,
      @Path("paymentId") ScheduledPaymentId paymentId,
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable ChargeId cursor);

  @PATCH("/stores/{storeId}/subscriptions/{subscriptionId}/payments/{paymentId}")
  Call<ScheduledPayment> updatePayment(
      @Path("storeId") StoreId storeId,
      @Path("subscriptionId") SubscriptionId subscriptionId,
      @Path("paymentId") ScheduledPaymentId paymentId,
      @Body ScheduledPaymentPatchData patchData,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @DELETE("/stores/{storeId}/subscriptions/{subscriptionId}")
  Call<Void> deleteSubscription(
      @Path("storeId") StoreId storeId, @Path("subscriptionId") SubscriptionId subscriptionId);

  @GET("/stores/{storeId}/subscriptions/{subscriptionId}/charges")
  Call<PaginatedList<Charge>> listSubscriptionCharges(
      @Path("storeId") StoreId storeId,
      @Path("subscriptionId") SubscriptionId subscriptionId,
      @Query("limit") @Nullable Integer limit,
      @Query("cursor_direction") @Nullable CursorDirection cursorDirection,
      @Query("cursor") @Nullable ChargeId cursor);

  @POST("/subscriptions/simulate_plan")
  Call<PaymentsPlan> simulateSubscriptionPlan(
      @Body SubscriptionPlanSimulationRequest simulationRequest,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);

  @POST("/stores/{storeId}/subscriptions/simulate_plan")
  Call<PaymentsPlan> simulateSubscriptionPlan(
      @Path("storeId") StoreId storeId,
      @Body SubscriptionPlanSimulationRequest simulationRequest,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);
}
