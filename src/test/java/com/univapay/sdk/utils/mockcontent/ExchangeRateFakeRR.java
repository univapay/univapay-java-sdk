package com.univapay.sdk.utils.mockcontent;

public class ExchangeRateFakeRR {

  public static String convertFakeRequest =
      "{\n" + "\t\"currency\":\"JPY\",\n" + "\t\"amount\": 1000,\n" + "\t\"to\": \"USD\"\n" + "}";

  public static String convertFakeResponse =
      "{\n" + "    \"amount\": 1230,\n" + "    \"currency\": \"USD\"\n" + "}";
}
