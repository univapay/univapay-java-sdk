package com.univapay.sdk.models.request.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.response.subscription.ScheduleSettings;
import com.univapay.sdk.types.SubscriptionPeriod;
import com.univapay.sdk.types.SubscriptionStatus;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.Map;

public class SubscriptionUpdateData extends SubscriptionRequestData {

  @SerializedName("status")
  SubscriptionStatus status;

  public SubscriptionUpdateData(
      TransactionTokenId transactionTokenId,
      SubscriptionPeriod period,
      BigInteger initialAmount,
      Map<String, String> metadata,
      PaymentPlanRequest installmentPlan,
      PaymentPlanRequest subscriptionPlan,
      ScheduleSettings scheduleSettings,
      OffsetDateTime subsequentCyclesStart,
      SubscriptionStatus status,
      Boolean onlyDirectCurrency,
      String descriptor) {
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
    this.status = status;
  }
}
