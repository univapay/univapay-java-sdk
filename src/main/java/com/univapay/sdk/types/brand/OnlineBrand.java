package com.univapay.sdk.types.brand;

import com.google.gson.annotations.SerializedName;

public enum OnlineBrand {
  @SerializedName("test")
  TEST,
  @SerializedName("alipay_online")
  ALIPAY,
  @SerializedName("pay_pay_online")
  PAYPAY
}
