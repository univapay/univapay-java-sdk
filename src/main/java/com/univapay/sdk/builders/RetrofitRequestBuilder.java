package com.univapay.sdk.builders;

import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.utils.Sleeper;
import com.univapay.sdk.utils.UnivapayCallback;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import retrofit2.Call;
import retrofit2.Retrofit;

public abstract class RetrofitRequestBuilder<E extends UnivapayResponse, R>
    implements RequestBuilder<E, Request<E>>, Request<E> {

  private Class<R> resourceClass;
  protected Retrofit retrofit;

  public RetrofitRequestBuilder(Retrofit retrofit) {
    this.retrofit = retrofit;

    this.resourceClass =
        (Class<R>)
            ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
  }

  protected Call<E> createCall() {
    R resource = retrofit.create(resourceClass);
    return getRequest(resource);
  }

  protected abstract Call<E> getRequest(R resource);

  @Override
  public Request<E> build() {
    return new RetrofitRequestCaller<>(retrofit, createCall());
  }

  @Override
  public void dispatch(final UnivapayCallback<E> callback) {
    build().dispatch(callback);
  }

  @Override
  public E dispatch() throws IOException, UnivapayException {
    return build().dispatch();
  }

  @Override
  public E dispatch(int maxRetry, Sleeper sleeper)
      throws UnivapayException, InterruptedException, IOException {
    return build().dispatch(maxRetry, sleeper);
  }
}
