package com.univapay.sdk.utils.mockcontent;

public class ErrorsFakeRR {
  public static String invalidFormatFakeRequest =
      "{\n" + "  \"email\": \"test@univapay.com\",\n" + "  \"password\": \"c\"\n" + "}";

  public static String invalidFormatFakeResponse =
      "{\n"
          + "  \"status\": \"error\",\n"
          + "  \"code\": \"VALIDATION_ERROR\",\n"
          + "  \"errors\": [\n"
          + "    {\n"
          + "      \"field\": \"password\",\n"
          + "      \"reason\": \"INVALID_FORMAT_LENGTH\"\n"
          + "    }\n"
          + "  ]\n"
          + "}";

  public static String descriptorNotSupportedError =
      "{\n"
          + "  \"status\": \"error\",\n"
          + "  \"code\": \"VALIDATION_ERROR\",\n"
          + "  \"errors\": [\n"
          + "    {\n"
          + "      \"field\": \"descriptor\",\n"
          + "      \"reason\": \"NOT_SUPPORTED_BY_PROCESSOR\"\n"
          + "    }\n"
          + "  ]\n"
          + "}";
}
