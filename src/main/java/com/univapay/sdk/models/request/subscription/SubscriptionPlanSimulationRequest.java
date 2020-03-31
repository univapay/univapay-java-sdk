package com.univapay.sdk.models.request.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.response.subscription.ScheduleSettings;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.SubscriptionPeriod;
import java.math.BigInteger;

public class SubscriptionPlanSimulationRequest {

  @SerializedName("installment_plan")
  private InstallmentPlanRequest installmentPlan;

  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("currency")
  private String currency;

  @SerializedName("initial_amount")
  private BigInteger initialAmount;

  @SerializedName("schedule_settings")
  private ScheduleSettings scheduleSettings;

  @SerializedName("payment_type")
  private PaymentTypeName paymentType;

  @SerializedName("period")
  private SubscriptionPeriod period;

  public SubscriptionPlanSimulationRequest(
      InstallmentPlanRequest installmentPlan,
      MoneyLike money,
      BigInteger initialAmount,
      ScheduleSettings scheduleSettings,
      PaymentTypeName paymentType,
      SubscriptionPeriod period) {
    this.installmentPlan = installmentPlan;
    this.amount = money.getAmount();
    this.currency = money.getCurrency();
    this.initialAmount = initialAmount;
    this.scheduleSettings = scheduleSettings;
    this.paymentType = paymentType;
    this.period = period;
  }

  public InstallmentPlanRequest getInstallmentPlan() {
    return installmentPlan;
  }

  public BigInteger getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public BigInteger getInitialAmount() {
    return initialAmount;
  }

  public ScheduleSettings getScheduleSettings() {
    return scheduleSettings;
  }

  public PaymentTypeName getPaymentType() {
    return paymentType;
  }

  public SubscriptionPeriod getPeriod() {
    return period;
  }
}
