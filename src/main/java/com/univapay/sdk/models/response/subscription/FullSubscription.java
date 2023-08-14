package com.univapay.sdk.models.response.subscription;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import java.math.BigInteger;

public class FullSubscription extends Subscription {

  @SerializedName("amount_left")
  private BigInteger amountLeft;

  @SerializedName("amount_left_formatted")
  private BigDecimal amountLeftFormatted;

  @SerializedName("installment_plan")
  private PaymentPlan installmentPlan;

  @SerializedName("subscription_plan")
  private PaymentPlan subscriptionPlan;

  @SerializedName("next_payment")
  private ScheduledPayment nextPayment;

  @SerializedName("cycles_left")
  private Integer cyclesLeft;

  public BigInteger getAmountLeft() {
    return amountLeft;
  }

  public BigDecimal getAmountLeftFormatted() {
    return amountLeftFormatted;
  }

  public PaymentPlan getInstallmentPlan() {
    return installmentPlan;
  }

  public PaymentPlan getSubscriptionPlan() {
    return subscriptionPlan;
  }

  public ScheduledPayment getNextPayment() {
    return nextPayment;
  }

  public Integer getCyclesLeft() {
    return cyclesLeft;
  }
}
