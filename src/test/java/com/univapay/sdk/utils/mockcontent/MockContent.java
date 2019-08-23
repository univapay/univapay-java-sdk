package com.univapay.sdk.utils.mockcontent;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.univapay.sdk.utils.ContentType;

public abstract class MockContent<T> {

  private ContentType contentType;
  private T content;

  public MockContent(ContentType contentType, T content) {
    this.contentType = contentType;
    this.content = content;
  }

  public ContentType getContentType() {
    return contentType;
  }

  public T getContent() {
    return content;
  }

  public abstract ResponseDefinitionBuilder createResponse(int status);

  public abstract int getLength();

  public static final String ContentLengthHeader = "Content-Length";
}
