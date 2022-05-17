package com.univapay.sdk.models.common;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum OsType {
  iOS("i_os"),
  Android("android");

  private final String typeRepresentation;

  OsType(String typeRepresentation) {
    this.typeRepresentation = typeRepresentation;
  }

  public String getTypeRepresentation() {
    return typeRepresentation;
  }

  static final Map<String, OsType> entryMapByTypeRepresentation =
      Arrays.stream(OsType.values())
          .collect(Collectors.toMap(OsType::getTypeRepresentation, Function.identity()));

  public static OsType getInstanceByLiteralValue(final String literalValue)
      throws IllegalArgumentException {
    OsType value = entryMapByTypeRepresentation.get(literalValue);

    if (value == null) {
      throw new IllegalArgumentException();
    } else {
      return value;
    }
  }

  public static OsType getInstanceByLiteralValueNullable(final String literalValue) {
    return entryMapByTypeRepresentation.get(literalValue);
  }
}
