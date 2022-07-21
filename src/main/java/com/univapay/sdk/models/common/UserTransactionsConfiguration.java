package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserTransactionsConfiguration {
  @SerializedName("enabled")
  private final Boolean enabled;

  @SerializedName("notify_customer")
  private final Boolean notifyCustomer;

  @SerializedName("notify_on_test")
  private final Boolean notifyOnTest;

  @SerializedName("notify_on_recurring_token_creation")
  private final Boolean notifyOnRecurringTokenCreation;

  public UserTransactionsConfiguration() {
    this.enabled = null;
    this.notifyCustomer = null;
    this.notifyOnTest = null;
    this.notifyOnRecurringTokenCreation = null;
  }
}
