package com.univapay.sdk.models.request.configuration;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.DayOfMonth;

public class PreconfiguredMonthlySchedule extends PreconfiguredTransferSchedule {

  @SerializedName("day_of_month")
  private DayOfMonth dayOfMonth;

  public PreconfiguredMonthlySchedule(DayOfMonth dayOfMonth) {
    this.dayOfMonth = dayOfMonth;
  }

  public DayOfMonth getDayOfMonth() {
    return dayOfMonth;
  }

  @Override
  public String getConstant() {
    return "monthly";
  }
}
