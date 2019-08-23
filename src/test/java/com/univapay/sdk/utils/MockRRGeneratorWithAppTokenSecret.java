package com.univapay.sdk.utils;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.univapay.sdk.constants.UnivapayConstants;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.types.IdempotencyStatus;
import java.util.Map;

public class MockRRGeneratorWithAppTokenSecret implements MockServer {

  IdempotencyKey idempotencyKey;
  IdempotencyStatus idempotencyStatus;
  String contentType = "application/json";

  @Override
  public MockRRGeneratorWithAppTokenSecret withIdempotencySettings(
      IdempotencyKey idempotencyKey, IdempotencyStatus idempotencyStatus) {
    this.idempotencyKey = idempotencyKey;
    this.idempotencyStatus = idempotencyStatus;
    return this;
  }

  @Override
  public MockRRGeneratorWithAppTokenSecret withContentType(String contentType) {
    this.contentType = contentType;
    return this;
  }

  private MappingBuilder createStub(
      RequestMethod method,
      String path,
      String app_token,
      String secret,
      int status,
      String responseBody,
      String requestBody,
      Map<String, String> headerMap) {

    MappingBuilder stub = method.getStub(path);

    if (!(app_token == null || app_token.isEmpty()) && !(secret == null || secret.isEmpty())) {
      stub.withHeader("Authorization", containing("ApplicationToken " + app_token + "|" + secret));
    }
    if (!(requestBody == null || requestBody.isEmpty())) {
      stub.withRequestBody(equalToJson(requestBody));
    }

    if (!(responseBody == null || responseBody.isEmpty())) {
      if (idempotencyStatus == null) {
        stub.willReturn(
            aResponse()
                .withStatus(status)
                .withHeader("Content-Type", contentType)
                .withBody(responseBody));
      } else {
        stub.willReturn(
            aResponse()
                .withStatus(status)
                .withHeaders(
                    new HttpHeaders()
                        .plus(HttpHeader.httpHeader("Content-Type", contentType))
                        .plus(
                            HttpHeader.httpHeader(
                                UnivapayConstants.idempotencyStatusHeaderName,
                                idempotencyStatus.name())))
                .withBody(responseBody));
      }
    }

    if (idempotencyKey != null) {
      stub.withHeader(
          UnivapayConstants.idempotencyKeyHeaderName, containing(this.idempotencyKey.getKey()));
    }

    if (headerMap != null) {
      for (Map.Entry<String, String> entry : headerMap.entrySet()) {
        stub.withHeader(entry.getKey(), containing(entry.getValue()));
      }
    }

    return stub;
  }

  public void GenerateMockRequestResponse(
      String method, String path, String app_token, String secret, int status) {
    MappingBuilder stub =
        createStub(
            RequestMethod.fromString(method), path, app_token, secret, status, null, null, null);
    stubFor(stub);
  }

  public void GenerateMockRequestResponse(
      String method,
      String path,
      String app_token,
      String secret,
      int status,
      String responseBody) {
    MappingBuilder stub =
        createStub(
            RequestMethod.fromString(method),
            path,
            app_token,
            secret,
            status,
            responseBody,
            null,
            null);
    stubFor(stub);
  }

  public void GenerateMockRequestResponse(
      String method,
      String path,
      String app_token,
      String secret,
      int status,
      String responseBody,
      String requestBody) {
    MappingBuilder stub =
        createStub(
            RequestMethod.fromString(method),
            path,
            app_token,
            secret,
            status,
            responseBody,
            requestBody,
            null);
    stubFor(stub);
  }

  public void GenerateMockRequestResponse(
      String method,
      String path,
      String app_token,
      String secret,
      int status,
      String responseBody,
      String requestBody,
      Map<String, String> headerMap) {
    MappingBuilder stub =
        createStub(
            RequestMethod.fromString(method),
            path,
            app_token,
            secret,
            status,
            responseBody,
            requestBody,
            headerMap);
    stubFor(stub);
  }
}
