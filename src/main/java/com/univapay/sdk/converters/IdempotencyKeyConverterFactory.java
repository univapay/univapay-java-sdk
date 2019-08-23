package com.univapay.sdk.converters;

import com.univapay.sdk.models.common.IdempotencyKey;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class IdempotencyKeyConverterFactory extends Converter.Factory {

  class IdempotencyKeyConverter implements Converter<IdempotencyKey, String> {
    @Override
    public String convert(IdempotencyKey idempotencyKey) throws IOException {
      return idempotencyKey.getKey();
    }
  }

  @Override
  public Converter<?, String> stringConverter(
      Type type, Annotation[] annotations, Retrofit retrofit) {
    if (type instanceof Class<?> && ((IdempotencyKey.class.isAssignableFrom((Class<?>) type)))) {
      return new IdempotencyKeyConverter();
    }
    return super.stringConverter(type, annotations, retrofit);
  }
}
