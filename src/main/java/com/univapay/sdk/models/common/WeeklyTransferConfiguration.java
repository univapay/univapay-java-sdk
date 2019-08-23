package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.DayOfWeek;

public class WeeklyTransferConfiguration {
  @SerializedName("closing_day")
  private DayOfWeek closingDay;

  @SerializedName("payout_day")
  private DayOfWeek payoutDay;

  public DayOfWeek getClosingDay() {
    return closingDay;
  }

  public DayOfWeek getPayoutDay() {
    return payoutDay;
  }
}
