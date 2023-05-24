package com.univapay.sdk.models.request.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.response.subscription.ScheduleSettings;
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.types.SubscriptionPeriod;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.UUID;

public abstract class SubscriptionRequestData {

  @SerializedName("transaction_token_id")
  private UUID transactionTokenId;

  @SerializedName("period")
  private SubscriptionPeriod period;

  @SerializedName("initial_amount")
  private BigInteger initialAmount;

  @SerializedName("only_direct_currency")
  private Boolean onlyDirectCurrency;

  @SerializedName("descriptor")
  private String descriptor;

  @SerializedName("metadata")
  private MetadataMap metadata;

  @SerializedName("installment_plan")
  private PaymentPlanRequest installmentPlan;

  @SerializedName("subscription_plan")
  private PaymentPlanRequest subscriptionPlan;

  @SerializedName("schedule_settings")
  private ScheduleSettings scheduleSettings;

  @SerializedName("subsequent_cycles_start")
  private OffsetDateTime subsequentCyclesStart;

  public SubscriptionRequestData(
      TransactionTokenId transactionTokenId,
      SubscriptionPeriod period,
      BigInteger initialAmount,
      MetadataMap metadata,
      PaymentPlanRequest installmentPlan,
      PaymentPlanRequest subscriptionPlan,
      ScheduleSettings scheduleSettings,
      OffsetDateTime subsequentCyclesStart,
      Boolean onlyDirectCurrency,
      String descriptor) {
    if (transactionTokenId != null) {
      this.transactionTokenId = transactionTokenId.toUUID();
    }
    this.period = period;
    this.initialAmount = initialAmount;
    this.metadata = metadata;
    this.installmentPlan = installmentPlan;
    this.subscriptionPlan = subscriptionPlan;
    this.scheduleSettings = scheduleSettings;
    this.subsequentCyclesStart = subsequentCyclesStart;
    this.onlyDirectCurrency = onlyDirectCurrency;
    this.descriptor = descriptor;
  }

  public TransactionTokenId getTransactionTokenId() {
    return new TransactionTokenId(transactionTokenId);
  }

  public SubscriptionPeriod getPeriod() {
    return period;
  }

  public BigInteger getInitialAmount() {
    return initialAmount;
  }

  public Boolean getOnlyDirectCurrency() {
    return onlyDirectCurrency;
  }

  public String getDescriptor() {
    return descriptor;
  }

  public MetadataMap getMetadata() {
    return metadata;
  }

  public PaymentPlanRequest getInstallmentPlan() {
    return installmentPlan;
  }

  public PaymentPlanRequest getSubscriptionPlan() {
    return subscriptionPlan;
  }

  public ScheduleSettings getScheduleSettings() {
    return scheduleSettings;
  }

  public OffsetDateTime getSubsequentCyclesStart() {
    return subsequentCyclesStart;
  }
}
