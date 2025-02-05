package com.univapay.sdk.models.request.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.threeDs.ChargeThreeDsCreateData;
import com.univapay.sdk.models.response.subscription.ScheduleSettings;
import com.univapay.sdk.types.SubscriptionPeriod;
import java.math.BigInteger;
import java.time.Duration;
import java.util.Map;
import lombok.Getter;

@Getter
public class SubscriptionCreateData extends SubscriptionRequestData {

  @SerializedName("currency")
  private String currency;

  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("first_charge_capture_after")
  private Duration firstChargeCaptureAfter;

  @SerializedName("first_charge_authorization_only")
  private Boolean firstChargeAuthorizationOnly;

  @SerializedName("three_ds")
  private ChargeThreeDsCreateData threeDs;

  public SubscriptionCreateData(
      TransactionTokenId transactionTokenId,
      SubscriptionPeriod period,
      BigInteger initialAmount,
      Map<String, Object> metadata,
      PaymentPlanRequest installmentPlan,
      PaymentPlanRequest subscriptionPlan,
      ScheduleSettings scheduleSettings,
      MoneyLike money,
      Boolean onlyDirectCurrency,
      Duration firstChargeCaptureAfter,
      Boolean firstChargeAuthorizationOnly,
      ChargeThreeDsCreateData threeDs) {
    super(
        transactionTokenId,
        period,
        initialAmount,
        metadata,
        installmentPlan,
        subscriptionPlan,
        scheduleSettings,
        onlyDirectCurrency);
    this.currency = money.getCurrency();
    this.amount = money.getAmount();
    this.firstChargeCaptureAfter = firstChargeCaptureAfter;
    this.firstChargeAuthorizationOnly = firstChargeAuthorizationOnly;
    this.threeDs = threeDs;
  }
}
