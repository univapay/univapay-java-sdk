package com.univapay.sdk.types.brand;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum PaidyBrand {
  Paidy("paidy");

  private final String typeRepresentation;

  PaidyBrand(String typeRepresentation) {
    this.typeRepresentation = typeRepresentation;
  }

  public String getTypeRepresentation() {
    return typeRepresentation;
  }

  static final Map<String, PaidyBrand> entryMapByTypeRepresentation =
      Arrays.stream(PaidyBrand.values())
          .collect(Collectors.toMap(PaidyBrand::getTypeRepresentation, Function.identity()));

  public static PaidyBrand getInstanceByLiteralValue(final String literalValue)
      throws IllegalArgumentException {
    PaidyBrand value = entryMapByTypeRepresentation.get(literalValue);

    if (value == null) {
      throw new IllegalArgumentException();
    } else {
      return value;
    }
  }

  public static PaidyBrand getInstanceByLiteralValueNullable(final String literalValue) {
    return entryMapByTypeRepresentation.get(literalValue);
  }
}
