package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class KonbiniExpirationTimeShift {

  @SerializedName("enabled")
  private final Boolean enabled;
}
