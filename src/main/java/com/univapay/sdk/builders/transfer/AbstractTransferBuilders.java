package com.univapay.sdk.builders.transfer;

import com.univapay.sdk.builders.RetrofitRequestBuilder;
import com.univapay.sdk.builders.RetrofitRequestBuilderPaginated;
import com.univapay.sdk.models.common.TransferId;
import com.univapay.sdk.models.response.transfer.Transfer;
import retrofit2.Retrofit;

public abstract class AbstractTransferBuilders {

  public abstract static class AbstractListTransferRequestBuilder<
          B extends AbstractListTransferRequestBuilder, R, M extends Transfer>
      extends RetrofitRequestBuilderPaginated<M, R, B, TransferId> {

    public AbstractListTransferRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }
  }

  public abstract static class AbstractGetTransferRequestBuilder<
          B extends AbstractGetTransferRequestBuilder, R, M extends Transfer>
      extends RetrofitRequestBuilder<M, R> {

    protected TransferId transferId;

    protected TransferId getTransferId() {
      return transferId;
    }

    public AbstractGetTransferRequestBuilder(Retrofit retrofit, TransferId transferId) {
      super(retrofit);
      this.transferId = transferId;
    }
  }
}
