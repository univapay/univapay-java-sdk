package com.univapay.sdk.types;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Konbini {
  SEVEN_ELEVEN("seven_eleven"),
  FAMILY_MART("family_mart"),
  LAWSON("lawson"),
  MINI_STOP("mini_stop"),
  SEICO_MART("seico_mart"),
  PAY_EASY("pay_easy"),
  CIRCLE_K("circle_k"),
  SUNKUS("sunkus"),
  DAILY_YAMAZAKI("daily_yamazaki"),
  YAMAZAKI_DAILY_STORE("yamazaki_daily_store");

  private final String typeRepresentation;

  Konbini(String typeRepresentation) {
    this.typeRepresentation = typeRepresentation;
  }

  public String getTypeRepresentation() {
    return typeRepresentation;
  }

  static final Map<String, Konbini> entryMapByTypeRepresentation =
      Arrays.stream(Konbini.values())
          .collect(Collectors.toMap(Konbini::getTypeRepresentation, Function.identity()));

  // Not sure if needed
  public static Konbini getInstanceByLiteralValue(final String literalValue)
      throws IllegalArgumentException {
    Konbini value = entryMapByTypeRepresentation.get(literalValue);

    if (value == null) {
      throw new IllegalArgumentException();
    } else {
      return value;
    }
  }

  public static Konbini getInstanceByLiteralValueNullable(final String literalValue) {
    return entryMapByTypeRepresentation.get(literalValue);
  }
}
