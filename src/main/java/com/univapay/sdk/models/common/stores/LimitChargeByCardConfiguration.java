package com.univapay.sdk.models.common.stores;

import com.google.gson.annotations.SerializedName;
import org.threeten.bp.Duration;

public class LimitChargeByCardConfiguration {
  @SerializedName("quantity_of_charges")
  private Integer quantityOfCharges;

  @SerializedName("duration_window")
  private Duration durationWindow;

  public Integer getQuantityOfCharges() {
    return quantityOfCharges;
  }

  public Duration getDurationWindow() {
    return durationWindow;
  }

  public LimitChargeByCardConfiguration(Integer quantityOfCharges, Duration durationWindow) {
    this.quantityOfCharges = quantityOfCharges;
    this.durationWindow = durationWindow;
  }
}
