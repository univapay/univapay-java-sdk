package com.univapay.sdk.builders.transfer;

import com.univapay.sdk.builders.transfer.AbstractTransferBuilders.AbstractGetTransferRequestBuilder;
import com.univapay.sdk.builders.transfer.AbstractTransferBuilders.AbstractListTransferRequestBuilder;
import com.univapay.sdk.models.common.TransferId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.transfer.Transfer;
import com.univapay.sdk.resources.TransfersResource;
import retrofit2.Call;
import retrofit2.Retrofit;

public abstract class TransferBuilders {

  public static class ListTransferRequestBuilder
      extends AbstractListTransferRequestBuilder<
                ListTransferRequestBuilder, TransfersResource, Transfer> {

    public ListTransferRequestBuilder(Retrofit retrofit) {
      super(retrofit);
    }

    @Override
    protected Call<PaginatedList<Transfer>> getRequest(TransfersResource resource) {
      return resource.list(getLimit(), getCursorDirection(), getCursor());
    }
  }

  public static class GetTransferRequestBuilder
      extends AbstractGetTransferRequestBuilder<
                GetTransferRequestBuilder, TransfersResource, Transfer> {

    public GetTransferRequestBuilder(Retrofit retrofit, TransferId transferId) {
      super(retrofit, transferId);
    }

    @Override
    protected Call<Transfer> getRequest(TransfersResource resource) {
      return resource.get(transferId);
    }
  }
}
