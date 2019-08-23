package com.univapay.sdk.resources;

import com.univapay.sdk.constants.UnivapayConstants;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.request.exchangerate.ExchangeRateConversionReq;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ExchangeRatesResource {

  @POST("/exchange_rates/calculate")
  Call<MoneyLike> convert(
      @Body ExchangeRateConversionReq dataToPost,
      @Header(UnivapayConstants.idempotencyKeyHeaderName) IdempotencyKey idempotencyKey);
}
