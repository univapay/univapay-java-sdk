package com.univapay.sdk.settings;

import com.univapay.sdk.models.common.auth.AuthStrategy;
import com.univapay.sdk.utils.UnivapayClient;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

public class UnivapaySettings extends AbstractSDKSettings<UnivapaySettings> {

  @Override
  public UnivapaySettings copy() {
    return new UnivapaySettings()
        .withEndpoint(this.endpoint)
        .withTimeoutSeconds(this.timeout)
        .attachOrigin(this.origin)
        .withRefreshLoginJWTCallback(this.refreshLoginJWTCallback);
  }

  @Override
  public OkHttpClient getClient(AuthStrategy authStrategy) {
    return UnivapayClient.getDefaultClientBuilder(authStrategy, this).build();
  }

  @Override
  public OkHttpClient getClient(AuthStrategy authStrategy, ConnectionPool connectionPool) {
    return UnivapayClient.getDefaultClientBuilder(authStrategy, this)
        .connectionPool(connectionPool)
        .build();
  }
}
