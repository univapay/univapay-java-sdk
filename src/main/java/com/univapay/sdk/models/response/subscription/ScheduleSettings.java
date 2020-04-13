package com.univapay.sdk.models.response.subscription;

import com.google.gson.annotations.SerializedName;
import java.time.ZoneId;
import org.joda.time.LocalDate;

public class ScheduleSettings {

  @SerializedName("start_on")
  private LocalDate startOn;

  @SerializedName("zone_id")
  private ZoneId zoneId;

  @SerializedName("preserve_end_of_month")
  private Boolean preserveEndOfMonth;

  public ScheduleSettings(LocalDate startOn, ZoneId zoneId, Boolean preserveEndOfMonth) {
    this.startOn = startOn;
    this.zoneId = zoneId;
    this.preserveEndOfMonth = preserveEndOfMonth;
  }

  public LocalDate getStartOn() {
    return startOn;
  }

  public ZoneId getZoneId() {
    return zoneId;
  }

  public Boolean getPreserveEndOfMonth() {
    return preserveEndOfMonth;
  }
}
