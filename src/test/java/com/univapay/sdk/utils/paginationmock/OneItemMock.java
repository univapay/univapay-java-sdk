package com.univapay.sdk.utils.paginationmock;

import lombok.Getter;

@Getter
public class OneItemMock {
  private final String id;
  private final String responseBody;

  public OneItemMock(String id, String responseBody) {
    this.id = id;
    this.responseBody = responseBody;
  }
}
