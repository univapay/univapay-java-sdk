package com.univapay.sdk.utils;

import com.univapay.sdk.models.common.auth.AuthHeader;
import com.univapay.sdk.models.common.auth.AuthStrategy;
import com.univapay.sdk.models.common.auth.LoginJWTStrategy;
import com.univapay.sdk.settings.AbstractSDKSettings;
import com.univapay.sdk.types.AuthType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UnivapayClient {
  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String X_AMZN_REMAPPED_AUTHORIZATION_HEADER = "x-amzn-remapped-authorization";
  public static final String ORIGIN_HEADER_NAME = "Origin";
  public static final List<String> REFRESH_AUTHORIZATION_HEADERS =
      new ArrayList<>(Arrays.asList(AUTHORIZATION_HEADER, X_AMZN_REMAPPED_AUTHORIZATION_HEADER));

  public static OkHttpClient.Builder getDefaultClientBuilder(
      final AuthStrategy authStrategy, final AbstractSDKSettings settings) {

    return new OkHttpClient.Builder()
        .connectTimeout(settings.getTimeout(), TimeUnit.SECONDS)
        .readTimeout(settings.getTimeout(), TimeUnit.SECONDS)
        .writeTimeout(settings.getTimeout(), TimeUnit.SECONDS)
        .addInterceptor(
            new Interceptor() {
              @Override
              public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                AuthHeader authHeader = authStrategy.getAuthHeader();
                if (!authHeader.getAuthType().equals(AuthType.NO_AUTH_HEADER)) {
                  requestBuilder.addHeader(AUTHORIZATION_HEADER, authHeader.getValue());
                }
                if (!settings.getOrigin().isEmpty()) {
                  requestBuilder.addHeader(ORIGIN_HEADER_NAME, settings.getOrigin());
                }

                Request request = requestBuilder.build();
                Response response = chain.proceed(request);

                /*
                 * If the client is currently using a Login JWT for authentication, replace the soon-to-expire
                 * token with the refresh token sent by the API in the response's Authorization header.
                 */

                if (LoginJWTStrategy.class.isInstance(authStrategy)) {
                  List<String> matchingRefreshHeaders = new ArrayList<>();
                  for (String refreshHeader : REFRESH_AUTHORIZATION_HEADERS) {
                    String header = response.header(refreshHeader);
                    if (header != null) {
                      matchingRefreshHeaders.add(header);
                    }
                  }

                  if (!matchingRefreshHeaders.isEmpty()) {
                    String refreshToken =
                        AuthHeader.parseValueFromJWTHeader(matchingRefreshHeaders.get(0));
                    ((LoginJWTStrategy) authStrategy).refresh(refreshToken);
                    if (settings.getRefreshLoginJWTCallback() != null) {
                      settings
                          .getRefreshLoginJWTCallback()
                          .onRefreshed(authStrategy.getAuthHeader());
                    }
                  }
                }

                return response;
              }
            });
  }
}
