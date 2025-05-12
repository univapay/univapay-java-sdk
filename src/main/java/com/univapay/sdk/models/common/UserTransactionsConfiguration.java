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

  @SerializedName("notify_on_recurring_token_cvv_failed")
  private final Boolean notifyOnRecurringTokenCvvFailed;

  @SerializedName("notify_on_webhook_failure")
  private final Boolean notifyOnWebhookFailure;

  @SerializedName("notify_on_webhook_disabled")
  private final Boolean notifyOnWebhookDisabled;

  @SerializedName("notify_user_on_failed_transactions")
  private final Boolean notifyUserOnFailedTransactions;

  @SerializedName("notify_customer_on_failed_transactions")
  private final Boolean notifyCustomerOnFailedTransactions;

  @SerializedName("notify_user_on_convenience_instructions")
  private final Boolean notifyUserOnConvenienceInstructions;

  @SerializedName("notify_on_subscriptions")
  private final Boolean notifyOnSubscriptions;

  @SerializedName("notify_on_authorizations")
  private final Boolean notifyOnAuthorizations;

  @SerializedName("notify_on_cvv_authorizations")
  private final Boolean notifyOnCvvAuthorizations;

  @SerializedName("notify_on_cancels")
  private final Boolean notifyOnCancels;

  @SerializedName("customer_refer_link_enabled")
  private final Boolean customerReferLinkEnabled;

  @SerializedName("notify_on_convenience_expiry")
  private final Boolean notifyOnConvenienceExpiry;

  public UserTransactionsConfiguration() {
    this.enabled = null;
    this.notifyCustomer = null;
    this.notifyOnTest = null;
    this.notifyOnRecurringTokenCreation = null;
    this.notifyOnRecurringTokenCvvFailed = null;
    this.notifyOnWebhookFailure = null;
    this.notifyOnWebhookDisabled = null;
    this.notifyUserOnFailedTransactions = null;
    this.notifyCustomerOnFailedTransactions = null;
    this.notifyUserOnConvenienceInstructions = null;
    this.notifyOnSubscriptions = null;
    this.notifyOnAuthorizations = null;
    this.notifyOnCvvAuthorizations = null;
    this.notifyOnCancels = null;
    this.customerReferLinkEnabled = null;
    this.notifyOnConvenienceExpiry = null;
  }
}
