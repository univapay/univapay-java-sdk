package com.univapay.sdk.models.response;

import com.univapay.sdk.types.CursorDirection;
import java.util.List;
import com.univapay.sdk.builders.RetrofitRequestBuilderPaginated;

public class PaginatedListIterable<T extends SimpleModel> implements Iterable<List<T>> {
  private final PaginatedListIterator<T> paginatedList;

  public PaginatedListIterator<T> getPaginatedList() {
    return paginatedList;
  }

  public PaginatedListIterable(PaginatedListIterator<T> paginatedList) {
    this.paginatedList = paginatedList;
  }

  @Override
  public UnivapayPaginatedListIterator<T> iterator() {
    if (paginatedList == null) {
      throw new NullPointerException();
    }
    return paginatedList;
  }

  public PaginatedListIterable<T> reverse() {
    RetrofitRequestBuilderPaginated lastBuilder = paginatedList.getLastBuilder();
    CursorDirection cursorDirection =
        (lastBuilder.getCursorDirection() == CursorDirection.ASC)
            ? CursorDirection.DESC
            : CursorDirection.ASC;
    lastBuilder.setCursorDirection(cursorDirection);

    if (paginatedList.isNotFirstTime()) {
      List<T> items = paginatedList.getItems();
      if (!items.isEmpty()) {
        paginatedList.setNextCursor(items.get(0).getId());
      }
      lastBuilder.setCursor(paginatedList.getNextCursor());
      paginatedList.setHasMore(true);
    }

    return this;
  }
}
