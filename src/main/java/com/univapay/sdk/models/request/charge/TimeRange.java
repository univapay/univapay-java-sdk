package com.univapay.sdk.models.request.charge;

import java.util.Date;

public class TimeRange {

  private Date fromDate;

  private Date toDate;

  public TimeRange() {}

  public TimeRange(Date fromDate, Date toDate) {
    this.fromDate = fromDate;
    this.toDate = toDate;
  }

  public TimeRange withFromDate(Date fromDate) {
    this.fromDate = fromDate;
    return this;
  }

  public TimeRange withToDate(Date toDate) {
    this.toDate = toDate;
    return this;
  }

  public Date getFromDate() {
    return fromDate;
  }

  public Date getToDate() {
    return toDate;
  }
}
