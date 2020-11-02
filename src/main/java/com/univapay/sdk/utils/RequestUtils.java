package com.univapay.sdk.utils;

import static com.univapay.sdk.adapters.JsonAdapters.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.univapay.sdk.converters.BinaryDataConverterFactory;
import com.univapay.sdk.converters.DomainConverterFactory;
import com.univapay.sdk.converters.IdempotencyKeyConverterFactory;
import com.univapay.sdk.converters.VoidConverterFactory;
import com.univapay.sdk.models.common.Domain;
import com.univapay.sdk.models.common.PaidyToken;
import com.univapay.sdk.models.common.PaymentDataTypeAdapter;
import com.univapay.sdk.models.common.UnivapayEmailAddress;
import com.univapay.sdk.models.common.auth.AuthStrategy;
import com.univapay.sdk.models.common.auth.LoginJWTStrategy;
import com.univapay.sdk.models.request.subscription.RemoveInstallmentsPlan;
import com.univapay.sdk.models.response.PaymentsPlan;
import com.univapay.sdk.models.response.gateway.UnivapayGateway;
import com.univapay.sdk.models.response.transactiontoken.PaymentData;
import com.univapay.sdk.models.response.transactiontoken.TokenAliasKey;
import com.univapay.sdk.models.webhook.WebhookEvent;
import com.univapay.sdk.models.webhook.WebhookEventDeserializer;
import com.univapay.sdk.settings.AbstractSDKSettings;
import com.univapay.sdk.types.CardBrand;
import com.univapay.sdk.types.Country;
import com.univapay.sdk.types.DayOfMonth;
import com.univapay.sdk.types.MetadataMap;
import java.time.*;
import okhttp3.ConnectionPool;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestUtils {
  // Utility gson that doesn't ignore null values. Useful on some patch methods that use nulls to
  // unset properties.
  private static final Gson gsonForNulls = new GsonBuilder().serializeNulls().create();

  private static final Gson gson =
      new GsonBuilder()
          .enableComplexMapKeySerialization()
          .registerTypeAdapter(OffsetDateTime.class, new JsonDateAdapter())
          .registerTypeAdapter(Domain.class, new JsonDomainAdapter())
          .registerTypeAdapter(Period.class, new JsonPeriodAdapter())
          .registerTypeAdapter(Duration.class, new JsonDurationAdapter())
          .registerTypeAdapter(LocalDate.class, new JsonLocalDateAdapter())
          .registerTypeAdapter(LoginJWTStrategy.class, new JsonJWTDeserializer())
          .registerTypeAdapter(WebhookEvent.class, new WebhookEventDeserializer())
          .registerTypeAdapter(PaymentData.class, new PaymentDataTypeAdapter())
          .registerTypeAdapter(
              RemoveInstallmentsPlan.class, new JsonRemoveInstallmentsPlanAdapter(gsonForNulls))
          .registerTypeAdapter(ZoneId.class, new JsonZoneIdAdapter())
          .registerTypeAdapter(Country.class, new JsonCountryAdapter())
          .registerTypeAdapter(MetadataMap.class, new JsonMetadataMapAdapter())
          .registerTypeAdapter(CardBrand.class, new JsonCardBrandAdapter())
          .registerTypeAdapter(PaymentsPlan.class, new JsonPaymentsPlanAdapter())
          .registerTypeAdapter(DayOfMonth.class, new JsonDayOfMonthAdapter())
          .registerTypeAdapter(TokenAliasKey.class, new JsonTokenAliasKeyAdapter())
          .registerTypeAdapter(PaidyToken.class, new JsonPaidyTokenAdapter())
          .registerTypeAdapter(UnivapayGateway.class, new JsonUnivapayGatewayAdapter())
          .registerTypeAdapter(UnivapayEmailAddress.class, new JsonEmailAddressAdapter())
          .registerTypeAdapterFactory(new JsonPreconfiguredTransferScheduleSerializer())
          .create();

  public static Retrofit createClient(
      AuthStrategy authStrategy, AbstractSDKSettings settings, ConnectionPool connectionPool) {
    return getBuilderBeforeClient(settings)
        .client(settings.getClient(authStrategy, connectionPool))
        .build();
  }

  public static Retrofit createClient(AuthStrategy authStrategy, AbstractSDKSettings settings) {
    return getBuilderBeforeClient(settings).client(settings.getClient(authStrategy)).build();
  }

  public static Retrofit.Builder getBuilderBeforeClient(AbstractSDKSettings settings) {
    return new Retrofit.Builder()
        .baseUrl(settings.getEndpoint())
        .addConverterFactory(new BinaryDataConverterFactory())
        .addConverterFactory(new VoidConverterFactory())
        .addConverterFactory(new EnumConverter())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(new IdempotencyKeyConverterFactory())
        .addConverterFactory(new DomainConverterFactory());
  }

  public static Gson getGson() {
    return gson;
  }
}
