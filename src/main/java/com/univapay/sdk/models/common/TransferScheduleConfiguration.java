package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.DayOfMonth;
import com.univapay.sdk.types.DayOfWeek;
import com.univapay.sdk.types.TransferPeriod;
import com.univapay.sdk.types.WeekOfMonth;
import java.time.Period;

public class TransferScheduleConfiguration {
  @SerializedName("wait_period")
  private Period waitPeriod;

  @SerializedName("period")
  private TransferPeriod period;

  @SerializedName("full_period_required")
  private Boolean fullPeriodRequired;

  @SerializedName("day_of_week")
  private DayOfWeek dayOfWeek;

  @SerializedName("week_of_month")
  private WeekOfMonth weekOfMonth;

  @SerializedName("day_of_month")
  private DayOfMonth dayOfMonth;

  @SerializedName("weekly_closing_day")
  private DayOfWeek weeklyClosingDay;

  @SerializedName("weekly_payout_day")
  private DayOfWeek weeklyPayoutDay;

  public Period getWaitPeriod() {
    return waitPeriod;
  }

  public TransferPeriod getPeriod() {
    return period;
  }

  public Boolean getFullPeriodRequired() {
    return fullPeriodRequired;
  }

  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }

  public WeekOfMonth getWeekOfMonth() {
    return weekOfMonth;
  }

  public DayOfMonth getDayOfMonth() {
    return dayOfMonth;
  }

  public DayOfWeek getWeeklyClosingDay() {
    return weeklyClosingDay;
  }

  public DayOfWeek getWeeklyPayoutDay() {
    return weeklyPayoutDay;
  }
}
