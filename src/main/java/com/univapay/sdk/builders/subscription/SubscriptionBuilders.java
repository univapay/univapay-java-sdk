package com.univapay.sdk.builders.subscription;

import com.univapay.sdk.builders.ResourceMonitor;
import com.univapay.sdk.builders.ResourcePredicate;
import com.univapay.sdk.builders.subscription.AbstractSubscriptionBuilders.*;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.ScheduledPaymentId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.request.subscription.ScheduledPaymentPatchData;
import com.univapay.sdk.models.request.subscription.SubscriptionCreateData;
import com.univapay.sdk.models.request.subscription.SubscriptionPlanSimulationRequest;
import com.univapay.sdk.models.request.subscription.SubscriptionUpdateData;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.PaymentsPlan;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.models.response.subscription.FullSubscription;
import com.univapay.sdk.models.response.subscription.ScheduleSettings;
import com.univapay.sdk.models.response.subscription.ScheduledPayment;
import com.univapay.sdk.models.response.subscription.Subscription;
import com.univapay.sdk.resources.SubscriptionsResource;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.SubscriptionPeriod;
import com.univapay.sdk.types.SubscriptionStatus;
import retrofit2.Call;
import retrofit2.Retrofit;

public abstract class SubscriptionBuilders {

  public static class CreateSubscriptionRequestBuilder
      extends AbstractCreateSubscriptionRequestBuilder<
          CreateSubscriptionRequestBuilder, SubscriptionsResource, FullSubscription> {

    public CreateSubscriptionRequestBuilder(
        Retrofit retrofit, TransactionTokenId token, MoneyLike money, SubscriptionPeriod period) {
      super(retrofit, token, money, period);
    }

    @Override
    protected Call<FullSubscription> getRequest(SubscriptionsResource resource) {
      return resource.createSubscription(
          new SubscriptionCreateData(
              token,
              period,
              initialAmount,
              metadata,
              installmentPlan,
              subscriptionPlan,
              new ScheduleSettings(
                  startOn, zoneId, preserveEndOfMonth, retryInterval, terminationMode),
              money,
              onlyDirectCurrency,
              firstChargeCaptureAfter,
              firstChargeAuthorizationOnly,
              threeDs),
          idempotencyKey);
    }
  }

  public static class UpdateSubscriptionRequestBuilder
      extends AbstractUpdateSubscriptionRequestBuilder<
          UpdateSubscriptionRequestBuilder, SubscriptionsResource, FullSubscription> {

    public UpdateSubscriptionRequestBuilder(
        Retrofit retrofit, StoreId storeId, SubscriptionId subscriptionId) {
      super(retrofit, storeId, subscriptionId);
    }

    @Override
    protected Call<FullSubscription> getRequest(SubscriptionsResource resource) {
      ScheduleSettings scheduleSettings = null;
      if (startOn != null && preserveEndOfMonth != null) {
        scheduleSettings =
            new ScheduleSettings(startOn, null, preserveEndOfMonth, retryInterval, terminationMode);
      } else if (retryInterval != null || terminationMode != null) {
        scheduleSettings = new ScheduleSettings(null, null, null, retryInterval, terminationMode);
      }

      return resource.updateSubscription(
          storeId,
          subscriptionId,
          new SubscriptionUpdateData(
              transactionTokenId,
              period,
              initialAmount,
              metadata,
              installmentPlan,
              subscriptionPlan,
              scheduleSettings,
              status,
              onlyDirectCurrency),
          idempotencyKey);
    }
  }

  public static class GetSubscriptionRequestBuilder
      extends AbstractGetSubscriptionRequestBuilder<
          GetSubscriptionRequestBuilder, SubscriptionsResource, FullSubscription> {

    public GetSubscriptionRequestBuilder(
        Retrofit retrofit, StoreId storeId, SubscriptionId subscriptionID) {
      super(retrofit, storeId, subscriptionID);
    }

    @Override
    protected Call<FullSubscription> getRequest(SubscriptionsResource resource) {
      return resource.getSubscription(storeId, subscriptionId, polling);
    }
  }

  public static class ListSubscriptionsMerchantRequestBuilder
      extends AbstractListSubscriptionsMerchantRequestBuilder<
          ListSubscriptionsMerchantRequestBuilder, SubscriptionsResource, Subscription> {

    public ListSubscriptionsMerchantRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    @Override
    protected Call<PaginatedList<Subscription>> getRequest(SubscriptionsResource resource) {
      return resource.listAllSubscriptions(getLimit(), getCursorDirection(), getCursor());
    }
  }

  public static class ListSubscriptionsRequestBuilder
      extends AbstractListSubscriptionsRequestBuilder<
          ListSubscriptionsRequestBuilder, SubscriptionsResource, Subscription> {

    public ListSubscriptionsRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit, storeId);
    }

    @Override
    protected Call<PaginatedList<Subscription>> getRequest(SubscriptionsResource resource) {
      return resource.listAllSubscriptions(storeId, getLimit(), getCursorDirection(), getCursor());
    }
  }

  public static class ListChargesForPaymentRequestBuilder
      extends AbstractListChargesForPaymentRequestBuilder<
          AbstractListChargesForPaymentRequestBuilder, SubscriptionsResource, Charge> {

    public ListChargesForPaymentRequestBuilder(
        Retrofit retrofit,
        StoreId storeId,
        SubscriptionId subscriptionId,
        ScheduledPaymentId paymentId) {
      super(retrofit, storeId, subscriptionId, paymentId);
    }

    @Override
    protected Call<PaginatedList<Charge>> getRequest(SubscriptionsResource resource) {
      return resource.listChargesForPayment(
          storeId, subscriptionId, paymentId, getLimit(), getCursorDirection(), getCursor());
    }
  }

  public static class DeleteSubscriptionRequestBuilder
      extends AbstractDeleteSubscriptionRequestBuilder<
          DeleteSubscriptionRequestBuilder, SubscriptionsResource> {

    public DeleteSubscriptionRequestBuilder(
        Retrofit retrofit, StoreId storeId, SubscriptionId subscriptionId) {
      super(retrofit, storeId, subscriptionId);
    }

    @Override
    protected Call<Void> getRequest(SubscriptionsResource resource) {
      return resource.deleteSubscription(storeId, subscriptionId);
    }
  }

  public static class ListScheduledPaymentsRequestBuilder
      extends AbstractListScheduledPaymentsRequestBuilder<
          ListScheduledPaymentsRequestBuilder, SubscriptionsResource, ScheduledPayment> {

    public ListScheduledPaymentsRequestBuilder(
        Retrofit retrofit, StoreId storeId, SubscriptionId subscriptionId) {
      super(retrofit, storeId, subscriptionId);
    }

    @Override
    protected Call<PaginatedList<ScheduledPayment>> getRequest(SubscriptionsResource resource) {
      return resource.listPayments(
          storeId, subscriptionId, getLimit(), getCursorDirection(), getCursor());
    }
  }

  public static class GetScheduledPaymentRequestBuilder
      extends AbstractGetScheduledPaymentRequestBuilder<
          GetScheduledPaymentRequestBuilder, SubscriptionsResource, ScheduledPayment> {

    public GetScheduledPaymentRequestBuilder(
        Retrofit retrofit,
        StoreId storeId,
        SubscriptionId subscriptionId,
        ScheduledPaymentId scheduledPaymentId) {
      super(retrofit, storeId, subscriptionId, scheduledPaymentId);
    }

    @Override
    protected Call<ScheduledPayment> getRequest(SubscriptionsResource resource) {
      return resource.getPayment(storeId, subscriptionId, paymentId);
    }
  }

  public static class UpdateScheduledPaymentRequestBuilder
      extends AbstractUpdateScheduledPaymentRequestBuilder<
          UpdateScheduledPaymentRequestBuilder, SubscriptionsResource, ScheduledPayment> {

    public UpdateScheduledPaymentRequestBuilder(
        Retrofit retrofit,
        StoreId storeId,
        SubscriptionId subscriptionId,
        ScheduledPaymentId scheduledPaymentId) {
      super(retrofit, storeId, subscriptionId, scheduledPaymentId);
    }

    @Override
    protected Call<ScheduledPayment> getRequest(SubscriptionsResource resource) {
      return resource.updatePayment(
          storeId,
          subscriptionId,
          paymentId,
          new ScheduledPaymentPatchData(isPaid),
          idempotencyKey);
    }
  }

  public static class SimulateInstallmentsPlanRequestBuilder
      extends AbstractSimulateInstallmentsPlanRequestBuilder<
          SimulateInstallmentsPlanRequestBuilder, SubscriptionsResource, PaymentsPlan> {

    public SimulateInstallmentsPlanRequestBuilder(
        Retrofit retrofit,
        MoneyLike money,
        PaymentTypeName paymentType,
        SubscriptionPeriod period) {
      super(retrofit, money, paymentType, period);
    }

    public SimulateInstallmentsPlanRequestBuilder(
        Retrofit retrofit,
        StoreId storeId,
        MoneyLike money,
        PaymentTypeName paymentType,
        SubscriptionPeriod period) {
      super(retrofit, storeId, money, paymentType, period);
    }

    @Override
    protected Call<PaymentsPlan> getRequest(SubscriptionsResource resource) {
      SubscriptionPlanSimulationRequest requestData =
          new SubscriptionPlanSimulationRequest(
              installmentPlan,
              subscriptionPlan,
              money,
              initialAmount,
              new ScheduleSettings(
                  startOn, zoneId, preserveEndOfMonth, retryInterval, terminationMode),
              paymentType,
              period);

      if (storeId == null) {
        return resource.simulateSubscriptionPlan(requestData, idempotencyKey);
      }

      return resource.simulateSubscriptionPlan(storeId, requestData, idempotencyKey);
    }
  }

  public static class ListSubscriptionChargesRequestBuilder
      extends AbstractListSubscriptionChargesRequestBuilder<
          AbstractListSubscriptionChargesRequestBuilder, SubscriptionsResource, Charge> {

    public ListSubscriptionChargesRequestBuilder(
        Retrofit retrofit, StoreId storeId, SubscriptionId subscriptionId) {
      super(retrofit, storeId, subscriptionId);
    }

    @Override
    protected Call<PaginatedList<Charge>> getRequest(SubscriptionsResource resource) {
      return resource.listSubscriptionCharges(
          storeId, subscriptionId, getLimit(), getCursorDirection(), getCursor());
    }
  }

  public static ResourceMonitor<FullSubscription> createSubscriptionCompletionMonitor(
      Retrofit retrofit, StoreId storeId, SubscriptionId subscriptionId) {
    return new ResourceMonitor<>(
        new GetSubscriptionRequestBuilder(retrofit, storeId, subscriptionId).withPolling(true),
        new ResourcePredicate<FullSubscription>() {
          @Override
          public boolean test(FullSubscription resource) {
            return resource.getStatus() != SubscriptionStatus.UNVERIFIED;
          }
        });
  }
}
