package com.univapay.sdk.models.common.stores;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.SubscriptionPeriod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LimitRefundBySalesConfiguration {
  @SerializedName("enabled")
  private Boolean enabled;

  @SerializedName("period")
  private SubscriptionPeriod period;

  @SerializedName("rolling_window")
  private Boolean rollingWindow;
}
