package com.univapay.sdk.resources;

import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.response.IssuerToken;
import retrofit2.Call;
import retrofit2.http.*;

public interface IssuerTokensResource {

  @GET("/stores/{storeId}/charges/{id}/issuer_token")
  Call<IssuerToken> getChargeIssuerToken(@Path("storeId") StoreId storeId, @Path("id") ChargeId id);

  @GET("/stores/{storeId}/tokens/{id}/three_ds/issuer_token")
  Call<IssuerToken> getTokenThreeDsIssuerToken(
      @Path("storeId") StoreId storeId, @Path("id") TransactionTokenId id);

  @GET("/stores/{storeId}/charges/{id}/three_ds/issuer_token")
  Call<IssuerToken> getChargeThreeDsIssuerToken(
      @Path("storeId") StoreId storeId, @Path("id") ChargeId id);
}
