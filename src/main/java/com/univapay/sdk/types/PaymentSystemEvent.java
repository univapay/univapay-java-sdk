package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum PaymentSystemEvent {
  @SerializedName("charge_updated")
  CHARGE_UPDATED,
  @SerializedName("charge_finished")
  CHARGE_FINISHED,
  @SerializedName("subscription_payment")
  SUBSCRIPTION_PAYMENT,
  @SerializedName("subscription_completed")
  SUBSCRIPTION_COMPLETED,
  @SerializedName("subscription_failure")
  SUBSCRIPTION_FAILURE,
  @SerializedName("subscription_canceled")
  SUBSCRIPTION_CANCELED,
  @SerializedName("subscription_suspended")
  SUBSCRIPTION_SUSPENDED,
  @SerializedName("refund_finished")
  REFUND_FINISHED,
  @SerializedName("transfer_finalized")
  TRANSFER_FINALIZED,
  @SerializedName("cancel_finished")
  CANCEL_FINISHED
}
