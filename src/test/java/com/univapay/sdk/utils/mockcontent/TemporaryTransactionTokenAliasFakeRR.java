package com.univapay.sdk.utils.mockcontent;

import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.response.transactiontoken.TokenAliasKey;
import com.univapay.sdk.types.TransactionTokenType;

public class TemporaryTransactionTokenAliasFakeRR {

  public static String createAliasRequest(TransactionTokenId transactionTokenId) {
    return "{  \n"
        + "   \"transaction_token_id\":\""
        + transactionTokenId.toString()
        + "\"\n"
        + "}";
  }

  public static String createAliasResponse(TokenAliasKey aliasKey) {
    return "{\n"
        + "       \"key\": \""
        + aliasKey
        + "\",\n"
        + "       \"valid_until\": \"2018-10-02T15:06:51.879097Z\"\n"
        + "}";
  }

  public static String createAliasWithMetadataRequest(TransactionTokenId transactionTokenId) {
    return "{  \n"
        + "   \"transaction_token_id\":\""
        + transactionTokenId.toString()
        + "\",\n"
        + "   \"metadata\":{  \n"
        + "      \"string_value\":\"hola\",\n"
        + "      \"biginteger_value\":\"989223112\",\n"
        + "      \"bigdecimal_value\":\"1234.7981723987\",\n"
        + "      \"boolean_value\":\"true\",\n"
        + "      \"float_value\":\"3.141592\"\n"
        + "   }\n"
        + "}";
  }

  public static String createAliasWithMetadataResponse(TokenAliasKey aliasKey) {
    return "{\n"
        + "\"key\":\""
        + aliasKey
        + "\",\n"
        + "\"valid_until\": \"2018-10-02T15:06:51.879097Z\"\n"
        + "}";
  }

  public static String createAliasWithAllOptionsRequest(
      TransactionTokenId transactionTokenId, String validUntil) {
    return "{  \n"
        + "   \"transaction_token_id\":\""
        + transactionTokenId.toString()
        + "\",\n"
        + "   \"amount\":10000,\n"
        + "   \"currency\":\"jpy\",\n"
        + "   \"valid_until\":\""
        + validUntil
        + "\",\n"
        + "   \"metadata\":{  \n"
        + "      \"string_value\":\"hola\",\n"
        + "      \"biginteger_value\":\"989223112\",\n"
        + "      \"bigdecimal_value\":\"1234.7981723987\",\n"
        + "      \"boolean_value\":\"true\",\n"
        + "      \"float_value\":\"3.141592\"\n"
        + "   }\n"
        + "}";
  }

  public static String createAliasWithAllOptionsResponse(
      TokenAliasKey aliasKey, String validUntil) {
    return "{\n"
        + "   \"key\": \""
        + aliasKey
        + "\",\n"
        + "   \"valid_until\":\""
        + validUntil
        + "\"\n"
        + "}";
  }

  public static String getTokenAliasResponse(boolean active) {
    return getTokenAliasResponse(active, TransactionTokenType.RECURRING);
  }

  public static String getTokenAliasResponse(boolean active, TransactionTokenType tokenType) {
    return "{\n"
        + "    \"id\": \"11e8d66c-0b26-aace-ae22-7b4a85c3ff85\",\n"
        + "   \"store_id\": \"425e88b7-b588-4247-80ee-0ea0caff1190\",\n"
        + "    \"email\": \"blah@blah.com\",\n"
        + "    \"payment_type\": \"card\",\n"
        + "    \"active\": "
        + active
        + ",\n"
        + "    \"mode\": \"test\",\n"
        + "    \"type\": \""
        + tokenType.name().toLowerCase()
        + "\",\n"
        + "    \"metadata\": {\n"
        + "      \"cod\": \"15984632\",\n"
        + "      \"prod\": \"electronics\"\n"
        + "    },\n"
        + "   \"created_on\": \"2018-10-02T15:01:51.890204Z\",\n"
        + "    \"last_used_on\": null,\n"
        + "    \"amount\": 5000,\n"
        + "    \"currency\": \"JPY\"\n"
        + "}";
  }

  public static String getTokenAliasResponse =
      "{\n"
          + "    \"id\": \"11e8d66c-0b26-aace-ae22-7b4a85c3ff85\",\n"
          + "    \"store_id\": \"11e8d66b-5985-7200-8eee-9f2382a3211b\",\n"
          + "    \"email\": \"blah@blah.com\",\n"
          + "    \"payment_type\": \"card\",\n"
          + "    \"active\": true,\n"
          + "    \"mode\": \"test\",\n"
          + "    \"type\": \"recurring\",\n"
          + "    \"metadata\": {  \n"
          + "       \"goodbye\":\"adios\"\n"
          + "    },\n"
          + "    \"created_on\": \"2018-10-02T15:09:51.879097Z\",\n"
          + "    \"last_used_on\": null,\n"
          + "    \"alias_metadata\": {  \n"
          + "       \"string_value\":\"hola\",\n"
          + "       \"biginteger_value\":\"989223112\",\n"
          + "       \"bigdecimal_value\":\"1234.7981723987\",\n"
          + "       \"boolean_value\":\"true\",\n"
          + "       \"float_value\":\"3.141592\"\n"
          + "    },\n"
          + "    \"amount\": 5000,\n"
          + "    \"currency\": \"JPY\"\n"
          + "}";

  public static String getTokenAliasWithNoMoneyResponse =
      "{\n"
          + "    \"id\": \"11e8d66c-0b26-aace-ae22-7b4a85c3ff85\",\n"
          + "    \"store_id\": \"11e8d66b-5985-7200-8eee-9f2382a3211b\",\n"
          + "    \"email\": \"blah@blah.com\",\n"
          + "    \"payment_type\": \"card\",\n"
          + "    \"active\": true,\n"
          + "    \"mode\": \"test\",\n"
          + "    \"type\": \"recurring\",\n"
          + "    \"metadata\": {  \n"
          + "       \"string_value\":\"hola\",\n"
          + "       \"biginteger_value\":\"989223112\",\n"
          + "       \"bigdecimal_value\":\"1234.7981723987\",\n"
          + "       \"boolean_value\":\"true\",\n"
          + "       \"float_value\":\"3.141592\"\n"
          + "    },\n"
          + "    \"created_on\": \"2018-10-02T15:09:51.879097Z\",\n"
          + "    \"last_used_on\": null\n"
          + "}";
}
