package com.univapay.sdk.utils;

import static com.univapay.sdk.adapters.JsonAdapters.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.univapay.sdk.adapters.BrandsTypeAdapters;
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
import com.univapay.sdk.models.request.subscription.RemovePaymentPlan;
import com.univapay.sdk.models.response.PaymentsPlan;
import com.univapay.sdk.models.response.gateway.UnivapayGateway;
import com.univapay.sdk.models.response.transactiontoken.PaymentData;
import com.univapay.sdk.models.webhook.WebhookEvent;
import com.univapay.sdk.models.webhook.WebhookEventDeserializer;
import com.univapay.sdk.settings.AbstractSDKSettings;
import com.univapay.sdk.types.*;
import com.univapay.sdk.types.brand.OnlineBrand;
import com.univapay.sdk.types.brand.PaidyBrand;
import com.univapay.sdk.types.brand.QrCpmBrand;
import com.univapay.sdk.types.brand.QrMpmBrand;
import java.time.*;
import java.util.concurrent.atomic.AtomicReference;
import okhttp3.ConnectionPool;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

  public Retrofit createClient(
      AuthStrategy authStrategy, AbstractSDKSettings settings, ConnectionPool connectionPool) {
    return getRetrofitBuilder(settings)
        .client(settings.getClient(authStrategy, connectionPool))
        .build();
  }

  public Retrofit createClient(AuthStrategy authStrategy, AbstractSDKSettings settings) {
    return getRetrofitBuilder(settings).client(settings.getClient(authStrategy)).build();
  }

  protected Retrofit.Builder getRetrofitBuilder(AbstractSDKSettings settings) {
    GsonConverterFactory converterFactory = GsonConverterFactory.create(getGson());
    Retrofit.Builder factory =
        new Retrofit.Builder()
            .baseUrl(settings.getEndpoint())
            .addConverterFactory(new BinaryDataConverterFactory())
            .addConverterFactory(new VoidConverterFactory())
            .addConverterFactory(new EnumConverter())
            .addConverterFactory(converterFactory)
            .addConverterFactory(new IdempotencyKeyConverterFactory())
            .addConverterFactory(new DomainConverterFactory());

    return postCreateRetrofitBuilder(factory);
  }

  protected Retrofit.Builder postCreateRetrofitBuilder(Retrofit.Builder factory) {
    return factory;
  }

  protected GsonBuilder getGsonBuilder() {
    GsonBuilder builder =
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
                RemovePaymentPlan.class, new JsonRemoveInstallmentsPlanAdapter(gsonForNulls))
            .registerTypeAdapter(ZoneId.class, new JsonZoneIdAdapter())
            .registerTypeAdapter(Country.class, new JsonCountryAdapter())
            .registerTypeAdapter(CardBrand.class, new JsonCardBrandAdapter())
            .registerTypeAdapter(PaymentsPlan.class, new JsonPaymentsPlanAdapter())
            .registerTypeAdapter(DayOfMonth.class, new JsonDayOfMonthAdapter())
            .registerTypeAdapter(PaidyToken.class, new JsonPaidyTokenAdapter())
            .registerTypeAdapter(UnivapayGateway.class, new JsonUnivapayGatewayAdapter())
            .registerTypeAdapter(UnivapayEmailAddress.class, new JsonEmailAddressAdapter())
            .registerTypeAdapter(Konbini.class, new BrandsTypeAdapters.KonbiniTypeAdapter())
            .registerTypeAdapter(QrCpmBrand.class, new BrandsTypeAdapters.QrCpmBrandTypeAdapter())
            .registerTypeAdapter(OnlineBrand.class, new BrandsTypeAdapters.OnlineBrandTypeAdapter())
            .registerTypeAdapter(PaidyBrand.class, new BrandsTypeAdapters.PaidyBrandTypeAdapter())
            .registerTypeAdapter(QrMpmBrand.class, new BrandsTypeAdapters.QrMpmBrandTypeAdapter())
            .registerTypeAdapterFactory(new JsonPreconfiguredTransferScheduleSerializer());
    return postCreateGsonBuilder(builder);
  }

  protected GsonBuilder postCreateGsonBuilder(GsonBuilder builder) {
    return builder;
  }

  protected Gson createGson() {
    return getGsonBuilder().create();
  }

  // Utility gson that doesn't ignore null values. Useful on some patch methods that use nulls to
  // unset properties.
  protected static final Gson gsonForNulls = new GsonBuilder().serializeNulls().create();
  protected static final AtomicReference<Gson> cached = new AtomicReference<>();

  public Gson getGson() {
    Gson value = cached.get();
    if (value == null) {
      synchronized (cached) {
        value = cached.get();
        if (value == null) {
          value = createGson();
          cached.set(value);
        }
      }
    }
    return value;
  }
}
