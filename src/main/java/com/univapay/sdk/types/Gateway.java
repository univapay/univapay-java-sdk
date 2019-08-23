package com.univapay.sdk.types;

import com.google.gson.annotations.SerializedName;

public enum Gateway {
  @SerializedName("payvision")
  PAYVISION,
  @SerializedName("wirecard")
  WIRECARD,
  @SerializedName("world_pay")
  WORLD_PAY,
  @SerializedName("allied_wallet")
  ALLIED_WALLET,
  @SerializedName("allied_wallet_next_gen")
  ALLIED_WALLET_NEXT_GEN,
  @SerializedName("nccc")
  NCCC,
  @SerializedName("meiko_pay")
  MEIKO_PAY,
  @SerializedName("alipay")
  ALIPAY,
  @SerializedName("qq")
  QQ,
  @SerializedName("alipay_merchant_qr")
  ALIPAY_MERCHANT_QR,
  @SerializedName("densan")
  DENSAN,
  @SerializedName("ips")
  IPS,
  @SerializedName("paidy")
  PAIDY,
  @SerializedName("origami")
  ORIGAMI,
  @SerializedName("alipay_connect")
  ALIPAY_CONNECT,
  @SerializedName("we_chat")
  WE_CHAT,
  @SerializedName("jkopay")
  JKOPAY,
  @SerializedName("test")
  TEST
}
