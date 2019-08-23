package com.univapay.sdk.converters;

import com.univapay.sdk.models.common.Void;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class VoidConverterFactory extends Converter.Factory {

  @Override
  public Converter<ResponseBody, ?> responseBodyConverter(
      Type type, Annotation[] annotations, Retrofit retrofit) {
    if (!type.equals(Void.class)) {
      return null;
    }

    return new Converter<ResponseBody, Object>() {
      @Override
      public Object convert(ResponseBody body) throws IOException {
        return null;
      }
    };
  }
}
