package com.univapay.sdk.utils.mockcontent;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.univapay.sdk.utils.ContentType;

public class JsonMockContent extends MockContent<String> {

  public JsonMockContent(String content) {
    super(ContentType.APPLICATION_JSON, content);
  }

  @Override
  public ResponseDefinitionBuilder createResponse(int status) {
    ResponseDefinitionBuilder response =
        aResponse().withStatus(status).withHeader(ContentType.header, getContentType().toString());

    if (!(getContent() == null || getContent().isEmpty())) {
      response
          .withHeader(MockContent.ContentLengthHeader, String.valueOf(getLength()))
          .withBody(getContent());
    }

    return response;
  }

  @Override
  public int getLength() {
    return getContent().length();
  }
}
