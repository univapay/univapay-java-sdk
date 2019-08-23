package com.univapay.sdk.builders;

import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.models.response.UnivapayResponse;
import retrofit2.Retrofit;

public abstract class IdempotentRetrofitRequestBuilder<E extends UnivapayResponse, R, B>
    extends RetrofitRequestBuilder<E, R> implements IdempotentRequestBuilder<B> {

  protected IdempotencyKey idempotencyKey;

  public IdempotentRetrofitRequestBuilder(Retrofit retrofit) {
    super(retrofit);
  }

  @Override
  public B withIdempotencyKey(IdempotencyKey idempotencyKey) {
    this.idempotencyKey = idempotencyKey;
    return (B) this;
  }

  @Override
  public IdempotencyKey getIdempotencyKey() {
    return idempotencyKey;
  }
}
