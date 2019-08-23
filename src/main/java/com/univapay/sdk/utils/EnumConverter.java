package com.univapay.sdk.utils;

import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit2.Converter;
import retrofit2.Converter.Factory;
import retrofit2.Retrofit;

public class EnumConverter extends Factory {

  @Override
  public Converter<?, String> stringConverter(
      Type type, Annotation[] annotations, Retrofit retrofit) {
    class UnivapayEnumConverter implements Converter<Enum, String> {
      @Override
      public String convert(Enum e) throws IOException {
        String value = null;
        try {
          value = e.getClass().getField(e.name()).getAnnotation(SerializedName.class).value();
        } catch (NoSuchFieldException exception) {
          exception.printStackTrace();
        }
        return value;
      }
    }

    Converter<Enum, String> converter = null;
    if (type instanceof Class<?> && ((Class<?>) type).isEnum()) {
      converter = new UnivapayEnumConverter();
    }
    return converter;
  }
}
