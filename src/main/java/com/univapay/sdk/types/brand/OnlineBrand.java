package com.univapay.sdk.types.brand;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum OnlineBrand {
  TEST("test_brand"),

  ALIPAY("alipay_online"),

  PAYPAY("pay_pay_online"),

  WE_CHAT("we_chat_online"),

  ALIPAY_CHINA("alipay_china"),

  ALIPAY_HK("alipay_hk"),

  B_KASH("b_kash"),

  DANA("dana"),

  EASYPAISA("easypaisa"),

  GCASH("gcash"),

  KAKAOPAY("kakaopay"),

  TOUCH_N_GO("tng"),

  TRUEMONEY("truemoney");

  private final String typeRepresentation;

  OnlineBrand(String typeRepresentation) {
    this.typeRepresentation = typeRepresentation;
  }

  public String getTypeRepresentation() {
    return typeRepresentation;
  }

  static final Map<String, OnlineBrand> entryMapByTypeRepresentation =
      Arrays.stream(OnlineBrand.values())
          .collect(Collectors.toMap(OnlineBrand::getTypeRepresentation, Function.identity()));

  public static OnlineBrand getInstanceByLiteralValue(final String literalValue)
      throws IllegalArgumentException {
    OnlineBrand value = entryMapByTypeRepresentation.get(literalValue);

    if (value == null) {
      throw new IllegalArgumentException();
    } else {
      return value;
    }
  }

  public static OnlineBrand getInstanceByLiteralValueNullable(final String literalValue) {
    return entryMapByTypeRepresentation.get(literalValue);
  }
}
