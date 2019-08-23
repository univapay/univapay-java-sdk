package com.univapay.sdk.types;

public class DayOfMonth {
  private Integer day;

  public DayOfMonth(Integer day) {
    if (day < 1 || day > 31) {
      throw new IllegalArgumentException("Must be a valid day of the month");
    } else {
      this.day = day;
    }
  }

  public Integer getDay() {
    return day;
  }

  @Override
  public boolean equals(Object obj) {
    Boolean equals;
    try {
      equals = ((DayOfMonth) obj).day.equals(this.day);

    } catch (ClassCastException | NullPointerException e) {
      equals = false;
    }
    return equals;
  }
}
