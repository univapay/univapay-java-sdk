package com.univapay.sdk.builders.issuerToken;

import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.response.IssuerToken;
import retrofit2.Retrofit;

public class AbstractIssuerTokensBuilders {

  public abstract static class AbstractGetChargeIssuerTokenRequestBuilder<
          B extends AbstractGetChargeIssuerTokenRequestBuilder<B, R, M>, R, M extends IssuerToken>
      extends RetrofitRequestBuilder<M, R> {

    protected StoreId storeId;
    protected ChargeId chargeId;

    public AbstractGetChargeIssuerTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, ChargeId chargeId) {
      super(retrofit);
      this.storeId = storeId;
      this.chargeId = chargeId;
    }
  }

  public abstract static class AbstractGetTokenIssuerTokenRequestBuilder<
          B extends AbstractGetTokenIssuerTokenRequestBuilder<B, R, M>, R, M extends IssuerToken>
      extends RetrofitRequestBuilder<M, R> {

    protected StoreId storeId;
    protected TransactionTokenId transactionTokenId;

    public AbstractGetTokenIssuerTokenRequestBuilder(
        Retrofit retrofit, StoreId storeId, TransactionTokenId transactionTokenId) {
      super(retrofit);
      this.storeId = storeId;
      this.transactionTokenId = transactionTokenId;
    }
  }
}
