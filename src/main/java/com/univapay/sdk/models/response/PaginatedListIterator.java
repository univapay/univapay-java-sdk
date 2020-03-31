package com.univapay.sdk.models.response;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.builders.RetrofitRequestBuilderPaginated;
import com.univapay.sdk.models.common.BaseId;
import com.univapay.sdk.models.errors.TooManyRequestsException;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.types.CursorDirection;
import com.univapay.sdk.utils.Backoff;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

public class PaginatedListIterator<T extends SimpleModel> extends UnivapayResponse
    implements UnivapayPaginatedListIterator<T> {

  public PaginatedListIterator(
      RetrofitRequestBuilderPaginated lastBuilder, long timeoutMilliSec, Backoff backoff) {
    this.items = new ArrayList<>();
    this.hasMore = true;
    this.lastBuilder = lastBuilder;
    this.nextCursor = lastBuilder.getCursor();
    this.timeoutMilliSec = timeoutMilliSec;
    this.backoff = backoff;
  }

  @SerializedName("items")
  private List<T> items;

  @SerializedName("has_more")
  private Boolean hasMore;

  private RetrofitRequestBuilderPaginated lastBuilder;
  private BaseId nextCursor;
  private Backoff backoff;
  private long timeoutMilliSec;

  boolean isNotFirstTime() {
    return (!items.isEmpty() || !hasMore);
  }

  void setNextCursor(BaseId nextCursor) {
    this.nextCursor = nextCursor;
  }

  void setHasMore(boolean hasMore) {
    this.hasMore = hasMore;
  }

  @Override
  public boolean hasNext() {
    return hasMore;
  }

  /**
   * Get a next page from API if it has more pages.
   *
   * @return items
   */
  @Override
  public List<T> next() {
    if (lastBuilder == null || backoff == null) {
      throw new UnsupportedOperationException(
          "use 'RetrofitRequestBuilderPaginated.asIterable()' before use 'next()' ");
    }
    if (!hasMore) {
      throw new NoSuchElementException();
    }

    PaginatedList<T> dispatchedList;
    if (isNotFirstTime()) {
      lastBuilder.setCursor(nextCursor);
    }
    dispatchedList = await();
    items = dispatchedList.getItems();
    hasMore = dispatchedList.getHasMore();
    if (!items.isEmpty()) {
      nextCursor = items.get(items.size() - 1).getId();
    }

    return items;
  }

  private PaginatedList<T> await() {
    PaginatedList<T> dispatchedList;

    backoff.reset();
    final long startTime = System.currentTimeMillis();

    do {
      if (System.currentTimeMillis() - startTime > timeoutMilliSec) {
        throw new IllegalStateException(
            new TimeoutException(String.format("Retry timeout exceeded : %dms ", timeoutMilliSec)));
      }

      try {
        dispatchedList = (PaginatedList<T>) lastBuilder.build().dispatch();
      } catch (TooManyRequestsException | IOException e) {
        dispatchedList = null;
        try {
          Thread.sleep(backoff.next());
        } catch (InterruptedException eb) {
          throw new IllegalStateException(eb);
        }
      } catch (UnivapayException e) {
        throw new IllegalStateException(e);
      }
    } while (dispatchedList == null);

    return dispatchedList;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("remove");
  }

  public UnivapayPaginatedListIterator<T> reverse() {
    CursorDirection cursorDirection =
        (lastBuilder.getCursorDirection() == CursorDirection.ASC)
            ? CursorDirection.DESC
            : CursorDirection.ASC;
    lastBuilder.setCursorDirection(cursorDirection);

    if (isNotFirstTime()) {
      if (!items.isEmpty()) {
        nextCursor = items.get(0).getId();
      }
      lastBuilder.setCursor(nextCursor);
      hasMore = true;
    }

    return this;
  }

  public List<T> getItems() {
    return Collections.unmodifiableList(items);
  }

  public Boolean getHasMore() {
    return hasMore;
  }

  public RetrofitRequestBuilderPaginated getLastBuilder() {
    return lastBuilder;
  }

  public BaseId getNextCursor() {
    return nextCursor;
  }
}
