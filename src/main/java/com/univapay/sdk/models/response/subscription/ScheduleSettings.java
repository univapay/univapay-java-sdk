package com.univapay.sdk.models.response.subscription;

import com.google.gson.annotations.SerializedName;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleSettings {

  @SerializedName("start_on")
  private LocalDate startOn;

  @SerializedName("zone_id")
  private ZoneId zoneId;

  @SerializedName("preserve_end_of_month")
  private Boolean preserveEndOfMonth;

  @SerializedName("retry_interval")
  private Duration retryInterval;

  @SerializedName("termination_mode")
  private SubscriptionTerminationMode terminationMode;
}
