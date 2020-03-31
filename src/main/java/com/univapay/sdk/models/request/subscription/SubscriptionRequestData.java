package com.univapay.sdk.models.request.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.response.subscription.ScheduleSettings;
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.types.SubscriptionPeriod;
import java.math.BigInteger;
import java.util.Date;
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
  private InstallmentPlanRequest installmentPlan;

  @SerializedName("schedule_settings")
  private ScheduleSettings scheduleSettings;

  @SerializedName("subsequent_cycles_start")
  private Date subsequentCyclesStart;

  public SubscriptionRequestData(
      TransactionTokenId transactionTokenId,
      SubscriptionPeriod period,
      BigInteger initialAmount,
      MetadataMap metadata,
      InstallmentPlanRequest installmentPlan,
      ScheduleSettings scheduleSettings,
      Date subsequentCyclesStart,
      Boolean onlyDirectCurrency,
      String descriptor) {
    if (transactionTokenId != null) {
      this.transactionTokenId = transactionTokenId.toUUID();
    }
    this.period = period;
    this.initialAmount = initialAmount;
    this.metadata = metadata;
    this.installmentPlan = installmentPlan;
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

  public InstallmentPlanRequest getInstallmentPlan() {
    return installmentPlan;
  }

  public ScheduleSettings getScheduleSettings() {
    return scheduleSettings;
  }

  public Date getSubsequentCyclesStart() {
    return subsequentCyclesStart;
  }
}
