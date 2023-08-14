package com.univapay.sdk.utils.mockcontent;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.univapay.sdk.utils.ContentType;
import lombok.Getter;

@Getter
public abstract class MockContent<T> {

  private final ContentType contentType;
  private final T content;

  public MockContent(ContentType contentType, T content) {
    this.contentType = contentType;
    this.content = content;
  }

  public abstract ResponseDefinitionBuilder createResponse(int status);

  public abstract int getLength();

  public static final String ContentLengthHeader = "Content-Length";
}
