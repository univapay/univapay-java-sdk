package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class KonbiniConfiguration {

  @SerializedName("enabled")
  private final Boolean enabled;

  @SerializedName("expiration")
  private final Duration expirationPeriod;

  @SerializedName("expiration_time_shift")
  private final KonbiniExpirationTimeShift expirationTimeShift;
}
