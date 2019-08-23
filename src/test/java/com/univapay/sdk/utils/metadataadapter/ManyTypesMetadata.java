package com.univapay.sdk.utils.metadataadapter;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ManyTypesMetadata {

  private String stringValue;
  private BigInteger bigIntegerValue;
  private BigDecimal bigDecimalValue;
  private Boolean booleanValue;
  private Float floatValue;

  public ManyTypesMetadata(
      String stringValue,
      BigInteger bigIntegerValue,
      BigDecimal bigDecimalValue,
      Boolean booleanValue,
      Float floatValue) {
    this.stringValue = stringValue;
    this.bigIntegerValue = bigIntegerValue;
    this.bigDecimalValue = bigDecimalValue;
    this.booleanValue = booleanValue;
    this.floatValue = floatValue;
  }

  public String getStringValue() {
    return stringValue;
  }

  public BigInteger getBigIntegerValue() {
    return bigIntegerValue;
  }

  public BigDecimal getBigDecimalValue() {
    return bigDecimalValue;
  }

  public Boolean getBooleanValue() {
    return booleanValue;
  }

  public Float getFloatValue() {
    return floatValue;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj.getClass().isAssignableFrom(ManyTypesMetadata.class)) {
      ManyTypesMetadata other = (ManyTypesMetadata) obj;
      return (other.getBooleanValue() == booleanValue
          && other.getBigDecimalValue().equals(bigDecimalValue)
          && other.getBigIntegerValue().equals(bigIntegerValue)
          && other.getFloatValue().equals(floatValue)
          && other.getStringValue().equals(stringValue));
    } else return false;
  }
}
