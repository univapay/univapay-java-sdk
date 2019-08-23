package com.univapay.sdk.models.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/*
<h2>PAGINATED LIST</h2>
 */

/**
 * The Paginated list formats the response to include the <code>items</code> and <code>has_more
 * </code> fields, which are specific to paginated responses.
 *
 * @param <T> The type of the individual items.
 */
public class PaginatedList<T extends SimpleModel> extends UnivapayResponse {

  @SerializedName("items")
  private List<T> items;

  @SerializedName("has_more")
  private Boolean hasMore;

  public List<T> getItems() {
    return items;
  }

  public Boolean getHasMore() {
    return hasMore;
  }
}
