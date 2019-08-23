package com.univapay.sdk.builders.utility;

import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.resources.UtilityResource;
import retrofit2.Call;
import retrofit2.Retrofit;

public class UtilityBuilders {
  public static class HeartBeatRequestBuilder
      extends AbstractUtilityBuilders.AbstractHeartbeatRequestBuilder<
          HeartBeatRequestBuilder, UtilityResource> {

    public HeartBeatRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    @Override
    protected Call<Void> getRequest(UtilityResource resource) {
      return resource.beat();
    }
  }
}
