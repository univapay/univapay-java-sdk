package com.univapay.sdk.models.request.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.response.subscription.ScheduleSettings;
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.types.SubscriptionPeriod;
import java.math.BigInteger;
import java.time.Duration;
import java.time.OffsetDateTime;

public class SubscriptionCreateData extends SubscriptionRequestData {

  @SerializedName("currency")
  private String currency;

  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("first_charge_capture_after")
  private Duration firstChargeCaptureAfter;

  @SerializedName("first_charge_authorization_only")
  private Boolean firstChargeAuthorizationOnly;

  public SubscriptionCreateData(
      TransactionTokenId transactionTokenId,
      SubscriptionPeriod period,
      BigInteger initialAmount,
      MetadataMap metadata,
      PaymentPlanRequest installmentPlan,
      PaymentPlanRequest subscriptionPlan,
      ScheduleSettings scheduleSettings,
      OffsetDateTime subsequentCyclesStart,
      MoneyLike money,
      Boolean onlyDirectCurrency,
      String descriptor,
      Duration firstChargeCaptureAfter,
      Boolean firstChargeAuthorizationOnly) {
    super(
        transactionTokenId,
        period,
        initialAmount,
        metadata,
        installmentPlan,
        subscriptionPlan,
        scheduleSettings,
        subsequentCyclesStart,
        onlyDirectCurrency,
        descriptor);
    this.currency = money.getCurrency();
    this.amount = money.getAmount();
    this.firstChargeCaptureAfter = firstChargeCaptureAfter;
    this.firstChargeAuthorizationOnly = firstChargeAuthorizationOnly;
  }
}
