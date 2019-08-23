package com.univapay.sdk.builders.ledgers;

import com.univapay.sdk.builders.RetrofitRequestBuilderPaginated;
import com.univapay.sdk.models.common.LedgerId;
import com.univapay.sdk.models.common.TransferId;
import com.univapay.sdk.models.response.ledger.Ledger;
import retrofit2.Retrofit;

public abstract class AbstractLedgersBuilders {

  public abstract static class AbstractListLedgersRequestBuilder<
          B extends AbstractListLedgersRequestBuilder, R, M extends Ledger>
      extends RetrofitRequestBuilderPaginated<M, R, B, LedgerId> {
    protected Boolean all;
    protected String from;
    protected String to;
    protected Integer min;
    protected Integer max;
    protected String currency;
    protected TransferId transferId;

    protected Boolean isAll() {
      return all;
    }

    protected String getFrom() {
      return from;
    }

    protected String getTo() {
      return to;
    }

    protected Integer getMin() {
      return min;
    }

    protected Integer getMax() {
      return max;
    }

    protected String getCurrency() {
      return currency;
    }

    protected TransferId getTransferId() {
      return transferId;
    }

    public AbstractListLedgersRequestBuilder(Retrofit retrofit, TransferId transferId) {
      super(retrofit);
      this.transferId = transferId;
    }

    public B withAll(Boolean all) {
      this.all = all;
      return (B) this;
    }

    public B withFrom(String from) {
      this.from = from;
      return (B) this;
    }

    public B withTo(String to) {
      this.to = to;
      return (B) this;
    }

    public B withMin(Integer min) {
      this.min = min;
      return (B) this;
    }

    public B withMax(Integer max) {
      this.max = max;
      return (B) this;
    }

    public B withCurrency(String currency) {
      this.currency = currency;
      return (B) this;
    }
  }
}
