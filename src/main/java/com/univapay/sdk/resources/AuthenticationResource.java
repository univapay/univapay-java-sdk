package com.univapay.sdk.resources;

import com.univapay.sdk.models.common.Void;
import com.univapay.sdk.models.request.authentication.AuthenticationRequestData;
import com.univapay.sdk.models.response.authentication.LoginTokenInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/** Resource for requesting the merchant's credentials. */
public interface AuthenticationResource {

  @POST("/authenticate")
  Call<LoginTokenInfo> getLoginToken(@Body AuthenticationRequestData authorizationReq);

  @POST("/logout")
  Call<Void> deleteAuthToken();
}
