package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RecurringTokenCVVConfirmation {

  @SerializedName("enabled")
  private Boolean enabled;

  @SerializedName("threshold")
  private List<MoneyLike> threshold;

  public RecurringTokenCVVConfirmation(Boolean enabled, List<MoneyLike> threshold) {
    this.enabled = enabled;
    this.threshold = threshold;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public List<MoneyLike> getThreshold() {
    return threshold;
  }
}
