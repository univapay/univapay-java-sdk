package com.univapay.sdk.utils;

import com.univapay.sdk.models.common.auth.AuthStrategy;
import com.univapay.sdk.settings.AbstractSDKSettings;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class UnivapayDebugSettings extends AbstractSDKSettings<UnivapayDebugSettings> {

  private boolean requestsLogging;

  public boolean getRequestsLogging() {
    return this.requestsLogging;
  }

  public UnivapayDebugSettings withRequestsLogging(boolean logRequests) {
    this.requestsLogging = logRequests;
    return this;
  }

  @Override
  public UnivapayDebugSettings copy() {
    return new UnivapayDebugSettings()
        .withEndpoint(this.endpoint)
        .withTimeoutSeconds(this.timeout)
        .attachOrigin(this.origin)
        .withRefreshLoginJWTCallback(this.refreshLoginJWTCallback)
        .withRequestsLogging(this.requestsLogging);
  }

  @Override
  public OkHttpClient getClient(AuthStrategy authStrategy) {
    OkHttpClient.Builder builder = UnivapayClient.getDefaultClientBuilder(authStrategy, this);
    if (requestsLogging) {
      HttpLoggingInterceptor loggingInterceptor =
          new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
      builder.addInterceptor(loggingInterceptor);
    }

    return builder.build();
  }

  @Override
  public OkHttpClient getClient(AuthStrategy authStrategy, ConnectionPool connectionPool) {
    OkHttpClient.Builder builder = UnivapayClient.getDefaultClientBuilder(authStrategy, this);
    if (requestsLogging) {
      HttpLoggingInterceptor loggingInterceptor =
          new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
      builder.addInterceptor(loggingInterceptor);
    }
    builder.connectionPool(connectionPool);

    return builder.build();
  }
}
