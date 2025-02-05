package com.univapay.sdk.models.request.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.response.subscription.ScheduleSettings;
import com.univapay.sdk.types.SubscriptionPeriod;
import java.math.BigInteger;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;

public abstract class SubscriptionRequestData {

  @SerializedName("transaction_token_id")
  private UUID transactionTokenId;

  @Getter
  @SerializedName("period")
  private SubscriptionPeriod period;

  @Getter
  @SerializedName("initial_amount")
  private BigInteger initialAmount;

  @Getter
  @SerializedName("only_direct_currency")
  private Boolean onlyDirectCurrency;

  @Getter
  @SerializedName("metadata")
  private Map<String, Object> metadata;

  @Getter
  @SerializedName("installment_plan")
  private PaymentPlanRequest installmentPlan;

  @Getter
  @SerializedName("subscription_plan")
  private PaymentPlanRequest subscriptionPlan;

  @Getter
  @SerializedName("schedule_settings")
  private ScheduleSettings scheduleSettings;

  public SubscriptionRequestData(
      TransactionTokenId transactionTokenId,
      SubscriptionPeriod period,
      BigInteger initialAmount,
      Map<String, Object> metadata,
      PaymentPlanRequest installmentPlan,
      PaymentPlanRequest subscriptionPlan,
      ScheduleSettings scheduleSettings,
      Boolean onlyDirectCurrency) {
    if (transactionTokenId != null) {
      this.transactionTokenId = transactionTokenId.toUUID();
    }
    this.period = period;
    this.initialAmount = initialAmount;
    this.metadata = metadata;
    this.installmentPlan = installmentPlan;
    this.subscriptionPlan = subscriptionPlan;
    this.scheduleSettings = scheduleSettings;
    this.onlyDirectCurrency = onlyDirectCurrency;
  }

  public TransactionTokenId getTransactionTokenId() {
    return new TransactionTokenId(transactionTokenId);
  }
}
