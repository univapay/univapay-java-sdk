package com.univapay.sdk.resources;

import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.IssuerToken;
import retrofit2.Call;
import retrofit2.http.*;

public interface IssuerTokensResource {

  @GET("/stores/{storeId}/charges/{id}/issuerToken")
  Call<IssuerToken> getChargeIssuerToken(@Path("storeId") StoreId storeId, @Path("id") ChargeId id);
}
