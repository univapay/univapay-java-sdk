package com.univapay.sdk.utils;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.univapay.sdk.constants.UnivapayConstants;
import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.IdempotencyStatus;
import com.univapay.sdk.utils.mockcontent.JsonMockContent;
import com.univapay.sdk.utils.mockcontent.MockContent;
import com.univapay.sdk.utils.paginationmock.PaginatedMock;
import java.util.Map;

public class MockRRGenerator implements MockServer {

  //    TODO: this should be refactored considerable to reduce code replication

  IdempotencyKey idempotencyKey;
  IdempotencyStatus idempotencyStatus;
  String contentType = "application/json";

  @Override
  public MockRRGenerator withIdempotencySettings(
      IdempotencyKey idempotencyKey, IdempotencyStatus idempotencyStatus) {
    this.idempotencyKey = idempotencyKey;
    this.idempotencyStatus = idempotencyStatus;
    return this;
  }

  @Override
  public MockRRGenerator withContentType(String contentType) {
    this.contentType = contentType;
    return this;
  }

  private ResponseDefinitionBuilder emptyResponse(int status) {
    return aResponse().withStatus(status);
  }

  public MappingBuilder createStub(
      RequestMethod method,
      String path,
      String token,
      int status,
      MockContent responseBody,
      String requestBody,
      AuthType authType,
      Map<String, String> headerMap) {

    MappingBuilder stub = method.getStub(path);

    if (!(token == null || token.isEmpty())) {
      stub.withHeader("Authorization", containing(authType.getPrefix() + " " + token));
    }
    if (!(requestBody == null || requestBody.isEmpty())) {
      stub.withRequestBody(equalToJson(requestBody));
    }

    ResponseDefinitionBuilder response;
    if (responseBody == null) {
      response = emptyResponse(status);
    } else response = responseBody.createResponse(status);

    if (idempotencyStatus != null) {
      response.withHeader(UnivapayConstants.idempotencyStatusHeaderName, idempotencyStatus.name());
    }

    stub.willReturn(response);

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

  public void GenerateMockRequestResponseJWT(
      String method,
      String path,
      String token,
      int status,
      String responseBody,
      String requestBody) {
    MappingBuilder stub =
        createStub(
            RequestMethod.fromString(method),
            path,
            token,
            status,
            new JsonMockContent(responseBody),
            requestBody,
            AuthType.JWT,
            null);
    stubFor(stub);
  }

  public void GenerateMockRequestResponseJWT(
      String method, String path, String token, int status, String responseBody) {
    MappingBuilder stub =
        createStub(
            RequestMethod.fromString(method),
            path,
            token,
            status,
            new JsonMockContent(responseBody),
            null,
            AuthType.JWT,
            null);
    stubFor(stub);
  }

  public void GenerateMockRequestResponseJWT(
      String method, String path, String token, int status, PaginatedMock paginatedMock) {
    MappingBuilder stub =
        createStub(
            RequestMethod.fromString(method),
            path,
            token,
            status,
            new JsonMockContent(paginatedMock.getPaginatedResponse(path)),
            null,
            AuthType.JWT,
            null);
    stubFor(stub);
  }

  public void GenerateMockRequestResponseJWT(
      String method,
      String path,
      String token,
      int status,
      String responseBody,
      String requestBody,
      Map<String, String> headerMap) {
    MappingBuilder stub =
        createStub(
            RequestMethod.fromString(method),
            path,
            token,
            status,
            new JsonMockContent(responseBody),
            requestBody,
            AuthType.JWT,
            headerMap);
    stubFor(stub);
  }

  public void GenerateMockRequestResponseJWT(
      String method, String path, String token, int status, MockContent responseBody) {
    MappingBuilder stub =
        createStub(
            RequestMethod.fromString(method),
            path,
            token,
            status,
            responseBody,
            null,
            AuthType.JWT,
            null);
    stubFor(stub);
  }

  public void GenerateMockRequestResponseJWT(
      String method,
      String path,
      String token,
      int status,
      MockContent responseBody,
      String requestBody) {
    MappingBuilder stub =
        createStub(
            RequestMethod.fromString(method),
            path,
            token,
            status,
            responseBody,
            requestBody,
            AuthType.JWT,
            null);
    stubFor(stub);
  }
}
