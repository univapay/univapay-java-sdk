package com.univapay.sdk.applicationtoken;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.collect.Sets;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.Domain;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.applicationtoken.MerchantApplicationJWT;
import com.univapay.sdk.models.response.applicationtoken.StoreApplicationJWT;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.types.MerchantRole;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.StoreFakeRR;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CreateAppJWTTest extends GenericTest {

  @Test
  void shouldPostMerchantAppJWTDataAndReturnInfo() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/app_jwts",
        jwt,
        200,
        StoreFakeRR.createMerchantAppJWTFakeResponse,
        StoreFakeRR.createMerchantAppJWTFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2018-04-10T12:39:45.408204+09:00", DateTimeFormatter.ISO_DATE_TIME);

    Set<MerchantRole> roles = new HashSet<>();
    roles.add(MerchantRole.MERCHANT);

    MerchantApplicationJWT appJWT =
        univapay.createMerchantAppJWT().withRoles(roles).build().dispatch();

    assertEquals("11e83c6c-14db-30de-b9a5-17a790ae22a2", appJWT.getMerchantId().toString());
    assertEquals(appJWT.getRoles(), Sets.newHashSet(MerchantRole.MERCHANT));
    assertEquals("11e83c6c-14db-30de-b9a5-17a790ae22a2", appJWT.getCreatorId().toString());
    assertEquals("11e83c70-ce67-e858-b28f-1dc0c2892b77", appJWT.getId().toString());
    assertEquals("m4YkAf3B4A0h6JDeouN1", appJWT.getSecret());
    assertEquals(
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcHBfdG9rZW4iLCJpYXQiOjE1MjMzMzE1ODMsIm1lcmNoYW50X2lkIjoiMTFlODNjNmMtMTRkYi0zMGRlLWI5YTUtMTdhNzkwYWUyMmEyIiwicm9sZXMiOlsibWVyY2hhbnQiXSwiY3JlYXRvcl9pZCI6IjExZTgzYzZjLTE0ZGItMzBkZS1iOWE1LTE3YTc5MGFlMjJhMiIsInZlcnNpb24iOjEsImp0aSI6IjExZTgzYzcwLWNlNjctZTg1OC1iMjhmLTFkYzBjMjg5MmI3NyJ9.6VKtNDfvFuiuaW6wRtPThRCA3w4v4AQJeKUHL0Ly8ZU",
        appJWT.getJwt());
    assertEquals(appJWT.getCreatedOn(), parsedDate);
  }

  @Test
  void shouldPostStoreAppJWTDataAndReturnInfo() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/stores/11e83951-b3ca-cc2c-94ae-07a654762b8c/app_jwts",
        jwt,
        200,
        StoreFakeRR.createStoreAppJWTFakeResponse,
        StoreFakeRR.createStoreAppJWTFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final OffsetDateTime parsedDate =
        OffsetDateTime.parse("2018-04-10T12:30:33.284708+09:00", DateTimeFormatter.ISO_DATE_TIME);

    StoreApplicationJWT appJWT =
        univapay
            .createStoreAppJWT(new StoreId("11e83951-b3ca-cc2c-94ae-07a654762b8c"))
            .withMode(ProcessingMode.TEST)
            .withDomains(Collections.singletonList(Domain.ANY))
            .build()
            .dispatch();

    assertEquals("11e83c6c-14db-30de-b9a5-17a790ae22a2", appJWT.getMerchantId().toString());
    assertEquals("11e83c6c-16a4-e7a2-b9a5-cfab31f2d2e5", appJWT.getStoreId().toString());
    assertEquals("11e83c6c-14db-30de-b9a5-17a790ae22a2", appJWT.getCreatorId().toString());
    assertEquals("11e83c6f-8587-4002-b28f-abc9bcd96cd3", appJWT.getId().toString());
    assertEquals("FWCnurxE1dOKUZ4oAuTz", appJWT.getSecret());
    assertEquals(
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcHBfdG9rZW4iLCJpYXQiOjE1MjMzMzEwMzEsIm1lcmNoYW50X2lkIjoiMTFlODNjNmMtMTRkYi0zMGRlLWI5YTUtMTdhNzkwYWUyMmEyIiwic3RvcmVfaWQiOiIxMWU4M2M2Yy0xNmE0LWU3YTItYjlhNS1jZmFiMzFmMmQyZTUiLCJkb21haW5zIjpbIioiXSwibW9kZSI6InRlc3QiLCJjcmVhdG9yX2lkIjoiMTFlODNjNmMtMTRkYi0zMGRlLWI5YTUtMTdhNzkwYWUyMmEyIiwidmVyc2lvbiI6MSwianRpIjoiMTFlODNjNmYtODU4Ny00MDAyLWIyOGYtYWJjOWJjZDk2Y2QzIn0.mXXUWcbmBuIe3xCQSEjzMd7kTwQRsOW05yd9mE6gbwo",
        appJWT.getJwt());
    assertEquals(appJWT.getCreatedOn(), parsedDate);
  }
}
