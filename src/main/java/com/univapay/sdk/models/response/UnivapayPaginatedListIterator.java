package com.univapay.sdk.models.response;

import java.util.Iterator;
import java.util.List;

public interface UnivapayPaginatedListIterator<T extends SimpleModel> extends Iterator<List<T>> {

  UnivapayPaginatedListIterator<T> reverse();

  @Override
  boolean hasNext();

  @Override
  List<T> next();
}
