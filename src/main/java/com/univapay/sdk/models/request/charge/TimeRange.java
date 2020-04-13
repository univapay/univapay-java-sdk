package com.univapay.sdk.models.request.charge;

import java.time.OffsetDateTime;

public class TimeRange {

  private OffsetDateTime fromDate;

  private OffsetDateTime toDate;

  public TimeRange() {}

  public TimeRange(OffsetDateTime fromDate, OffsetDateTime toDate) {
    this.fromDate = fromDate;
    this.toDate = toDate;
  }

  public TimeRange withFromDate(OffsetDateTime fromDate) {
    this.fromDate = fromDate;
    return this;
  }

  public TimeRange withToDate(OffsetDateTime toDate) {
    this.toDate = toDate;
    return this;
  }

  public OffsetDateTime getFromDate() {
    return fromDate;
  }

  public OffsetDateTime getToDate() {
    return toDate;
  }
}
