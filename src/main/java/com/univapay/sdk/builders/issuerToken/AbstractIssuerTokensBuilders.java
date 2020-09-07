package com.univapay.sdk.builders.issuerToken;

import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.IssuerToken;
import retrofit2.Retrofit;

public class AbstractIssuerTokensBuilders {

  public abstract static class AbstractGetIssuerTokenRequestBuilder<
          B extends AbstractGetIssuerTokenRequestBuilder<B, R, M>, R, M extends IssuerToken>
      extends RetrofitRequestBuilder<M, R> {

    protected StoreId storeId;
    protected ChargeId chargeId;

    public AbstractGetIssuerTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
      super(retrofit);
      this.storeId = storeId;
      this.chargeId = chargeId;
    }
  }
}
