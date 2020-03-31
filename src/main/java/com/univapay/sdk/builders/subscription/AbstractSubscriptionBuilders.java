package com.univapay.sdk.builders.subscription;

import com.univapay.sdk.builders.*;
import com.univapay.sdk.builders.DescriptorRetry;
import com.univapay.sdk.builders.IdempotentRetrofitRequestBuilder;
import com.univapay.sdk.builders.Polling;
import com.univapay.sdk.builders.Request;
import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilderPaginated;
import com.univapay.sdk.builders.RetrofitRequestCaller;
import com.univapay.sdk.builders.RetryUtils;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.ScheduledPaymentId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.request.subscription.InstallmentPlanRequest;
import com.univapay.sdk.models.response.PaymentsPlan;
import com.univapay.sdk.models.response.charge.Charge;
import com.univapay.sdk.models.response.subscription.ScheduledPayment;
import com.univapay.sdk.models.response.subscription.Subscription;
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.SubscriptionPeriod;
import com.univapay.sdk.types.SubscriptionStatus;
import com.univapay.sdk.utils.MetadataAdapter;
import java.math.BigInteger;
import java.util.Date;
import org.joda.time.LocalDate;
import org.threeten.bp.Duration;
import org.threeten.bp.ZoneId;
import retrofit2.Retrofit;

public abstract class AbstractSubscriptionBuilders {

  public abstract static class AbstractCreateSubscriptionRequestBuilder<
          B extends AbstractCreateSubscriptionRequestBuilder, R, M extends Subscription>
      extends IdempotentRetrofitRequestBuilder<M, R, B> implements DescriptorRetry<B, M> {

    protected TransactionTokenId token;
    protected MoneyLike money;
    protected SubscriptionPeriod period;
    protected BigInteger initialAmount;
    protected Boolean onlyDirectCurrency;
    protected Boolean ignoreDescriptorOnError = false;
    protected String descriptor;
    protected MetadataMap metadata;
    protected InstallmentPlanRequest installmentPlan;
    protected LocalDate startOn;
    protected ZoneId zoneId;
    protected Boolean preserveEndOfMonth;
    protected Date subsequentCyclesStart;
    protected Duration firstChargeCaptureAfter;
    protected Boolean firstChargeAuthorizationOnly;

    protected TransactionTokenId getToken() {
      return token;
    }

    protected MoneyLike getMoney() {
      return money;
    }

    protected SubscriptionPeriod getPeriod() {
      return period;
    }

    protected BigInteger getInitialAmount() {
      return initialAmount;
    }

    protected Boolean getOnlyDirectCurrency() {
      return onlyDirectCurrency;
    }

    protected String getDescriptor() {
      return descriptor;
    }

    protected MetadataMap getMetadata() {
      return metadata;
    }

    public InstallmentPlanRequest getInstallmentPlan() {
      return installmentPlan;
    }

    public LocalDate getStartOn() {
      return startOn;
    }

    public ZoneId getZoneId() {
      return zoneId;
    }

    public Boolean getPreserveEndOfMonth() {
      return preserveEndOfMonth;
    }

    public Date getSubsequentCyclesStart() {
      return subsequentCyclesStart;
    }

    protected Duration getFirstChargeCaptureAfter() {
      return firstChargeCaptureAfter;
    }

    protected Boolean getFirstChargeAuthorizationOnly() {
      return firstChargeAuthorizationOnly;
    }

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

    public B withDescriptor(String descriptor) {
      this.descriptor = descriptor;
      return (B) this;
    }

    public B withDescriptor(String descriptor, Boolean ignoreDescriptorOnError) {
      this.descriptor = descriptor;
      this.ignoreDescriptorOnError = ignoreDescriptorOnError;
      return (B) this;
    }

    public B withMetadata(MetadataMap metadata) {
      this.metadata = metadata;
      return (B) this;
    }

    public <T> B withMetadata(T metadata, MetadataAdapter<T> adapter) {
      this.metadata = adapter.serialize(metadata);
      return (B) this;
    }

    public B withInstallmentPlan(InstallmentPlanRequest installmentPlan) {
      this.installmentPlan = installmentPlan;
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

    public B withSubsequentCyclesStart(Date subsequentCyclesStart) {
      this.subsequentCyclesStart = subsequentCyclesStart;
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

    @Override
    public Request<M> build() {

      if (descriptor != null && ignoreDescriptorOnError) {
        Request<M> request = new RetrofitRequestCaller<>(retrofit, createCall());
        return retryIgnoringDescriptor(request);

      } else return super.build();
    }

    @Override
    public Request<M> retryIgnoringDescriptor(Request<M> originalRequest) {
      return RetryUtils.retryIgnoringDescriptor(originalRequest, this);
    }
  }

  public abstract static class AbstractUpdateSubscriptionRequestBuilder<
          B extends AbstractUpdateSubscriptionRequestBuilder, R, M extends Subscription>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {
    protected StoreId storeId;
    protected SubscriptionId subscriptionId;
    protected TransactionTokenId transactionTokenId;
    protected BigInteger initialAmount;
    protected Boolean onlyDirectCurrency;
    protected SubscriptionStatus status;
    protected String descriptor;
    protected MetadataMap metadata;
    protected InstallmentPlanRequest installmentPlan;
    protected LocalDate startOn;
    protected Boolean preserveEndOfMonth;
    protected SubscriptionPeriod period;
    protected Date subsequentCyclesStart;

    protected StoreId getStoreId() {
      return storeId;
    }

    protected SubscriptionId getSubscriptionId() {
      return subscriptionId;
    }

    protected TransactionTokenId getTransactionTokenId() {
      return transactionTokenId;
    }

    protected BigInteger getInitialAmount() {
      return initialAmount;
    }

    protected Boolean getOnlyDirectCurrency() {
      return onlyDirectCurrency;
    }

    public SubscriptionStatus getStatus() {
      return status;
    }

    protected String getDescriptor() {
      return descriptor;
    }

    protected MetadataMap getMetadata() {
      return metadata;
    }

    public InstallmentPlanRequest getInstallmentPlan() {
      return installmentPlan;
    }

    public LocalDate getStartOn() {
      return startOn;
    }

    public Boolean getPreserveEndOfMonth() {
      return preserveEndOfMonth;
    }

    public SubscriptionPeriod getPeriod() {
      return period;
    }

    public Date getSubsequentCyclesStart() {
      return subsequentCyclesStart;
    }

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

    public B withDescriptor(String descriptor) {
      this.descriptor = descriptor;
      return (B) this;
    }

    public B withMetadata(MetadataMap metadata) {
      this.metadata = metadata;
      return (B) this;
    }

    public <T> B withMetadata(T metadata, MetadataAdapter<T> adapter) {
      this.metadata = adapter.serialize(metadata);
      return (B) this;
    }

    public B withInstallmentPlan(InstallmentPlanRequest installmentPlan) {
      this.installmentPlan = installmentPlan;
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

    @Deprecated
    /** This method will be removed in future releases in favor of `withStartOn`. */
    public B withSubsequentCyclesStart(Date subsequentCyclesStart) {
      this.subsequentCyclesStart = subsequentCyclesStart;
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

    protected StoreId storeId;
    protected SubscriptionId subscriptionId;
    protected ScheduledPaymentId paymentId;

    public StoreId getStoreId() {
      return storeId;
    }

    public SubscriptionId getSubscriptionId() {
      return subscriptionId;
    }

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

    protected StoreId storeId;
    protected SubscriptionId subscriptionId;
    protected ScheduledPaymentId paymentId;
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

    public StoreId getStoreId() {
      return storeId;
    }

    public SubscriptionId getSubscriptionId() {
      return subscriptionId;
    }

    public ScheduledPaymentId getPaymentId() {
      return paymentId;
    }

    public Boolean getPaid() {
      return isPaid;
    }

    public B withIsPaid(Boolean isPaid) {
      this.isPaid = isPaid;
      return (B) this;
    }
  }

  public abstract static class AbstractSimulateInstallmentsPlanRequestBuilder<
          B extends AbstractSimulateInstallmentsPlanRequestBuilder, R, M extends PaymentsPlan>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    protected InstallmentPlanRequest installmentPlan;
    protected MoneyLike money;
    protected BigInteger initialAmount;
    protected LocalDate startOn;
    protected ZoneId zoneId;
    protected Boolean preserveEndOfMonth;
    protected PaymentTypeName paymentType;
    protected SubscriptionPeriod period;
    protected StoreId storeId;

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

    public InstallmentPlanRequest getInstallmentPlan() {
      return installmentPlan;
    }

    public MoneyLike getMoney() {
      return money;
    }

    public BigInteger getInitialAmount() {
      return initialAmount;
    }

    public LocalDate getStartOn() {
      return startOn;
    }

    public ZoneId getZoneId() {
      return zoneId;
    }

    public Boolean getPreserveEndOfMonth() {
      return preserveEndOfMonth;
    }

    public PaymentTypeName getPaymentType() {
      return paymentType;
    }

    public SubscriptionPeriod getPeriod() {
      return period;
    }

    public StoreId getStoreId() {
      return storeId;
    }

    public B withInstallmentPlan(InstallmentPlanRequest installmentPlan) {
      this.installmentPlan = installmentPlan;
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
