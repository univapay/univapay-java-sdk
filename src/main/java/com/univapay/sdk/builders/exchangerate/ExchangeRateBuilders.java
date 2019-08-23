package com.univapay.sdk.builders.exchangerate;

import com.univapay.sdk.builders.exchangerate.AbstractExchangeRateBuilders.AbstractConvertMoneyBuilder;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.request.exchangerate.ExchangeRateConversionReq;
import com.univapay.sdk.resources.ExchangeRatesResource;
import retrofit2.Call;
import retrofit2.Retrofit;

public abstract class ExchangeRateBuilders {

  public static class ConvertMoneyBuilder
      extends AbstractConvertMoneyBuilder<ConvertMoneyBuilder, ExchangeRatesResource, MoneyLike> {

    public ConvertMoneyBuilder(Retrofit retrofit, MoneyLike moneyToConvert, String targetCurrency) {
      super(retrofit, moneyToConvert, targetCurrency);
    }

    @Override
    protected Call<MoneyLike> getRequest(ExchangeRatesResource resource) {
      return resource.convert(
          new ExchangeRateConversionReq(moneyToConvert, targetCurrency), idempotencyKey);
    }
  }
}
