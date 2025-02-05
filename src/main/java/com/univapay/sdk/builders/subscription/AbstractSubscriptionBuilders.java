package com.univapay.sdk.builders.subscription;

import com.univapay.sdk.builders.IdempotentRetrofitRequestBuilder;
import com.univapay.sdk.builders.Polling;
import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilderPaginated;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.ScheduledPaymentId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.common.threeDs.ChargeThreeDsCreateData;
import com.univapay.sdk.models.common.threeDs.ChargeThreeDsMode;
import com.univapay.sdk.models.request.subscription.PaymentPlanRequest;
import com.univapay.sdk.models.response.PaymentsPlan;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.models.response.subscription.ScheduledPayment;
import com.univapay.sdk.models.response.subscription.Subscription;
import com.univapay.sdk.models.response.subscription.SubscriptionTerminationMode;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.SubscriptionPeriod;
import com.univapay.sdk.types.SubscriptionStatus;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import lombok.Getter;
import retrofit2.Retrofit;

public abstract class AbstractSubscriptionBuilders {

  public abstract static class AbstractCreateSubscriptionRequestBuilder<
          B extends AbstractCreateSubscriptionRequestBuilder, R, M extends Subscription>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    @Getter protected TransactionTokenId token;

    @Getter protected MoneyLike money;
    @Getter protected SubscriptionPeriod period;
    @Getter protected BigInteger initialAmount;
    @Getter protected Boolean onlyDirectCurrency;
    @Getter protected Map<String, Object> metadata;
    @Getter protected PaymentPlanRequest installmentPlan;
    @Getter protected PaymentPlanRequest subscriptionPlan;
    @Getter protected LocalDate startOn;
    @Getter protected ZoneId zoneId;
    @Getter protected Boolean preserveEndOfMonth;
    @Getter protected Duration firstChargeCaptureAfter;
    @Getter protected Boolean firstChargeAuthorizationOnly;
    @Getter protected Duration retryInterval;
    @Getter protected SubscriptionTerminationMode terminationMode;
    @Getter protected ChargeThreeDsCreateData threeDs;

    public AbstractCreateSubscriptionRequestBuilder(
        Retrofit retrofit, TransactionTokenId token, MoneyLike money, SubscriptionPeriod period) {
      super(retrofit);
      this.token = token;
      this.money = money;
      this.period = period;
    }

    public B withInitialAmount(BigInteger initialAmount) {
      this.initialAmount = initialAmount;
      return (B) this;
    }

    public B withOnlyDirectCurrency(Boolean onlyDirectCurrency) {
      this.onlyDirectCurrency = onlyDirectCurrency;
      return (B) this;
    }

    public B withMetadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return (B) this;
    }

    public B withInstallmentPlan(PaymentPlanRequest installmentPlan) {
      this.installmentPlan = installmentPlan;
      return (B) this;
    }

    public B withSubscriptionPlan(PaymentPlanRequest subscriptionPlan) {
      this.subscriptionPlan = subscriptionPlan;
      return (B) this;
    }

    public B withStartOn(LocalDate startOn) {
      this.startOn = startOn;
      return (B) this;
    }

    public B withZoneId(ZoneId zoneId) {
      this.zoneId = zoneId;
      return (B) this;
    }

    public B withPreserveEndOfMoth(Boolean preserveEndOfMonth) {
      this.preserveEndOfMonth = preserveEndOfMonth;
      return (B) this;
    }

    public B withFirstChargeCaptureAfter(Duration firstChargeCaptureAfter) {
      this.firstChargeCaptureAfter = firstChargeCaptureAfter;
      return (B) this;
    }

    public B withFirstChargeAuthorizationOnly(Boolean firstChargeAuthorizationOnly) {
      this.firstChargeAuthorizationOnly = firstChargeAuthorizationOnly;
      return (B) this;
    }

    public B withTerminationMode(SubscriptionTerminationMode terminationMode) {
      this.terminationMode = terminationMode;
      return (B) this;
    }

    public B withRetryInterval(Duration retryInterval) {
      this.retryInterval = retryInterval;
      return (B) this;
    }

    public B withThreeDs(String redirectEndpoint) {
      this.threeDs =
          new ChargeThreeDsCreateData(null, redirectEndpoint, null, null, null, null, null, null);
      return (B) this;
    }

    public B withThreeDs(ChargeThreeDsMode mode, String redirectEndpoint) {
      this.threeDs =
          new ChargeThreeDsCreateData(mode, redirectEndpoint, null, null, null, null, null, null);
      return (B) this;
    }

    public B withThreeDs(ChargeThreeDsMode mode) {
      this.threeDs = new ChargeThreeDsCreateData(mode, null, null, null, null, null, null, null);
      return (B) this;
    }

    public B withProvidedThreeDs(
        String authenticationValue,
        String eci,
        String dsTransactionId,
        String serverTransactionId,
        String messageVersion,
        String transactionStatus) {
      this.threeDs =
          new ChargeThreeDsCreateData(
              null,
              null,
              authenticationValue,
              eci,
              dsTransactionId,
              serverTransactionId,
              messageVersion,
              transactionStatus);
      return (B) this;
    }
  }

  @Getter
  public abstract static class AbstractUpdateSubscriptionRequestBuilder<
          B extends AbstractUpdateSubscriptionRequestBuilder, R, M extends Subscription>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {
    protected StoreId storeId;
    protected SubscriptionId subscriptionId;
    protected TransactionTokenId transactionTokenId;
    protected BigInteger initialAmount;
    protected Boolean onlyDirectCurrency;
    protected SubscriptionStatus status;
    protected Map<String, Object> metadata;
    protected PaymentPlanRequest installmentPlan;
    protected PaymentPlanRequest subscriptionPlan;
    protected LocalDate startOn;
    protected Boolean preserveEndOfMonth;
    protected SubscriptionPeriod period;

    protected Duration retryInterval;
    protected SubscriptionTerminationMode terminationMode;

    public AbstractUpdateSubscriptionRequestBuilder(
        Retrofit retrofit, StoreId storeId, SubscriptionId subscriptionId) {
      super(retrofit);
      this.storeId = storeId;
      this.subscriptionId = subscriptionId;
    }

    public B withInitialAmount(BigInteger initialAmount) {
      this.initialAmount = initialAmount;
      return (B) this;
    }

    public B withTransactionToken(TransactionTokenId transactionTokenId) {
      this.transactionTokenId = transactionTokenId;
      return (B) this;
    }

    public B withOnlyDirectCurrency(Boolean onlyDirectCurrency) {
      this.onlyDirectCurrency = onlyDirectCurrency;
      return (B) this;
    }

    public B withStatus(SubscriptionStatus status) {
      this.status = status;
      return (B) this;
    }

    public B withMetadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return (B) this;
    }

    public B withInstallmentPlan(PaymentPlanRequest installmentPlan) {
      this.installmentPlan = installmentPlan;
      return (B) this;
    }

    public B withSubscriptionPlan(PaymentPlanRequest subscriptionPlan) {
      this.subscriptionPlan = subscriptionPlan;
      return (B) this;
    }

    public B withStartOn(LocalDate startOn) {
      this.startOn = startOn;
      return (B) this;
    }

    public B withPreserveEndOfMonth(Boolean preserveEndOfMonth) {
      this.preserveEndOfMonth = preserveEndOfMonth;
      return (B) this;
    }

    public B withPeriod(SubscriptionPeriod period) {
      this.period = period;
      return (B) this;
    }

    public B withTerminationMode(SubscriptionTerminationMode terminationMode) {
      this.terminationMode = terminationMode;
      return (B) this;
    }

    public B withRetryInterval(Duration retryInterval) {
      this.retryInterval = retryInterval;
      return (B) this;
    }
  }

  public abstract static class AbstractGetSubscriptionRequestBuilder<
          B extends AbstractGetSubscriptionRequestBuilder, R, M extends Subscription>
      extends RetrofitRequestBuilder<M, R> implements Polling<B> {

    protected StoreId storeId;
    protected SubscriptionId subscriptionId;
    protected Boolean polling;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected SubscriptionId getSubscriptionId() {
      return subscriptionId;
    }

    public AbstractGetSubscriptionRequestBuilder(
        Retrofit retrofit, StoreId storeId, SubscriptionId subscriptionID) {
      super(retrofit);
      this.storeId = storeId;
      this.subscriptionId = subscriptionID;
    }

    @Override
    public B withPolling(boolean polling) {
      this.polling = polling;
      return (B) this;
    }
  }

  public abstract static class AbstractListSubscriptionsMerchantRequestBuilder<
          B extends AbstractListSubscriptionsMerchantRequestBuilder, R, M extends Subscription>
      extends RetrofitRequestBuilderPaginated<M, R, B, SubscriptionId> {

    public AbstractListSubscriptionsMerchantRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }
  }

  public abstract static class AbstractListSubscriptionsRequestBuilder<
          B extends AbstractListSubscriptionsRequestBuilder, R, M extends Subscription>
      extends RetrofitRequestBuilderPaginated<M, R, B, SubscriptionId> {

    protected StoreId storeId;

    protected StoreId getStoreId() {
      return storeId;
    }

    public AbstractListSubscriptionsRequestBuilder(Retrofit retrofit, StoreId storeId) {
      super(retrofit);
      this.storeId = storeId;
    }
  }

  public abstract static class AbstractListChargesForPaymentRequestBuilder<
          B extends AbstractListChargesForPaymentRequestBuilder, R, M extends Charge>
      extends RetrofitRequestBuilderPaginated<M, R, B, ChargeId> {
    protected StoreId storeId;
    protected SubscriptionId subscriptionId;
    protected ScheduledPaymentId paymentId;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected SubscriptionId getSubscriptionId() {
      return subscriptionId;
    }

    public AbstractListChargesForPaymentRequestBuilder(
        Retrofit retrofit,
        StoreId storeId,
        SubscriptionId subscriptionId,
        ScheduledPaymentId paymentId) {
      super(retrofit);
      this.storeId = storeId;
      this.subscriptionId = subscriptionId;
      this.paymentId = paymentId;
    }
  }

  public abstract static class AbstractDeleteSubscriptionRequestBuilder<
          B extends AbstractDeleteSubscriptionRequestBuilder, R>
      extends RetrofitRequestBuilder<Void, R> {
    protected StoreId storeId;
    protected SubscriptionId subscriptionId;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected SubscriptionId getSubscriptionId() {
      return subscriptionId;
    }

    public AbstractDeleteSubscriptionRequestBuilder(
        Retrofit retrofit, StoreId storeId, SubscriptionId subscriptionId) {
      super(retrofit);
      this.storeId = storeId;
      this.subscriptionId = subscriptionId;
    }
  }

  public abstract static class AbstractListScheduledPaymentsRequestBuilder<
          B extends AbstractListScheduledPaymentsRequestBuilder, R, M extends ScheduledPayment>
      extends RetrofitRequestBuilderPaginated<M, R, B, ScheduledPaymentId> {

    protected StoreId storeId;
    protected SubscriptionId subscriptionId;

    public AbstractListScheduledPaymentsRequestBuilder(
        Retrofit retrofit, StoreId storeId, SubscriptionId subscriptionId) {
      super(retrofit);
      this.storeId = storeId;
      this.subscriptionId = subscriptionId;
    }
  }

  public abstract static class AbstractGetScheduledPaymentRequestBuilder<
          B extends AbstractGetScheduledPaymentRequestBuilder, R, M extends ScheduledPayment>
      extends RetrofitRequestBuilder<M, R> {

    @Getter protected StoreId storeId;
    @Getter protected SubscriptionId subscriptionId;
    protected ScheduledPaymentId paymentId;

    public ScheduledPaymentId getScheduledPayment() {
      return paymentId;
    }

    public AbstractGetScheduledPaymentRequestBuilder(
        Retrofit retrofit,
        StoreId storeId,
        SubscriptionId subscriptionId,
        ScheduledPaymentId scheduledPaymentId) {
      super(retrofit);
      this.storeId = storeId;
      this.subscriptionId = subscriptionId;
      this.paymentId = scheduledPaymentId;
    }
  }

  public abstract static class AbstractUpdateScheduledPaymentRequestBuilder<
          B extends AbstractUpdateScheduledPaymentRequestBuilder, R, M extends ScheduledPayment>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    @Getter protected StoreId storeId;
    @Getter protected SubscriptionId subscriptionId;
    @Getter protected ScheduledPaymentId paymentId;
    protected Boolean isPaid;

    public AbstractUpdateScheduledPaymentRequestBuilder(
        Retrofit retrofit,
        StoreId storeId,
        SubscriptionId subscriptionId,
        ScheduledPaymentId scheduledPaymentId) {
      super(retrofit);
      this.storeId = storeId;
      this.subscriptionId = subscriptionId;
      this.paymentId = scheduledPaymentId;
    }

    public Boolean getPaid() {
      return isPaid;
    }

    public B withIsPaid(Boolean isPaid) {
      this.isPaid = isPaid;
      return (B) this;
    }
  }

  @Getter
  public abstract static class AbstractSimulateInstallmentsPlanRequestBuilder<
          B extends AbstractSimulateInstallmentsPlanRequestBuilder, R, M extends PaymentsPlan>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    protected PaymentPlanRequest installmentPlan;
    protected PaymentPlanRequest subscriptionPlan;
    protected MoneyLike money;
    protected BigInteger initialAmount;
    protected LocalDate startOn;
    protected ZoneId zoneId;
    protected Boolean preserveEndOfMonth;
    protected PaymentTypeName paymentType;
    protected SubscriptionPeriod period;
    protected StoreId storeId;

    protected Duration retryInterval;
    protected SubscriptionTerminationMode terminationMode;

    public AbstractSimulateInstallmentsPlanRequestBuilder(
        Retrofit retrofit,
        MoneyLike money,
        PaymentTypeName paymentType,
        SubscriptionPeriod period) {
      super(retrofit);
      this.money = money;
      this.paymentType = paymentType;
      this.period = period;
    }

    public AbstractSimulateInstallmentsPlanRequestBuilder(
        Retrofit retrofit,
        StoreId storeId,
        MoneyLike money,
        PaymentTypeName paymentType,
        SubscriptionPeriod period) {
      super(retrofit);
      this.storeId = storeId;
      this.money = money;
      this.paymentType = paymentType;
      this.period = period;
    }

    public B withInstallmentPlan(PaymentPlanRequest installmentPlan) {
      this.installmentPlan = installmentPlan;
      return (B) this;
    }

    public B withSubscriptionPlan(PaymentPlanRequest subscriptionPlan) {
      this.subscriptionPlan = subscriptionPlan;
      return (B) this;
    }

    public B withInitialAmount(BigInteger initialAmount) {
      this.initialAmount = initialAmount;
      return (B) this;
    }

    public B withStartOn(LocalDate startOn) {
      this.startOn = startOn;
      return (B) this;
    }

    public B withZoneId(ZoneId zoneId) {
      this.zoneId = zoneId;
      return (B) this;
    }

    public B withPreserveEndOfMonth(Boolean preserveEndOfMonth) {
      this.preserveEndOfMonth = preserveEndOfMonth;
      return (B) this;
    }

    public B withTerminationMode(SubscriptionTerminationMode terminationMode) {
      this.terminationMode = terminationMode;
      return (B) this;
    }

    public B withRetryInterval(Duration retryInterval) {
      this.retryInterval = retryInterval;
      return (B) this;
    }
  }

  public abstract static class AbstractListSubscriptionChargesRequestBuilder<
          B extends AbstractListSubscriptionChargesRequestBuilder, R, M extends Charge>
      extends RetrofitRequestBuilderPaginated<M, R, B, ChargeId> {
    protected StoreId storeId;
    protected SubscriptionId subscriptionId;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected SubscriptionId getSubscriptionId() {
      return subscriptionId;
    }

    public AbstractListSubscriptionChargesRequestBuilder(
        Retrofit retrofit, StoreId storeId, SubscriptionId subscriptionId) {
      super(retrofit);
      this.storeId = storeId;
      this.subscriptionId = subscriptionId;
    }
  }
}
