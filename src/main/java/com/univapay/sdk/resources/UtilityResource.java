package com.univapay.sdk.resources;

import com.univapay.sdk.models.common.Void;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UtilityResource {
  @GET("/heartbeat")
  Call<Void> beat();
}
