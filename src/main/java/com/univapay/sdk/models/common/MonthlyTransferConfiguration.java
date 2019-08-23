package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.DayOfMonth;

public class MonthlyTransferConfiguration {
  @SerializedName("day_of_month")
  private Integer dayOfMonth;

  public DayOfMonth getDayOfMonth() {
    return new DayOfMonth(dayOfMonth);
  }
}
