package com.univapay.sdk.builders;

import com.univapay.sdk.models.common.BaseId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.types.CursorDirection;

/*
<h2>PAGINATOR</h2>
 */

/**
 * The paginator interface provides the methods for passing pagination parameters to requests.
 *
 * @param <T> The model of the response.
 * @param <E> The type of request returned by methods that respond with a paginated list. Allows
 *     concatenation.
 * @param <C> The class of the cursor used for pagination, which must extend the BaseId class.
 * @see PaginatedList
 * @see BaseId
 */
public interface Paginator<T extends SimpleModel, E, C extends BaseId> {

  /**
   * Sets the maximum number of responses to be returned by the API.
   *
   * @param limit is an integer value that represents the maximum number of items returned by the
   *     API
   * @return a request object
   */
  E setLimit(Integer limit);

  /**
   * Sets the direction of the cursor.
   *
   * @param cursorDirection is a member of the <code>CursorDirection</code> enum
   * @return a request object
   * @see CursorDirection
   */
  E setCursorDirection(CursorDirection cursorDirection);

  /**
   * Sets the cursor.
   *
   * @param cursor is the cursor used by the API to return a list of items following the other
   *     pagination parameters. The cursor usually equals the value of the ID of some item for which
   *     a list is being queried.
   * @return a request object.
   * @see BaseId
   */
  E setCursor(C cursor);
}
