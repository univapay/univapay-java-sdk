package com.univapay.sdk.builders.authentication;

import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.models.common.EmailAddress;
import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.response.authentication.LoginTokenInfo;
import retrofit2.Retrofit;

public abstract class AbstractAuthenticationBuilders {

  public abstract static class AbstractLoginRequestBuilder<
          B extends AbstractLoginRequestBuilder, R, M extends LoginTokenInfo>
      extends RetrofitRequestBuilder<M, R> {

    protected EmailAddress email;
    protected String password;

    protected String getEmail() {
      if (email == null) {
        return null;
      }
      return email.serialize();
    }

    protected String getPassword() {
      return password;
    }

    public AbstractLoginRequestBuilder(Retrofit retrofit, EmailAddress email, String password) {
      super(retrofit);
      this.email = email;
      this.password = password;
    }
  }

  public abstract static class AbstractLogoutRequestBuilder<
          B extends AbstractLogoutRequestBuilder, R>
      extends RetrofitRequestBuilder<Void, R> {

    public AbstractLogoutRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }
  }
}
