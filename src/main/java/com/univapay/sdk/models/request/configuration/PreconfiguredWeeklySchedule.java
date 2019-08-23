package com.univapay.sdk.models.request.configuration;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.DayOfWeek;

public class PreconfiguredWeeklySchedule extends PreconfiguredTransferSchedule {

  @SerializedName("closing_day")
  private DayOfWeek closingDay;

  @SerializedName("payout_day")
  private DayOfWeek payoutDay;

  public PreconfiguredWeeklySchedule(DayOfWeek closingDay, DayOfWeek payoutDay) {
    this.closingDay = closingDay;
    this.payoutDay = payoutDay;
  }

  public DayOfWeek getClosingDay() {
    return closingDay;
  }

  public DayOfWeek getPayoutDay() {
    return payoutDay;
  }
}
