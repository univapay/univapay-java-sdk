package com.univapay.sdk;

import com.univapay.sdk.models.common.auth.AuthStrategy;
import com.univapay.sdk.settings.AbstractSDKSettings;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.RetrofitBuilder;
import okhttp3.ConnectionPool;
import retrofit2.Retrofit;

public abstract class AbstractSDK {
  protected Retrofit retrofit;
  protected AuthStrategy authStrategy;
  protected AbstractSDKSettings settings;

  protected RetrofitBuilder getRetrofitBuilder() {
    return new RetrofitBuilder();
  }

  public AbstractSDK(AuthStrategy authStrategy, AbstractSDKSettings settings) {
    this.authStrategy = authStrategy;
    this.settings = settings;
    this.retrofit = getRetrofitBuilder().createClient(authStrategy, settings);
  }

  public AbstractSDK(
      AuthStrategy authStrategy, AbstractSDKSettings settings, ConnectionPool connectionPool) {
    this.authStrategy = authStrategy;
    this.settings = settings;
    this.retrofit = getRetrofitBuilder().createClient(authStrategy, settings, connectionPool);
  }

  public String getTokenValue() {
    return authStrategy.getAuthHeader().getTokenValue();
  }

  public AuthType getAuthType() {
    return authStrategy.getAuthHeader().getAuthType();
  }
}
