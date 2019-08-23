package com.univapay.sdk.adapters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.univapay.sdk.models.common.UnivapayEmailAddress;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import com.univapay.sdk.models.common.Domain;
import com.univapay.sdk.models.common.EmailAddress;
import com.univapay.sdk.models.common.EmptyEmailAddress;
import com.univapay.sdk.models.common.PaidyToken;
import com.univapay.sdk.models.common.auth.LoginJWTStrategy;
import com.univapay.sdk.models.request.subscription.RemoveInstallmentsPlan;
import com.univapay.sdk.models.response.PaymentsPlan;
import com.univapay.sdk.models.response.gateway.UnivapayGateway;
import com.univapay.sdk.models.response.subscription.SimulatedPayment;
import com.univapay.sdk.models.response.transactiontoken.TokenAliasKey;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.types.DayOfMonth;
import com.univapay.sdk.types.Gateway;
import com.univapay.sdk.types.MetadataMap;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.threeten.bp.Duration;
import org.threeten.bp.ZoneId;

public class JsonAdapters {

  public static final DateTimeFormatter dateTimeParser = ISODateTimeFormat.dateTimeParser();
  public static final DateTimeFormatter dateTimePrinter = ISODateTimeFormat.dateTime();

  public static class JsonDateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(dateTimePrinter.print(src.getTime()));
    }

    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      String s = json.getAsJsonPrimitive().getAsString();
      return dateTimeParser.parseDateTime(s).toDate();
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

  public static class JsonMetadataMapAdapter implements JsonDeserializer<MetadataMap> {
    @Override
    public MetadataMap deserialize(
        JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      MetadataMap metadataMap = new MetadataMap();
      for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
        if (entry.getValue().isJsonPrimitive()
            && entry.getValue().getAsJsonPrimitive().isString()) {
          metadataMap.put(entry.getKey(), entry.getValue().getAsString());
        } else {
          metadataMap.put(entry.getKey(), new Gson().toJson(entry.getValue()));
        }
      }
      return metadataMap;
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

  public static class JsonTokenAliasKeyAdapter
      implements JsonSerializer<TokenAliasKey>, JsonDeserializer<TokenAliasKey> {

    @Override
    public TokenAliasKey deserialize(
        JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      String keyStr = json.getAsString();
      return new TokenAliasKey(keyStr);
    }

    @Override
    public JsonElement serialize(
        TokenAliasKey src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.getKey());
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
}
