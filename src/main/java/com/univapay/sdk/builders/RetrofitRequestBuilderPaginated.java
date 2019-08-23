package com.univapay.sdk.builders;

import com.univapay.sdk.models.common.BaseId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.PaginatedListIterable;
import com.univapay.sdk.models.response.PaginatedListIterator;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.utils.Backoff;
import com.univapay.sdk.utils.ExponentialBackoff;
import retrofit2.Retrofit;

/** Created by nasu on 2017/06/29. */
public abstract class RetrofitRequestBuilderPaginated<E extends SimpleModel, R, T, C extends BaseId>
    extends RetrofitRequestBuilder<PaginatedList<E>, R> implements Paginator<E, T, C> {

  private Integer limit;
  private CursorDirection cursorDirection;
  private C cursor;

  public RetrofitRequestBuilderPaginated(Retrofit retrofit) {
    super(retrofit);
  }

  @Override
  public T setLimit(Integer limit) {
    this.limit = limit;
    return (T) this;
  }

  @Override
  public T setCursorDirection(CursorDirection cursorDirection) {
    this.cursorDirection = cursorDirection;
    return (T) this;
  }

  @Override
  public T setCursor(C cursor) {
    this.cursor = cursor;
    return (T) this;
  }

  public Integer getLimit() {
    return limit;
  }

  public CursorDirection getCursorDirection() {
    return cursorDirection;
  }

  public C getCursor() {
    return cursor;
  }

  public PaginatedListIterable<E> asIterable() {
    PaginatedListIterator<E> list = new PaginatedListIterator<>(this, 900_000L, createBackoff());
    return new PaginatedListIterable<>(list);
  }

  public PaginatedListIterable<E> asIterable(long timeout) {
    PaginatedListIterator<E> list = new PaginatedListIterator<>(this, timeout, createBackoff());
    return new PaginatedListIterable<>(list);
  }

  public PaginatedListIterable<E> asIterable(long timeout, Backoff backoff) {
    PaginatedListIterator<E> list = new PaginatedListIterator<>(this, timeout, backoff);
    return new PaginatedListIterable<>(list);
  }

  private Backoff createBackoff() {
    return new ExponentialBackoff(1_000L, 30_000L, 1.1, 0.5);
  }
}
