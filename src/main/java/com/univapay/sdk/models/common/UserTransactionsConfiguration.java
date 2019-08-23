package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;

public class UserTransactionsConfiguration {
  @SerializedName("enabled")
  Boolean enabled;

  @SerializedName("notify_customer")
  Boolean notifyCustomer;

  public UserTransactionsConfiguration(Boolean enabled, Boolean notifyCustomer) {
    this.enabled = enabled;
    this.notifyCustomer = notifyCustomer;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public Boolean getNotifyCustomer() {
    return notifyCustomer;
  }
}
