package com.univapay.sdk.utils.mockcontent;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.univapay.sdk.utils.ContentType;

public class ImageMockContent extends MockContent<byte[]> {

  public ImageMockContent(byte[] content) {
    super(ContentType.APPLICATION_IMAGE, content);
  }

  @Override
  public ResponseDefinitionBuilder createResponse(int status) {
    ResponseDefinitionBuilder response =
        aResponse().withStatus(status).withHeader(ContentType.header, getContentType().name());

    if (!(getContent() == null || getContent().length == 0)) {
      response.withHeader(ContentLengthHeader, String.valueOf(getLength())).withBody(getContent());
    }

    return response;
  }

  @Override
  public int getLength() {
    return getContent().length;
  }
}
