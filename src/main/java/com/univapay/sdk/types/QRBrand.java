package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum QRBrand {
  @SerializedName("alipay_china")
  ALIPAY_CH,
  @SerializedName("alipay_hk")
  ALIPAY_HK,
  @SerializedName("alipay_singapore")
  ALIPAY_SG,
  @SerializedName("d_barai")
  DBARAI,
  @SerializedName("kakaopay")
  KAKAOPAY,
  @SerializedName("origami")
  ORIGAMI,
  @SerializedName("qq")
  QQ,
  @SerializedName("we_chat")
  WECHAT,
  @SerializedName("rakuten_pay")
  RAKUTEN_PAY,
  @SerializedName("jkopay")
  JKOPAY,
  @SerializedName("bartong")
  BARTONG,
  @SerializedName("dash")
  DASH,
  @SerializedName("global_pay")
  GLOBAL_PAY,
  @SerializedName("tng")
  TNG,
  @SerializedName("ezlink")
  EZLINK,
  @SerializedName("truemoney")
  TRUEMONEY,
  @SerializedName("dana")
  DANA,
  @SerializedName("gcash")
  G_CASH

}
