package com.univapay.sdk.converters;

import com.univapay.sdk.models.response.UnivapayBinaryData;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class BinaryDataConverterFactory extends Converter.Factory {

  @Nullable
  @Override
  public Converter<ResponseBody, ?> responseBodyConverter(
      Type type, Annotation[] annotations, Retrofit retrofit) {

    if (type.equals(UnivapayBinaryData.class)) {
      return (Converter<ResponseBody, UnivapayBinaryData>)
          responseBody -> new UnivapayBinaryData(responseBody.bytes());
    } else return null;
  }
}
