package com.univapay.sdk.models.common.stores;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import java.time.Period;

public class SecurityConfiguration {
  @SerializedName("inspect_suspicious_login_after")
  private Period inspectSuspiciousLoginAfter;

  @SerializedName("refund_percent_limit")
  private BigDecimal refundPercentLimit;

  @SerializedName("limit_charge_by_card_configuration")
  private LimitChargeByCardConfiguration limitChargeByCardConfiguration;

  @SerializedName("confirmation_required")
  private Boolean confirmationRequired;

  public Period getInspectSuspiciousLoginAfter() {
    return inspectSuspiciousLoginAfter;
  }

  public BigDecimal getRefundPercentLimit() {
    return refundPercentLimit;
  }

  public LimitChargeByCardConfiguration getLimitChargeByCardConfiguration() {
    return limitChargeByCardConfiguration;
  }

  public Boolean getConfirmationRequired() {
    return confirmationRequired;
  }

  public SecurityConfiguration() {}

  public SecurityConfiguration(Period inspectSuspiciousLoginAfter) {
    this.inspectSuspiciousLoginAfter = inspectSuspiciousLoginAfter;
  }

  public SecurityConfiguration withConfirmationRequired(Boolean confirmationRequired) {
    this.confirmationRequired = confirmationRequired;
    return this;
  }
}
