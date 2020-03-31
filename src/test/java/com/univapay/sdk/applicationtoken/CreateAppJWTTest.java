package com.univapay.sdk.applicationtoken;

import static org.junit.Assert.assertEquals;

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
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class CreateAppJWTTest extends GenericTest {

  @Test
  public void shouldPostMerchantAppJWTDataAndReturnInfo() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/app_jwts",
        jwt,
        200,
        StoreFakeRR.createMerchantAppJWTFakeResponse,
        StoreFakeRR.createMerchantAppJWTFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final Date parsedDate = dateParser.parseDateTime("2018-04-10T12:39:45.408204+09:00").toDate();

    Set<MerchantRole> roles = new HashSet<>();
    roles.add(MerchantRole.MERCHANT);

    MerchantApplicationJWT appJWT =
        univapay.createMerchantAppJWT().withRoles(roles).build().dispatch();

    assertEquals(appJWT.getMerchantId().toString(), "11e83c6c-14db-30de-b9a5-17a790ae22a2");
    assertEquals(appJWT.getRoles(), Sets.newHashSet(MerchantRole.MERCHANT));
    assertEquals(appJWT.getCreatorId().toString(), "11e83c6c-14db-30de-b9a5-17a790ae22a2");
    assertEquals(appJWT.getId().toString(), "11e83c70-ce67-e858-b28f-1dc0c2892b77");
    assertEquals(appJWT.getSecret(), "m4YkAf3B4A0h6JDeouN1");
    assertEquals(
        appJWT.getJwt(),
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcHBfdG9rZW4iLCJpYXQiOjE1MjMzMzE1ODMsIm1lcmNoYW50X2lkIjoiMTFlODNjNmMtMTRkYi0zMGRlLWI5YTUtMTdhNzkwYWUyMmEyIiwicm9sZXMiOlsibWVyY2hhbnQiXSwiY3JlYXRvcl9pZCI6IjExZTgzYzZjLTE0ZGItMzBkZS1iOWE1LTE3YTc5MGFlMjJhMiIsInZlcnNpb24iOjEsImp0aSI6IjExZTgzYzcwLWNlNjctZTg1OC1iMjhmLTFkYzBjMjg5MmI3NyJ9.6VKtNDfvFuiuaW6wRtPThRCA3w4v4AQJeKUHL0Ly8ZU");
    assertEquals(appJWT.getCreatedOn(), parsedDate);
  }

  @Test
  public void shouldPostStoreAppJWTDataAndReturnInfo() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponseJWT(
        "POST",
        "/stores/11e83951-b3ca-cc2c-94ae-07a654762b8c/app_jwts",
        jwt,
        200,
        StoreFakeRR.createStoreAppJWTFakeResponse,
        StoreFakeRR.createStoreAppJWTFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.JWT);

    final Date parsedDate = dateParser.parseDateTime("2018-04-10T12:30:33.284708+09:00").toDate();

    StoreApplicationJWT appJWT =
        univapay
            .createStoreAppJWT(new StoreId("11e83951-b3ca-cc2c-94ae-07a654762b8c"))
            .withMode(ProcessingMode.TEST)
            .withDomains(Collections.singletonList(Domain.ANY))
            .build()
            .dispatch();

    assertEquals(appJWT.getMerchantId().toString(), "11e83c6c-14db-30de-b9a5-17a790ae22a2");
    assertEquals(appJWT.getStoreId().toString(), "11e83c6c-16a4-e7a2-b9a5-cfab31f2d2e5");
    assertEquals(appJWT.getCreatorId().toString(), "11e83c6c-14db-30de-b9a5-17a790ae22a2");
    assertEquals(appJWT.getId().toString(), "11e83c6f-8587-4002-b28f-abc9bcd96cd3");
    assertEquals(appJWT.getSecret(), "FWCnurxE1dOKUZ4oAuTz");
    assertEquals(
        appJWT.getJwt(),
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcHBfdG9rZW4iLCJpYXQiOjE1MjMzMzEwMzEsIm1lcmNoYW50X2lkIjoiMTFlODNjNmMtMTRkYi0zMGRlLWI5YTUtMTdhNzkwYWUyMmEyIiwic3RvcmVfaWQiOiIxMWU4M2M2Yy0xNmE0LWU3YTItYjlhNS1jZmFiMzFmMmQyZTUiLCJkb21haW5zIjpbIioiXSwibW9kZSI6InRlc3QiLCJjcmVhdG9yX2lkIjoiMTFlODNjNmMtMTRkYi0zMGRlLWI5YTUtMTdhNzkwYWUyMmEyIiwidmVyc2lvbiI6MSwianRpIjoiMTFlODNjNmYtODU4Ny00MDAyLWIyOGYtYWJjOWJjZDk2Y2QzIn0.mXXUWcbmBuIe3xCQSEjzMd7kTwQRsOW05yd9mE6gbwo");
    assertEquals(appJWT.getCreatedOn(), parsedDate);
  }
}
