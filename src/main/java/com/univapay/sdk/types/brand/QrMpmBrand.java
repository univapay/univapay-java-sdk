package com.univapay.sdk.types.brand;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum QrMpmBrand {
  RakutenPay("rakuten_pay_merchant"),

  Alipay("alipay_merchant_qr"),

  PayPay("pay_pay_merchant"),

  AlipayConnect("alipay_connect_mpm"),
  AlipayChina("alipay_china"),
  AlipayHK("alipay_hk"),
  AlipaySG("alipay_singapore"),
  Kakaopay("kakaopay"),
  TouchNGo("tng"),
  EzLink("ezlink"),
  GCash("gcash"),
  Dana("dana"),
  Tinaba("tinaba"),
  NaverPay("naverpay"),
  TossPay("tosspay"),
  OCBCBank("ocbc"),
  ChangiPay("changipay"),
  HiPay("hipay"),
  PublicBank("pbengagemy"),
  MPay("mpay"),
  TrueMoney("truemoney"),
  WeChat("we_chat_mpm"),
  DBarai("d_barai_mpm");

  private final String typeRepresentation;

  QrMpmBrand(String typeRepresentation) {
    this.typeRepresentation = typeRepresentation;
  }

  public String getTypeRepresentation() {
    return typeRepresentation;
  }

  static final Map<String, QrMpmBrand> entryMapByTypeRepresentation =
      Arrays.stream(QrMpmBrand.values())
          .collect(Collectors.toMap(QrMpmBrand::getTypeRepresentation, Function.identity()));

  public static QrMpmBrand getInstanceByLiteralValue(final String literalValue)
      throws IllegalArgumentException {
    QrMpmBrand value = entryMapByTypeRepresentation.get(literalValue);

    if (value == null) {
      throw new IllegalArgumentException();
    } else {
      return value;
    }
  }

  public static QrMpmBrand getInstanceByLiteralValueNullable(final String literalValue) {
    return entryMapByTypeRepresentation.get(literalValue);
  }
}
