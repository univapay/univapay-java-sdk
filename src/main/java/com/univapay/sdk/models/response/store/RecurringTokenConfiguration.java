package com.univapay.sdk.models.response.store;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.RecurringTokenCVVConfirmation;
import com.univapay.sdk.types.RecurringTokenPrivilege;
import java.time.Duration;

public class RecurringTokenConfiguration {

  public RecurringTokenConfiguration(
      RecurringTokenPrivilege recurringType,
      Duration chargeWaitPeriod,
      RecurringTokenCVVConfirmation recurringTokenCVVConfirmation) {
    this.recurringType = recurringType;
    this.chargeWaitPeriod = chargeWaitPeriod;
    this.recurringTokenCVVConfirmation = recurringTokenCVVConfirmation;
  }

  @SerializedName("recurring_type")
  private RecurringTokenPrivilege recurringType;

  @SerializedName("charge_wait_period")
  private Duration chargeWaitPeriod;

  @SerializedName("card_charge_cvv_confirmation")
  private RecurringTokenCVVConfirmation recurringTokenCVVConfirmation;

  public RecurringTokenPrivilege getRecurringType() {
    return recurringType;
  }

  public Duration getChargeWaitPeriod() {
    return chargeWaitPeriod;
  }

  public RecurringTokenCVVConfirmation getRecurringTokenCVVConfirmation() {
    return recurringTokenCVVConfirmation;
  }
}
