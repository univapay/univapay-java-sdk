package com.univapay.sdk.models.response.subscription;

import com.google.gson.annotations.SerializedName;

public enum SubscriptionTerminationMode {
  @SerializedName("immediate")
  IMMEDIATE,
  @SerializedName("on_next_payment")
  ON_NEXT_PAYMENT
}
