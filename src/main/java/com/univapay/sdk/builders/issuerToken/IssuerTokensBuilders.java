package com.univapay.sdk.builders.issuerToken;

import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.IssuerToken;
import com.univapay.sdk.resources.IssuerTokensResource;
import retrofit2.Call;
import retrofit2.Retrofit;

public class IssuerTokensBuilders {

  /** Get the IssuerToken of a Charge using the /stores/{storeId}/charges/{id}/issuerToken route */
  public static class GetIssuerToken
      extends AbstractIssuerTokensBuilders.AbstractGetIssuerTokenRequestBuilder<
          GetIssuerToken, IssuerTokensResource, IssuerToken> {

    public GetIssuerToken(Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
      super(retrofit, storeId, chargeId);
    }

    @Override
    protected Call<IssuerToken> getRequest(IssuerTokensResource resource) {
      return resource.getChargeIssuerToken(storeId, chargeId);
    }
  }
}
