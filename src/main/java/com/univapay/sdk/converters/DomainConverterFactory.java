package com.univapay.sdk.converters;

import com.univapay.sdk.models.common.Domain;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class DomainConverterFactory extends Converter.Factory {
  class DomainConverter implements Converter<Domain, String> {
    @Override
    public String convert(Domain domain) throws IOException {
      if (domain == null) {
        return null;
      } else {
        return domain.asString();
      }
    }
  }

  @Override
  public Converter<?, String> stringConverter(
      Type type, Annotation[] annotations, Retrofit retrofit) {
    if (type instanceof Class<?> && ((Domain.class.isAssignableFrom((Class<?>) type)))) {
      return new DomainConverter();
    }
    return super.stringConverter(type, annotations, retrofit);
  }
}
