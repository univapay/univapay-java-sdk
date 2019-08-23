package com.univapay.sdk.utils.paginationmock;

public class OneItemMock {
  private String id;
  private String responseBody;

  public OneItemMock(String id, String responseBody) {
    this.id = id;
    this.responseBody = responseBody;
  }

  public String getId() {
    return id;
  }

  public String getResponseBody() {
    return responseBody;
  }
}
