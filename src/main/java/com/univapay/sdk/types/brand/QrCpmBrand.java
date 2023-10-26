package com.univapay.sdk.types.brand;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum QrCpmBrand {
  QQ("qq"),

  PayPay("pay_pay"),
  Bartong("bartong"),
  WeChat("we_chat"),
  Origami("origami"),
  RakutenPay("rakuten_pay"),
  Jkopay("jkopay"),
  LinePay("line_pay"),
  AuPay("au_pay"),

  AlipayCN("alipay_china"),
  AlipayHK("alipay_hk"),
  AlipaySG("alipay_singapore"),
  KakaoPay("kakaopay"),
  TouchNGo("tng"),
  EzLink("ezlink"),
  GCash("gcash"),
  Dana("dana"),
  TrueMoney("truemoney"),
  HelloMoney("hellomoney"),
  Tinaba("tinaba"),
  NaverPay("naver_pay"),
  TossPay("toss_pay"),
  OCBCBank("ocbc_bank"),
  ChangiPay("changi_pay"),
  HiPay("hi_pay"),
  PublicBank("public_bank"),
  MPay("m_pay"),

  DBarai("d_barai"),

  Merpay("merpay"),

  Dash("dash"),
  GlobalPay("global_pay"),

  GinkoPay("ginko_pay"),
  YuchoPay("yucho_pay"),
  HamaPay("hama_pay"),
  OKIPay("oki_pay"),
  YOKAPay_Fukuoka("yoka_pay_fukuoka"),
  YOKAPay_Kumamoto("yoka_pay_kumamoto"),
  YOKAPay_Shinwa("yoka_pay_shinwa"),
  HokuHokuPay_Hokkaido("hokuhoku_pay_hokkaido"),
  HokuHokuPay_Hokuriku("hokuhoku_pay_hokuriku"),
  CoiPay_Hiroshima("coi_pay_hiroshima"),
  SMBC("smbc");

  private final String typeRepresentation;

  QrCpmBrand(String typeRepresentation) {
    this.typeRepresentation = typeRepresentation;
  }

  public String getTypeRepresentation() {
    return typeRepresentation;
  }

  static final Map<String, QrCpmBrand> entryMapByTypeRepresentation =
      Arrays.stream(QrCpmBrand.values())
          .collect(Collectors.toMap(QrCpmBrand::getTypeRepresentation, Function.identity()));

  public static QrCpmBrand getInstanceByLiteralValue(final String literalValue)
      throws IllegalArgumentException {
    QrCpmBrand value = entryMapByTypeRepresentation.get(literalValue);

    if (value == null) {
      throw new IllegalArgumentException();
    } else {
      return value;
    }
  }

  public static QrCpmBrand getInstanceByLiteralValueNullable(final String literalValue) {
    return entryMapByTypeRepresentation.get(literalValue);
  }
}
