package com.univapay.sdk.builders.issuerToken;

import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.response.IssuerToken;
import com.univapay.sdk.resources.IssuerTokensResource;
import retrofit2.Call;
import retrofit2.Retrofit;

public class IssuerTokensBuilders {

  /** Get the IssuerToken of a Charge using the /stores/{storeId}/charges/{id}/issuer_token route */
  public static class GetChargeIssuerToken
      extends AbstractIssuerTokensBuilders.AbstractGetChargeIssuerTokenRequestBuilder<
          GetChargeIssuerToken, IssuerTokensResource, IssuerToken> {

    public GetChargeIssuerToken(Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
      super(retrofit, storeId, chargeId);
    }

    @Override
    protected Call<IssuerToken> getRequest(IssuerTokensResource resource) {
      return resource.getChargeIssuerToken(storeId, chargeId);
    }
  }

  public static class GetThreeDsChargeChargeIssuerToken
      extends AbstractIssuerTokensBuilders.AbstractGetChargeIssuerTokenRequestBuilder<
          GetChargeIssuerToken, IssuerTokensResource, IssuerToken> {

    public GetThreeDsChargeChargeIssuerToken(
        Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
      super(retrofit, storeId, chargeId);
    }

    @Override
    protected Call<IssuerToken> getRequest(IssuerTokensResource resource) {
      return resource.getChargeThreeDsIssuerToken(storeId, chargeId);
    }
  }

  public static class GetThreeDsTokenChargeIssuerToken
      extends AbstractIssuerTokensBuilders.AbstractGetTokenIssuerTokenRequestBuilder<
          GetThreeDsTokenChargeIssuerToken, IssuerTokensResource, IssuerToken> {

    public GetThreeDsTokenChargeIssuerToken(
        Retrofit retrofit, StoreId storeId, TransactionTokenId transactionTokenId) {
      super(retrofit, storeId, transactionTokenId);
    }

    @Override
    protected Call<IssuerToken> getRequest(IssuerTokensResource resource) {
      return resource.getTokenThreeDsIssuerToken(storeId, transactionTokenId);
    }
  }
}
