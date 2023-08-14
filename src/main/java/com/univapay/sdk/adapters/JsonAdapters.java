package com.univapay.sdk.adapters;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.univapay.sdk.models.common.*;
import com.univapay.sdk.models.common.auth.LoginJWTStrategy;
import com.univapay.sdk.models.request.configuration.PreconfiguredTransferSchedule;
import com.univapay.sdk.models.request.subscription.RemoveInstallmentsPlan;
import com.univapay.sdk.models.response.PaymentsPlan;
import com.univapay.sdk.models.response.gateway.UnivapayGateway;
import com.univapay.sdk.models.response.subscription.SimulatedPayment;
import com.univapay.sdk.types.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class JsonAdapters {

  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

  public static class JsonDateAdapter
      implements JsonSerializer<OffsetDateTime>, JsonDeserializer<OffsetDateTime> {
    public JsonElement serialize(
        OffsetDateTime src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(DATE_TIME_FORMATTER.format(src));
    }

    public OffsetDateTime deserialize(
        JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      String s = json.getAsJsonPrimitive().getAsString();
      return OffsetDateTime.from(DATE_TIME_FORMATTER.parse(s));
    }
  }

  public static class JsonPeriodAdapter
      implements JsonSerializer<Period>, JsonDeserializer<Period> {
    public JsonElement serialize(Period src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.toString());
    }

    public Period deserialize(JsonElement json, Type typeOfP, JsonDeserializationContext context) {
      String s = json.getAsJsonPrimitive().getAsString();
      return Period.parse(s);
    }
  }

  public static class JsonDurationAdapter
      implements JsonSerializer<Duration>, JsonDeserializer<Duration> {
    @Override
    public JsonElement serialize(
        Duration duration, Type type, JsonSerializationContext jsonSerializationContext) {
      return new JsonPrimitive(duration.toString());
    }

    @Override
    public Duration deserialize(
        JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
        throws JsonParseException {
      String s = jsonElement.getAsJsonPrimitive().getAsString();
      return Duration.parse(s);
    }
  }

  public static class JsonLocalDateAdapter
      implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.toString());
    }

    public LocalDate deserialize(
        JsonElement json, Type typeOfP, JsonDeserializationContext context) {
      String s = json.getAsJsonPrimitive().getAsString();
      return LocalDate.parse(s);
    }
  }

  public static class JsonRemoveInstallmentsPlanAdapter
      extends TypeAdapter<RemoveInstallmentsPlan> {
    private final Gson gson;

    public JsonRemoveInstallmentsPlanAdapter(Gson nullableGson) {
      this.gson = nullableGson;
    }

    @Override
    public void write(JsonWriter out, RemoveInstallmentsPlan value) throws IOException {
      gson.toJson(JsonNull.INSTANCE, out);
    }

    @Override
    public RemoveInstallmentsPlan read(JsonReader in) throws IOException {
      return new RemoveInstallmentsPlan();
    }
  }

  public static class JsonJWTDeserializer implements JsonDeserializer<LoginJWTStrategy> {
    @Override
    public LoginJWTStrategy deserialize(
        JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      return new LoginJWTStrategy(json.getAsJsonPrimitive().getAsString());
    }
  }

  public static class JsonDomainAdapter
      implements JsonSerializer<Domain>, JsonDeserializer<Domain> {
    @Override
    public Domain deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      return new Domain(json.getAsJsonPrimitive().getAsString());
    }

    @Override
    public JsonElement serialize(Domain src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.asString());
    }
  }

  public static class JsonZoneIdAdapter
      implements JsonSerializer<ZoneId>, JsonDeserializer<ZoneId> {
    @Override
    public JsonElement serialize(ZoneId src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.getId());
    }

    @Override
    public ZoneId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      return ZoneId.of(json.getAsJsonPrimitive().getAsString());
    }
  }

  public static class JsonCountryAdapter
      implements JsonSerializer<Country>, JsonDeserializer<Country> {
    @Override
    public Country deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      return Country.getCountryByAlpha2(json.getAsJsonPrimitive().getAsString());
    }

    @Override
    public JsonElement serialize(Country src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.getAlpha2());
    }
  }

  public static class JsonCardBrandAdapter implements JsonDeserializer<CardBrand> {
    @Override
    public CardBrand deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      return CardBrand.forBrandName(json.getAsJsonPrimitive().getAsString());
    }
  }

  public static class JsonPaymentsPlanAdapter
      implements JsonSerializer<PaymentsPlan>, JsonDeserializer<PaymentsPlan> {

    @Override
    public PaymentsPlan deserialize(
        JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      final ArrayList<SimulatedPayment> payments = new ArrayList<>();
      Class clazz = SimulatedPayment.class;
      for (JsonElement elem : json.getAsJsonArray()) {
        payments.add((SimulatedPayment) context.deserialize(elem, clazz));
      }
      return new PaymentsPlan(payments);
    }

    @Override
    public JsonElement serialize(
        PaymentsPlan src, Type typeOfSrc, JsonSerializationContext context) {
      JsonArray jsonPayments = new JsonArray();
      for (SimulatedPayment payment : src) {
        jsonPayments.add(context.serialize(payment));
      }
      return jsonPayments;
    }
  }

  public static class JsonDayOfMonthAdapter
      implements JsonSerializer<DayOfMonth>, JsonDeserializer<DayOfMonth> {

    @Override
    public DayOfMonth deserialize(
        JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      Integer jsonDayOfMonth = json.getAsNumber().intValue();
      return new DayOfMonth(jsonDayOfMonth);
    }

    @Override
    public JsonElement serialize(DayOfMonth src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.getDay());
    }
  }

  public static class JsonPaidyTokenAdapter
      implements JsonSerializer<PaidyToken>, JsonDeserializer<PaidyToken> {

    @Override
    public PaidyToken deserialize(
        JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      String str = json.getAsString();
      return new PaidyToken(str);
    }

    @Override
    public JsonElement serialize(PaidyToken src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.getPaidyToken());
    }
  }

  public static class JsonUnivapayGatewayAdapter implements JsonDeserializer<UnivapayGateway> {
    @Override
    public UnivapayGateway deserialize(
        JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      return new UnivapayGateway(Gateway.valueOf(json.getAsString().toUpperCase()));
    }
  }

  public static class JsonEmailAddressAdapter
      implements JsonSerializer<UnivapayEmailAddress>, JsonDeserializer<UnivapayEmailAddress> {
    @Override
    public JsonElement serialize(
        UnivapayEmailAddress src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.serialize());
    }

    @Override
    public UnivapayEmailAddress deserialize(
        JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      String email = json.getAsString();
      if (email.isEmpty()) {
        return new EmptyEmailAddress();
      }
      return new EmailAddress(email);
    }
  }

  public static class JsonPreconfiguredTransferScheduleSerializer implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

      if (PreconfiguredTransferSchedule.class.isAssignableFrom(type.getRawType())) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        return new TypeAdapter<T>() {
          @Override
          public void write(JsonWriter out, T value) throws IOException {

            PreconfiguredTransferSchedule typedValue = (PreconfiguredTransferSchedule) value;

            out.beginObject().name(typedValue.getConstant());
            delegate.write(out, value);
            out.endObject();
          }

          @Override
          public T read(JsonReader in) throws IOException {
            return delegate.read(in);
          }
        };
      } else {
        return null;
      }
    }
  }
}
