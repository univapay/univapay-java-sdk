package com.univapay.sdk.models.request.configuration;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.DayOfMonth;
import com.univapay.sdk.types.DayOfWeek;
import com.univapay.sdk.types.TransferPeriod;
import com.univapay.sdk.types.WeekOfMonth;
import java.time.Period;

public class TransferScheduleConfigurationRequest {

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

  @SerializedName("preconfigured")
  private PreconfiguredTransferSchedule preconfigured;

  public TransferScheduleConfigurationRequest(
      Period waitPeriod,
      TransferPeriod period,
      DayOfWeek dayOfWeek,
      WeekOfMonth weekOfMonth,
      DayOfMonth dayOfMonth,
      Boolean fullPeriodRequired) {
    this.waitPeriod = waitPeriod;
    this.period = period;
    this.fullPeriodRequired = fullPeriodRequired;
    this.dayOfWeek = dayOfWeek;
    this.weekOfMonth = weekOfMonth;
    this.dayOfMonth = dayOfMonth;
  }

  public TransferScheduleConfigurationRequest(
      PreconfiguredTransferSchedule preconfigured, Boolean fullPeriodRequired) {
    this.fullPeriodRequired = fullPeriodRequired;
    this.preconfigured = preconfigured;
  }

  public TransferScheduleConfigurationRequest(PreconfiguredTransferSchedule preconfigured) {
    this.preconfigured = preconfigured;
  }

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

  public PreconfiguredTransferSchedule getPreconfigured() {
    return preconfigured;
  }
}
