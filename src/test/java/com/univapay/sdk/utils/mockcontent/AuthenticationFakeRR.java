package com.univapay.sdk.utils.mockcontent;

public class AuthenticationFakeRR {
  public static String getLoginTokenFakeRequest =
      "{\"password\":\"mruserspassword\",\"email\":\"mruser@internet.com\"}";

  public static String getLogintokenFakeResponse =
      "{\n"
          + "\"jwt\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb2dpbl90b2tlbiIsImV4cCI6MTUyMDQyMjU3MywiaWF0IjoxNTIwNDE4OTczLCJqdGkiOiIxMWU4MjFmMy01YmIxLTBmODYtOTcxZi0wMWRkOGYwOTFhN2QiLCJtZXJjaGFudF9pZCI6IjExZTgyMWJlLTY4ZDQtNzMwOC1iYjYwLWI3MmRhYzA4MTA5OCIsIm5hbWUiOiJSb290IEFkbWluIiwiZW1haWwiOiJyb290X2FkbWluQHVuaXZhcGF5LmNvbSIsImxhbmciOiJqYSIsImlwX2FkZHJlc3MiOiIwOjA6MDowOjA6MDowOjEiLCJhdWQiOiIxMWU4MjFiZS02NWY4LWMxZmMtYjdmNi1iYjQ4NzFiYmExN2EiLCJyb2xlcyI6WyJhZG1pbiJdfQ.iaypSYq2kM1iCsAhZiRIe8WiEiAkPWqRNX6KhcrT8fY\",\n"
          + "\"merchant_id\": \"65774989-5cbb-4790-9cb9-17f127059d2d\"\n"
          + "}";
}
