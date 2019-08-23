package com.univapay.sdk.utils;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.client.MappingBuilder;

public enum RequestMethod {
  GET,
  POST,
  PATCH,
  DELETE;

  public MappingBuilder getStub(String path) {

    MappingBuilder stub;
    switch (this) {
      case GET:
        stub = get(urlEqualTo(path));
        break;
      case POST:
        stub = post(urlEqualTo(path));
        break;
      case PATCH:
        stub = patch(urlEqualTo(path));
        break;
      default:
        stub = delete(urlEqualTo(path));
        break;
    }

    return stub;
  }

  public static RequestMethod fromString(String method) {
    switch (method) {
      case "GET":
        return GET;
      case "POST":
        return POST;
      case "PATCH":
        return PATCH;
      case "DELETE":
        return DELETE;
      default:
        throw new IllegalArgumentException("Request method not supported");
    }
  }
}
