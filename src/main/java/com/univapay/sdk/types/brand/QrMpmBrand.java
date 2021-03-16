package com.univapay.sdk.types.brand;

import com.google.gson.annotations.SerializedName;

public enum QrMpmBrand {
  @SerializedName("rakuten_pay_merchant")
  RakutenPay,
  @SerializedName("alipay_merchant_qr")
  Alipay,
  @SerializedName("pay_pay_merchant")
  PayPay,

  @SerializedName("alipay_connect_mpm")
  AlipayConnect,

  @SerializedName("alipay_china")
  AlipayChina,
  @SerializedName("alipay_hk")
  AlipayHK,
  @SerializedName("alipay_singapore")
  AlipaySG,
  @SerializedName("kakaopay")
  Kakaopay
}
