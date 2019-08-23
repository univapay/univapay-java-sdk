package com.univapay.sdk.builders.ledgers;

import com.univapay.sdk.models.common.TransferId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.ledger.Ledger;
import com.univapay.sdk.resources.LedgersResource;
import retrofit2.Call;
import retrofit2.Retrofit;

public abstract class LedgersBuilders {

  public static class ListLedgersRequestBuilder
      extends AbstractLedgersBuilders.AbstractListLedgersRequestBuilder<
          ListLedgersRequestBuilder, LedgersResource, Ledger> {

    public ListLedgersRequestBuilder(Retrofit retrofit, TransferId transferId) {
      super(retrofit, transferId);
    }

    @Override
    protected Call<PaginatedList<Ledger>> getRequest(LedgersResource resource) {
      return resource.listLedgers(
          transferId,
          all,
          from,
          to,
          min,
          max,
          currency,
          getLimit(),
          getCursorDirection(),
          getCursor());
    }
  }
}
