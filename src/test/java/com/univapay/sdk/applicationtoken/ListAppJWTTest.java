package com.univapay.sdk.applicationtoken;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.PaginatedList;
import com.univapay.sdk.models.response.applicationtoken.ApplicationJWT;
import com.univapay.sdk.models.response.applicationtoken.MerchantApplicationJWT;
import com.univapay.sdk.models.response.applicationtoken.StoreApplicationJWT;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.Test;

class ListAppJWTTest extends GenericTest {

  @Test
  void shouldRequestListOfMerchantAppJWT() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET", "/app_jwts", jwt, 200, StoreFakeRR.listMerchantAppJWTFakeResponse, null);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2018-04-06T13:19:30.639099+09:00", DateTimeFormatter.ISO_DATE_TIME);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    PaginatedList<MerchantApplicationJWT> appJWTs =
        univapay.listMerchantAppJWT().build().dispatch();
    assertFalse(appJWTs.getItems().isEmpty());
    assertFalse(appJWTs.getHasMore());
    List<MerchantApplicationJWT> items = appJWTs.getItems();
    assertEquals(4, items.size());
    ApplicationJWT firstItem = items.get(0);
    assertEquals("11e83951-b3c5-d3ac-8d91-5be6827e5e4c", firstItem.getMerchantId().toString());
    assertEquals("11e83951-b3c5-d3ac-8d91-5be6827e5e4c", firstItem.getCreatorId().toString());
    assertEquals("11e83951-b2fb-06de-9ee2-ff98594e423b", firstItem.getId().toString());
    assertEquals(
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcHBfdG9rZW4iLCJpYXQiOjE1MjI5ODgzNjksIm1lcmNoYW50X2lkIjoiMTFlODM5NTEtYjNjNS1kM2FjLThkOTEtNWJlNjgyN2U1ZTRjIiwic3RvcmVfaWQiOiIxMWU4Mzk1MS1iM2NhLWNjMmMtOTRhZS0wN2E2NTQ3NjJiOGMiLCJkb21haW5zIjpbXSwibW9kZSI6ImxpdmVfdGVzdCIsImNyZWF0b3JfaWQiOiIxMWU4Mzk1MS1iM2M1LWQzYWMtOGQ5MS01YmU2ODI3ZTVlNGMiLCJ2ZXJzaW9uIjoxLCJqdGkiOiIxMWU4Mzk1MS1iMmZiLTA2ZGUtOWVlMi1mZjk4NTk0ZTQyM2IifQ.YNIYpOo4DRiGy4t8PyE99qE0JupV1C3gw4K2aIw9rCU",
        firstItem.getJwt());
    assertEquals(firstItem.getCreatedOn(), parsedDate);
  }

  @Test
  void shouldRequestListOfStoreAppJWT() throws Exception {

    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "GET",
        "/stores/11e83951-b3ca-cc2c-94ae-07a654762b8c/app_jwts",
        jwt,
        200,
        StoreFakeRR.listStoreAppJWTFakeResponse,
        null);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2018-04-06T13:19:30.639099+09:00", DateTimeFormatter.ISO_DATE_TIME);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    PaginatedList<StoreApplicationJWT> appJWTs =
        univapay
            .listStoreAppJWT(new StoreId("11e83951-b3ca-cc2c-94ae-07a654762b8c"))
            .build()
            .dispatch();
    assertFalse(appJWTs.getItems().isEmpty());
    assertFalse(appJWTs.getHasMore());
    List<StoreApplicationJWT> items = appJWTs.getItems();
    assertEquals(1, items.size());
    StoreApplicationJWT firstItem = items.get(0);
    assertEquals("11e83951-b3c5-d3ac-8d91-5be6827e5e4c", firstItem.getMerchantId().toString());
    assertEquals("11e83951-b3ca-cc2c-94ae-07a654762b8c", firstItem.getStoreId().toString());
    assertEquals("11e83951-b3c5-d3ac-8d91-5be6827e5e4c", firstItem.getCreatorId().toString());
    assertEquals("11e83951-b2fb-06de-9ee2-ff98594e423b", firstItem.getId().toString());
    assertEquals(
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcHBfdG9rZW4iLCJpYXQiOjE1MjI5ODgzNjksIm1lcmNoYW50X2lkIjoiMTFlODM5NTEtYjNjNS1kM2FjLThkOTEtNWJlNjgyN2U1ZTRjIiwic3RvcmVfaWQiOiIxMWU4Mzk1MS1iM2NhLWNjMmMtOTRhZS0wN2E2NTQ3NjJiOGMiLCJkb21haW5zIjpbXSwibW9kZSI6ImxpdmVfdGVzdCIsImNyZWF0b3JfaWQiOiIxMWU4Mzk1MS1iM2M1LWQzYWMtOGQ5MS01YmU2ODI3ZTVlNGMiLCJ2ZXJzaW9uIjoxLCJqdGkiOiIxMWU4Mzk1MS1iMmZiLTA2ZGUtOWVlMi1mZjk4NTk0ZTQyM2IifQ.YNIYpOo4DRiGy4t8PyE99qE0JupV1C3gw4K2aIw9rCU",
        firstItem.getJwt());
    assertEquals(firstItem.getCreatedOn(), parsedDate);
  }
}
