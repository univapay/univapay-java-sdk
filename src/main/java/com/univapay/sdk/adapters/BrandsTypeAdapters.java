package com.univapay.sdk.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.univapay.sdk.types.Konbini;
import com.univapay.sdk.types.brand.OnlineBrand;
import com.univapay.sdk.types.brand.PaidyBrand;
import com.univapay.sdk.types.brand.QrCpmBrand;
import java.io.IOException;

public class BrandsTypeAdapters {

  abstract static class SimpleEnumTypeAdapter<T> extends TypeAdapter<T> {

    @Override
    public T read(JsonReader reader) throws IOException {
      if (reader.peek() == JsonToken.NULL) {
        reader.nextNull();
        return null;
      }
      String value = reader.nextString();

      return getByValue(value);
    }

    @Override
    public void write(JsonWriter writer, T value) throws IOException {
      if (value == null) {
        writer.nullValue();
        return;
      }

      writer.value(getValueOfObject(value));
    }

    protected abstract String getValueOfObject(T input);

    protected abstract T getByValue(String input);
  }

  public static class KonbiniTypeAdapter extends SimpleEnumTypeAdapter<Konbini> {

    @Override
    protected String getValueOfObject(Konbini input) {
      return input.getTypeRepresentation();
    }

    @Override
    protected Konbini getByValue(String value) {
      return Konbini.getInstanceByLiteralValueNullable(value);
    }
  }

  public static class QrCpmBrandTypeAdapter extends SimpleEnumTypeAdapter<QrCpmBrand> {

    @Override
    protected String getValueOfObject(QrCpmBrand input) {
      return input.getTypeRepresentation();
    }

    @Override
    protected QrCpmBrand getByValue(String value) {
      return QrCpmBrand.getInstanceByLiteralValueNullable(value);
    }
  }

  public static class OnlineBrandTypeAdapter extends SimpleEnumTypeAdapter<OnlineBrand> {

    @Override
    protected String getValueOfObject(OnlineBrand input) {
      return input.getTypeRepresentation();
    }

    @Override
    protected OnlineBrand getByValue(String value) {
      return OnlineBrand.getInstanceByLiteralValueNullable(value);
    }
  }

  public static class PaidyBrandTypeAdapter extends SimpleEnumTypeAdapter<PaidyBrand> {

    @Override
    protected String getValueOfObject(PaidyBrand input) {
      return input.getTypeRepresentation();
    }

    @Override
    protected PaidyBrand getByValue(String value) {
      return PaidyBrand.getInstanceByLiteralValueNullable(value);
    }
  }
}
