package com.univapay.sdk.builders.utility;

import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.models.common.Void;
import retrofit2.Retrofit;

public class AbstractUtilityBuilders {
  public abstract static class AbstractHeartbeatRequestBuilder<
          B extends AbstractHeartbeatRequestBuilder, R>
      extends RetrofitRequestBuilder<Void, R> {

    public AbstractHeartbeatRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }
  }
}
