package com.univapay.sdk.models.response.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.ChargeId;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;
import lombok.Getter;

@Getter
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

  @SerializedName("charge_id")
  private UUID chargeId;

  public ChargeId getChargeId() {
    if (chargeId != null) {
      return new ChargeId(chargeId);
    } else {
      return null;
    }
  }
}
