package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum PaymentTypeName {
  @SerializedName("bank_transfer")
  BANK_TRANSFER,
  @SerializedName("card")
  CARD,
  @SerializedName("qr_scan")
  QR_SCAN,
  @SerializedName("qr_merchant")
  QR_MERCHANT,
  @SerializedName("apple_pay")
  APPLE_PAY,
  @SerializedName("konbini")
  KONBINI,
  @SerializedName("paidy")
  PAIDY,
  @SerializedName("online")
  ONLINE
}
