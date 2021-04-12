package com.univapay.sdk.utils;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.common.auth.AppJWTStrategy;
import com.univapay.sdk.models.common.auth.AppTokenStrategy;
import com.univapay.sdk.models.common.auth.LoginJWTStrategy;
import com.univapay.sdk.settings.AbstractSDKSettings;
import com.univapay.sdk.types.AuthType;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import org.junit.ClassRule;

public class GenericTest {
  protected static String appToken = "ZGlHubxJvNuoyhGRtNsn";
  protected static String secret = "CegjVnqiga68l2tRlVp9";
  protected static AppTokenStrategy appTokenStrategyWithSecret =
      new AppTokenStrategy(appToken, secret);
  protected static String jwt =
      "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9."
          + "eyJzdWIiOiJsb2dpbl90b2tlbiIsImV4cCI6MTUyMDQyMjU3MywiaWF0IjoxNT"
          + "IwNDE4OTczLCJqdGkiOiIxMWU4MjFmMy01YmIxLTBmODYtOTcxZi0wMWRkOGYw"
          + "OTFhN2QiLCJtZXJjaGFudF9pZCI6IjExZTgyMWJlLTY4ZDQtNzMwOC1iYjYwLW"
          + "I3MmRhYzA4MTA5OCIsIm5hbWUiOiJSb290IEFkbWluIiwiZW1haWwiOiJyb290"
          + "X2FkbWluQHVuaXZhcGF5LmNvbSIsImxhbmciOiJqYSIsImlwX2FkZHJlc3MiOi"
          + "IwOjA6MDowOjA6MDowOjEiLCJhdWQiOiIxMWU4MjFiZS02NWY4LWMxZmMtYjdm"
          + "Ni1iYjQ4NzFiYmExN2EiLCJyb2xlcyI6WyJhZG1pbiJdfQ.iaypSYq2kM1iCsA"
          + "hZiRIe8WiEiAkPWqRNX6KhcrT8fY";
  protected static String appJWT =
      "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9."
          + "eyJzdWIiOiJhcHBfdG9rZW4iLCJpYXQiOjE1MjMzMzE1ODMsIm1lcmNoYW50X2"
          + "lkIjoiMTFlODNjNmMtMTRkYi0zMGRlLWI5YTUtMTdhNzkwYWUyMmEyIiwicm9s"
          + "ZXMiOlsibWVyY2hhbnQiXSwiY3JlYXRvcl9pZCI6IjExZTgzYzZjLTE0ZGItMz"
          + "BkZS1iOWE1LTE3YTc5MGFlMjJhMiIsInZlcnNpb24iOjEsImp0aSI6IjExZTgz"
          + "YzcwLWNlNjctZTg1OC1iMjhmLTFkYzBjMjg5MmI3NyJ9.6VKtNDfvFuiuaW6wR"
          + "tPThRCA3w4v4AQJeKUHL0Ly8ZU";
  protected static String appJWTSecret = "m4YkAf3B4A0h6JDeouN1";
  protected static AppJWTStrategy appJWTStrategyWithSecret =
      new AppJWTStrategy(appJWT, appJWTSecret);
  protected static LoginJWTStrategy jwtCredentials = new LoginJWTStrategy(jwt);
  protected static int PORT = 8020;
  protected static final String TEST_ENDPOINT = "http://localhost:" + PORT;
  protected static final AbstractSDKSettings testSettings =
      new UnivapayDebugSettings().withEndpoint(TEST_ENDPOINT).withRequestsLogging(true);

  protected String urlEncode(String s) throws UnsupportedEncodingException {
    return URLEncoder.encode(s, StandardCharsets.UTF_8.name());
  }

  protected static UnivapaySDK createTestInstance(AuthType authType) {

    UnivapaySDK sdk;

    switch (authType) {
      case APP_TOKEN:
        {
          sdk = UnivapaySDK.create(appTokenStrategyWithSecret, testSettings);
        }
        break;
      case JWT:
        {
          sdk = UnivapaySDK.create(jwtCredentials, testSettings);
        }
        break;
      default:
        sdk = UnivapaySDK.create(testSettings);
    }

    return sdk;
  }

  @ClassRule public static WireMockRule wireMockRule = new WireMockRule(PORT);
  private final CountDownLatch latch = new CountDownLatch(1);

  protected OffsetDateTime parseDate(String dateStr) {
    return OffsetDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
  }

  protected String formatDate(OffsetDateTime dateTime) {
    return DateTimeFormatter.ISO_DATE_TIME.format(dateTime);
  }

  protected void notifyCall() {
    latch.countDown();
  }

  protected void waitCall() throws InterruptedException {
    assertTrue(latch.await(60, SECONDS));
  }

  public class ExpectSuccessCallback<T> implements UnivapayCallback<T> {

    @Override
    public void getResponse(T response) {

      notifyCall();
    }

    @Override
    public void getFailure(Throwable error) {
      System.out.println(error);
      fail();
      notifyCall();
    }
  }
}
