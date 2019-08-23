package com.univapay.sdk.builders.exchangerate;

import com.univapay.sdk.builders.IdempotentRetrofitRequestBuilder;
import com.univapay.sdk.models.common.MoneyLike;
import retrofit2.Retrofit;

public abstract class AbstractExchangeRateBuilders {

  public abstract static class AbstractConvertMoneyBuilder<
          B extends AbstractConvertMoneyBuilder, R, M extends MoneyLike>
      extends IdempotentRetrofitRequestBuilder<M, R, B> {

    protected MoneyLike moneyToConvert;
    protected String targetCurrency;

    public AbstractConvertMoneyBuilder(
        Retrofit retrofit, MoneyLike moneyToConvert, String targetCurrency) {
      super(retrofit);
      this.moneyToConvert = moneyToConvert;
      this.targetCurrency = targetCurrency;
    }
  }
}
