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
  private InstallmentPlan installmentPlan;

  @SerializedName("next_payment")
  private ScheduledPayment nextPayment;

  @SerializedName("payments_left")
  private Integer paymentsLeft;

  public BigInteger getAmountLeft() {
    return amountLeft;
  }

  public BigDecimal getAmountLeftFormatted() {
    return amountLeftFormatted;
  }

  public InstallmentPlan getInstallmentPlan() {
    return installmentPlan;
  }

  public ScheduledPayment getNextPayment() {
    return nextPayment;
  }

  public Integer getPaymentsLeft() {
    return paymentsLeft;
  }
}
