package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;

public class UserTransactionsConfiguration {
  @SerializedName("enabled")
  private final Boolean enabled;

  @SerializedName("notify_customer")
  private final Boolean notifyCustomer;

  @SerializedName("notify_on_test")
  private final Boolean notifyOnTest;

  public UserTransactionsConfiguration(
      Boolean enabled, Boolean notifyCustomer, Boolean notifyOnTest) {
    this.enabled = enabled;
    this.notifyCustomer = notifyCustomer;
    this.notifyOnTest = notifyOnTest;
  }

  public UserTransactionsConfiguration() {
    this.enabled = null;
    this.notifyCustomer = null;
    this.notifyOnTest = null;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public Boolean getNotifyCustomer() {
    return notifyCustomer;
  }

  public Boolean getNotifyOnTest() {
    return notifyOnTest;
  }
}
