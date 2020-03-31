package com.univapay.sdk.builders.authentication;

import com.univapay.sdk.builders.authentication.AbstractAuthenticationBuilders.AbstractLoginRequestBuilder;
import com.univapay.sdk.builders.authentication.AbstractAuthenticationBuilders.AbstractLogoutRequestBuilder;
import com.univapay.sdk.models.common.EmailAddress;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.request.authentication.AuthenticationRequestData;
import com.univapay.sdk.models.response.authentication.LoginTokenInfo;
import com.univapay.sdk.resources.AuthenticationResource;
import retrofit2.Call;
import retrofit2.Retrofit;

public abstract class AuthenticationBuilders {

  public static class LoginRequestBuilder
      extends AbstractLoginRequestBuilder<
          LoginRequestBuilder, AuthenticationResource, LoginTokenInfo> {
    public LoginRequestBuilder(Retrofit retrofit, EmailAddress email, String password) {
      super(retrofit, email, password);
    }

    @Override
    protected Call<LoginTokenInfo> getRequest(AuthenticationResource resource) {
      return resource.getLoginToken(new AuthenticationRequestData(email, password));
    }
  }

  public static class LogoutRequestBuilder
      extends AbstractLogoutRequestBuilder<LogoutRequestBuilder, AuthenticationResource> {
    public LogoutRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    @Override
    protected Call<Void> getRequest(AuthenticationResource resource) {
      return resource.deleteAuthToken();
    }
  }
}
