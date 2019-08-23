package com.univapay.sdk.utils;

import com.google.gson.annotations.SerializedName;

public class EnumConverterUtils {
  public static <E extends Enum<E>> String getSerializedValue(E e) {
    String value = null;
    try {
      value = e.getClass().getField(e.name()).getAnnotation(SerializedName.class).value();
    } catch (NoSuchFieldException exception) {
      exception.printStackTrace();
    }

    return value;
  }
}
