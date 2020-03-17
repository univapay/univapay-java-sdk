package com.univapay.sdk.utils.mockcontent;

import java.math.BigInteger;

public class ChargesFakeRR {
  public static String listAllStoreChargesFakeResponse =
      "{\n"
          + "  \"items\": [\n"
          + "    {\n"
          + "      \"id\": \"e544b386-b13c-42c9-ab77-b36f95c99eaa\",\n"
          + "      \"store_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "      \"requested_amount\": 123,\n"
          + "      \"requested_currency\": \"USD\",\n"
          + "      \"charged_amount\": null,\n"
          + "      \"charged_currency\": null,\n"
          + "      \"only_direct_currency\": true,\n"
          + "      \"descriptor\": \"test descriptor\",\n"
          + "      \"status\": \"successful\",\n"
          + "      \"error\": null,\n"
          + "      \"metadata\": {\n"
          +
          //                    "          \"null\": null,\n" +
          "          \"array\": [\"string\", \"12.3\"],\n"
          + "          \"float\": 10.3,\n"
          + "          \"number\": 10,\n"
          + "          \"string\": \"string\"\n"
          + "      },\n"
          + "      \"test_mode\": true,\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"id\": \"425e88b7-b588-4247-80ee-0ea0caff1190\",\n"
          + "      \"store_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "      \"requested_amount\": 1000,\n"
          + "      \"requested_currency\": \"JPY\",\n"
          + "      \"charged_amount\": 1000,\n"
          + "      \"charged_currency\": \"JPY\",\n"
          + "      \"only_direct_currency\": false,\n"
          + "      \"descriptor\": null,\n"
          + "      \"status\": \"successful\",\n"
          + "      \"error\": null,\n"
          + "      \"metadata\": {\n"
          + "        \"Tag\": \"electronics\",\n"
          + "        \"ProdID\": 15984632\n"
          + "      },\n"
          + "      \"test_mode\": true,\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "    }\n"
          + "  ],\n"
          + "  \"has_more\": false\n"
          + "}";

  public static String getStoreChargeFakeResponse =
      "{\n"
          + "  \"id\": \"425e88b7-b588-4247-80ee-0ea0caff1190\",\n"
          + "  \"store_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "  \"requested_amount\": 1000,\n"
          + "  \"requested_currency\": \"JPY\",\n"
          + "  \"charged_amount\": 1000,\n"
          + "  \"charged_currency\": \"JPY\",\n"
          + "  \"only_direct_currency\": true,\n"
          + "  \"descriptor\": \"test descriptor\",\n"
          + "  \"status\": \"successful\",\n"
          + "  \"error\": null,\n"
          + "  \"metadata\": {\n"
          + "    \"Tag\": \"electronics\",\n"
          + "    \"ProdID\": \"15984632\"\n"
          + "  },\n"
          + "  \"test_mode\": true,\n"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "}";

  public static String getStoreChargeMetadataFakeResponse =
      "{\n"
          + "  \"id\": \"425e88b7-b588-4247-80ee-0ea0caff1190\",\n"
          + "  \"store_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "  \"requested_amount\": 1000,\n"
          + "  \"requested_currency\": \"JPY\",\n"
          + "  \"charged_amount\": 1000,\n"
          + "  \"charged_currency\": \"JPY\",\n"
          + "  \"status\": \"successful\",\n"
          + "  \"error\": null,\n"
          + "  \"metadata\": {\n"
          + "      \"array\": [\"string\", \"12.3\"],\n"
          + "      \"float\": 10.3,\n"
          + "      \"number\": 10,\n"
          + "      \"string\": \"string value\"\n"
          + "  },\n"
          + "  \"test_mode\": true,\n"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "}";

  public static String createStoreChargeFakeRequest =
      "{\n"
          + "  \"transaction_token_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "  \"amount\": 1000,\n"
          + "  \"currency\": \"JPY\",\n"
          + "  \"capture\": true,\n"
          + "  \"metadata\": {\n"
          + "    \"cod\": \"15984632\",\n"
          + "    \"prod\": \"electronics\"\n"
          + "  }\n"
          + "}";

  public static String createStoreChargeNoMetadataFakeRequest =
      "{\n"
          + "  \"transaction_token_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "  \"amount\": 1000,\n"
          + "  \"capture\": true,\n"
          + "  \"only_direct_currency\": true,\n"
          + "  \"descriptor\": \"test descriptor\",\n"
          + "  \"currency\": \"JPY\""
          + "}";

  public static String createStoreChargeMetadataFakeRequest =
      "{\n"
          + "  \"transaction_token_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "  \"amount\": 1000,\n"
          + "  \"currency\": \"JPY\",\n"
          + "  \"capture\": true,\n"
          + "  \"metadata\": {\n"
          + "      \"array\": \"[string, 12.3]\","
          + "      \"float\": \"10.3\",\n"
          + "      \"number\": \"10\",\n"
          + "      \"string\": \"string\"\n"
          + "  }\n"
          + "}";

  public static String createChargeFakeRequestMetadataFloat =
      "{\n"
          + "  \"transaction_token_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "  \"amount\": 1000,\n"
          + "  \"currency\": \"JPY\",\n"
          + "  \"capture\": true,\n"
          + "  \"metadata\": {\n"
          + "      \"float\": \"10.3\"\n"
          + "  }\n"
          + "}";

  public static String createFullChargeFakeRequest(BigInteger amount, String captureAt) {
    return "{\n"
        + "  \"transaction_token_id\": \"11e8d66c-0b26-aace-ae22-7b4a85c3ff85\",\n"
        + "  \"amount\": "
        + amount
        + ",\n"
        + "  \"currency\": \"JPY\",\n"
        + "  \"capture\": true,\n"
        + "  \"only_direct_currency\": true,\n"
        + "  \"descriptor\": \"test descriptor\",\n"
        + "  \"capture_at\": \""
        + captureAt
        + "\"\n,"
        + "  \"metadata\": {\n"
        + "    \"cod\": \"15984632\",\n"
        + "    \"prod\": \"electronics\"\n"
        + "  }\n"
        + "}";
  }

  public static String createFullChargeWithComplexMetadataFakeRequest(
      String captureAt, BigInteger amount, boolean useDescriptor) {

    String descriptor = "";
    String captureAtField = "";
    if (useDescriptor) {
      descriptor = "  \"descriptor\": \"test descriptor\",\n";
    }

    if (captureAt != null) {
      captureAtField = "  \"capture_at\": \"" + captureAt + "\"\n,";
    }

    return "{\n"
        + "  \"transaction_token_id\": \"11e8d66c-0b26-aace-ae22-7b4a85c3ff85\",\n"
        + "  \"amount\": "
        + amount
        + ",\n"
        + "  \"currency\": \"JPY\",\n"
        + "  \"capture\": true,\n"
        + "  \"only_direct_currency\": true,\n"
        + descriptor
        + captureAtField
        + "   \"metadata\":{  \n"
        + "      \"string_value\":\"hola\",\n"
        + "      \"biginteger_value\":\"989223112\",\n"
        + "      \"bigdecimal_value\":\"1234.7981723987\",\n"
        + "      \"boolean_value\":\"true\",\n"
        + "      \"float_value\":\"3.141592\"\n"
        + "   }\n"
        + "}";
  }

  public static String createFullChargeWithComplexMetadataFakeRequest(String captureAt) {
    return createFullChargeWithComplexMetadataFakeRequest(
        captureAt, BigInteger.valueOf(1000), true);
  }

  public static String createStoreChargeFakeResponse =
      "{\n"
          + "  \"id\": \"425e88b7-b588-4247-80ee-0ea0caff1190\",\n"
          + "  \"store_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "  \"requested_amount\": 1000,\n"
          + "  \"requested_currency\": \"JPY\",\n"
          + "  \"charged_amount\": null,\n"
          + "  \"charged_currency\": null,\n"
          + "  \"status\": \"pending\",\n"
          + "  \"error\": null,\n"
          + "  \"metadata\": {\n"
          + "    \"cod\": 15984632,\n"
          + "    \"prod\": \"electronics\"\n"
          + "  },\n"
          + "  \"test_mode\": true,\n"
          + "  \"created_on\": null\n"
          + "}";

  public static String createStoreChargeNoMetadataFakeResponse =
      "{\n"
          + "  \"id\": \"425e88b7-b588-4247-80ee-0ea0caff1190\",\n"
          + "  \"store_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "  \"requested_amount\": 1000,\n"
          + "  \"requested_currency\": \"JPY\",\n"
          + "  \"charged_amount\": null,\n"
          + "  \"charged_currency\": null,\n"
          + "  \"only_direct_currency\": true,\n"
          + "  \"descriptor\": \"test descriptor\",\n"
          + "  \"status\": \"pending\",\n"
          + "  \"error\": null,\n"
          + "  \"test_mode\": true,\n"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "}";

  public static String createStoreChargeNoMetadataWithoutDescriptorFakeResponse =
      "{\n"
          + "  \"id\": \"425e88b7-b588-4247-80ee-0ea0caff1190\",\n"
          + "  \"store_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "  \"requested_amount\": 1000,\n"
          + "  \"requested_currency\": \"JPY\",\n"
          + "  \"charged_amount\": null,\n"
          + "  \"charged_currency\": null,\n"
          + "  \"only_direct_currency\": true,\n"
          + "  \"status\": \"pending\",\n"
          + "  \"error\": null,\n"
          + "  \"test_mode\": true,\n"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "}";

  public static String createStoreChargeMetadataFakeResponse =
      "{\n"
          + "  \"id\": \"425e88b7-b588-4247-80ee-0ea0caff1190\",\n"
          + "  \"store_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "  \"requested_amount\": 1000,\n"
          + "  \"requested_currency\": \"JPY\",\n"
          + "  \"charged_amount\": null,\n"
          + "  \"charged_currency\": null,\n"
          + "  \"status\": \"pending\",\n"
          + "  \"error\": null,\n"
          + "  \"metadata\": {\n"
          + "      \"array\": \"[string, 12.3]\",\n"
          + "      \"float\": 10.3,\n"
          + "      \"number\": 10,\n"
          + "      \"string\": \"string\"\n"
          + "  },\n"
          + "  \"test_mode\": true,\n"
          + "  \"created_on\": null\n"
          + "}";

  public static String createStoreChargeMetadataWithoutDescriptorFakeResponse =
      "{\n"
          + "  \"id\": \"425e88b7-b588-4247-80ee-0ea0caff1190\",\n"
          + "  \"store_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "  \"requested_amount\": 1000,\n"
          + "  \"requested_currency\": \"JPY\",\n"
          + "  \"charged_amount\": null,\n"
          + "  \"charged_currency\": null,\n"
          + "  \"status\": \"pending\",\n"
          + "  \"error\": null,\n"
          + "  \"metadata\": {\n"
          + "      \"array\": \"[string, 12.3]\",\n"
          + "      \"float\": 10.3,\n"
          + "      \"number\": 10,\n"
          + "      \"string\": \"string\"\n"
          + "  },\n"
          + "  \"test_mode\": true,\n"
          + "  \"created_on\": null\n"
          + "}";

  public static String chargeFakeResponseMetadataFloat =
      "{\n"
          + "  \"id\": \"425e88b7-b588-4247-80ee-0ea0caff1190\",\n"
          + "  \"store_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "  \"requested_amount\": 1000,\n"
          + "  \"requested_currency\": \"JPY\",\n"
          + "  \"charged_amount\": null,\n"
          + "  \"charged_currency\": null,\n"
          + "  \"status\": \"pending\",\n"
          + "  \"error\": null,\n"
          + "  \"metadata\": {\n"
          + "      \"float\": \"10.3\"\n"
          + "  },\n"
          + "  \"test_mode\": true,\n"
          + "  \"created_on\": null\n"
          + "}";

  public static String createFullChargeFakeResponse(
      String captureAt, String status, Boolean useDescriptor) {

    String descriptor = "";

    if (useDescriptor) {
      descriptor = "  \"descriptor\": \"test descriptor\",\n";
    }

    return "{\n"
        + "    \"id\": \"425e88b7-b588-4247-80ee-0ea0caff1190\",\n"
        + "    \"store_id\":\"425e88b7-b588-4247-80ee-0ea0caff1190\",\n"
        + "    \"transaction_token_id\": \"11e8d66c-0b26-aace-ae22-7b4a85c3ff85\",\n"
        + "    \"transaction_token_type\": \"recurring\",\n"
        + "    \"subscription_id\": null,\n"
        + "    \"requested_amount\": 1000,\n"
        + "    \"requested_currency\": \"JPY\",\n"
        + "    \"requested_amount_formatted\": 1000,\n"
        + "    \"charged_amount\": 1000,\n"
        + "    \"charged_currency\": \"JPY\",\n"
        + "    \"charged_amount_formatted\": 1000,\n"
        + "    \"only_direct_currency\": true,\n"
        + "    \"capture_at\": \""
        + captureAt
        + "\",\n"
        + descriptor
        + "    \"status\": \""
        + status
        + "\",\n"
        + "    \"error\": null,\n"
        + "    \"metadata\": {\n"
        + "      \"cod\": \"15984632\",\n"
        + "      \"prod\": \"electronics\"\n"
        + "    },\n"
        + "    \"mode\": \"test\",\n"
        + "    \"created_on\": \"2018-10-22T05:46:11.507166Z\",\n"
        + "    \"platform_id\": \"11e73ea1-d72c-8ce2-996d-4bb6671eb667\",\n"
        + "    \"merchant_id\": \"11e8833d-da6a-d576-a0be-c3e737779327\",\n"
        + "    \"ledger_id\": null,\n"
        + "    \"updated_on\": \"2018-10-22T05:46:11.606515Z\""
        + "}";
  }

  public static String createFullChargeFakeResponse(String captureAt) {
    return createFullChargeFakeResponse(captureAt, "pending", true);
  }

  public static String createFullChargeWithComplexMetadataFakeResponse(
      String captureAt, boolean useDescriptor) {
    String descriptorField = "";
    String captureAtField = "";
    if (useDescriptor) {
      descriptorField = "    \"descriptor\": \"test descriptor\",\n";
    }

    if (captureAt != null) {
      captureAtField = "    \"capture_at\": \"" + captureAt + "\",\n";
    }

    return "{\n"
        + "    \"id\": \"425e88b7-b588-4247-80ee-0ea0caff1190\",\n"
        + "    \"store_id\":\"425e88b7-b588-4247-80ee-0ea0caff1190\",\n"
        + "    \"transaction_token_id\": \"11e8d66c-0b26-aace-ae22-7b4a85c3ff85\",\n"
        + "    \"transaction_token_type\": \"recurring\",\n"
        + "    \"subscription_id\": null,\n"
        + "    \"requested_amount\": 1000,\n"
        + "    \"requested_currency\": \"JPY\",\n"
        + "    \"requested_amount_formatted\": 1000,\n"
        + "    \"charged_amount\": 1000,\n"
        + "    \"charged_currency\": \"JPY\",\n"
        + "    \"charged_amount_formatted\": 1000,\n"
        + "    \"only_direct_currency\": true,\n"
        + captureAtField
        + descriptorField
        + "    \"status\": \"pending\",\n"
        + "    \"error\": null,\n"
        + "    \"metadata\":{  \n"
        + "       \"string_value\":\"hola\",\n"
        + "       \"biginteger_value\":\"989223112\",\n"
        + "       \"bigdecimal_value\":\"1234.7981723987\",\n"
        + "       \"boolean_value\":\"true\",\n"
        + "       \"float_value\":\"3.141592\"\n"
        + "    },\n"
        + "    \"mode\": \"test\",\n"
        + "    \"created_on\": \"2018-10-22T05:46:11.507166Z\",\n"
        + "    \"platform_id\": \"11e73ea1-d72c-8ce2-996d-4bb6671eb667\",\n"
        + "    \"merchant_id\": \"11e8833d-da6a-d576-a0be-c3e737779327\",\n"
        + "    \"ledger_id\": null,\n"
        + "    \"updated_on\": \"2018-10-22T05:46:11.606515Z\""
        + "}";
  }

  public static String updateStoreChargeFakeRequest =
      "{\n" + "   \"metadata\":{\n" + "       \"hoge\":\"あああ\"\n" + "   }" + "}";

  public static String updateChargeFakeRequestMetadata =
      "{\n"
          + "  \"metadata\": {\n"
          + "      \"array\": \"[string, 12.3]\",\n"
          + "      \"float\": \"10.3\",\n"
          + "      \"number\": \"10\",\n"
          + "      \"string\": \"string\"\n"
          + "  }\n"
          + "}";

  public static String updateChargeFakeRequestMetadataFloat =
      "{\n" + "  \"metadata\": {\n" + "      \"float\": \"10.3\"\n" + "  }\n" + "}";

  public static String updateStoreChargeFakeResponse =
      "{\n"
          + "\"id\": \"11e792d6-6e0c-bf1e-bede-0be6e2f0ac23\",\n"
          + "\"store_id\": \"11e786da-4714-5028-8280-bb9bc7cf54e9\",\n"
          + "\"transaction_token_id\": \"11e792d6-6b6a-c44a-9eb0-23f3053db978\",\n"
          + "\"subscription_id\": \"11e792d6-6e02-3756-9eb2-bb14816a56bc\",\n"
          + "\"requested_amount\": 132,\n"
          + "\"requested_currency\": \"USD\",\n"
          + "\"requested_amount_formatted\": 1.32,\n"
          + "\"charged_amount\": 125,\n"
          + "\"charged_currency\": \"EUR\",\n"
          + "\"charged_amount_formatted\": 1.25,\n"
          + "\"status\": \"successful\",\n"
          + "\"metadata\": {\n"
          + "\"hoge\": \"ううう\"\n"
          + "},\n"
          + "\"mode\": \"test\",\n"
          + "\"created_on\": \"2017-09-06T07:38:52.000000+09:00\"\n"
          + "}";

  public static String listAllRefundsFakeResponse =
      "{\n"
          + "\"items\": [\n"
          + "    {\n"
          + "        \"id\": \"04ea4e3e-3f19-43d3-8593-fed3aba06770\",\n"
          + "        \"store_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "        \"charge_id\": \"6791acdd-d901-49b8-a46f-24a7a39e894f\",\n"
          + "        \"status\": \"failed\",\n"
          + "        \"amount\": 15,\n"
          + "        \"currency\": \"JPY\",\n"
          + "        \"amount_formatted\":15,\n"
          + "        \"reason\": \"customer_request\",\n"
          + "        \"message\": \"10% off\",\n"
          + "        \"error\": {\n"
          + "            \"code\": 500,\n"
          + "            \"message\": \"There was a processing error\",\n"
          + "            \"detail\": \"No gateway available to process the request\"\n"
          + "        },\n"
          + "        \"metadata\": {\n"
          + "            \"cod\": \"504547895\",\n"
          + "            \"prod\": \"ticket flight\"\n"
          + "        },\n"
          + "        \"test_mode\": true,\n"
          + "        \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "    },\n"
          + "    {\n"
          + "        \"id\": \"45f1a7ac-903e-4c46-a959-5564f4fdc5ca\",\n"
          + "        \"store_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "        \"charge_id\": \"6791acdd-d901-49b8-a46f-24a7a39e894f\",\n"
          + "        \"status\": \"successful\",\n"
          + "        \"amount\": 15,\n"
          + "        \"currency\": \"JPY\",\n"
          + "        \"amount_formatted\":15,\n"
          + "        \"reason\": \"customer_request\",\n"
          + "        \"message\": \"10% off\",\n"
          + "        \"error\": null,\n"
          + "        \"metadata\": {\n"
          + "            \"cod\": \"504547895\",\n"
          + "            \"prod\": \"ticket flight\"\n"
          + "        },\n"
          + "        \"test_mode\": true,\n"
          + "        \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "    }\n"
          + "],\n"
          + "\"has_more\": false\n"
          + "}";

  public static String getRefundFakeResponse =
      "{\n"
          + "\"id\": \"45f1a7ac-903e-4c46-a959-5564f4fdc5ca\",\n"
          + "\"charge_id\": \"6791acdd-d901-49b8-a46f-24a7a39e894f\",\n"
          + "\"store_id\":\"653ef5a3-73f2-408a-bac5-7058835f7700\","
          + "\"status\": \"successful\",\n"
          + "\"amount\": 15,\n"
          + "\"currency\": \"JPY\",\n"
          + "\"amount_formatted\":15,"
          + "\"reason\": \"customer_request\",\n"
          + "\"message\": \"10% off\",\n"
          + "\"error\": null,\n"
          + "\"metadata\": {\n"
          + "    \"cod\": \"504547895\",\n"
          + "    \"prod\": \"ticket flight\"\n"
          + "},\n"
          + "\"mode\": \"test\",\n"
          + "\"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "}";

  public static String getCancelFakeResponse =
      "{\n"
          + "\"id\": \"11e8c2de-5ecc-cd4e-858a-d3fc8224041b\",\n"
          + "\"charge_id\": \"11e8c2de-5672-f290-b382-cb0ee8b20db4\",\n"
          + "\"store_id\": \"11e73ea1-d73c-3138-9a3b-8f78567066f6\",\n"
          + "\"status\": \"successful\",\n"
          + "\"error\": null,\n"
          + "\"metadata\": {\n"
          + "    \"cod\": \"504547895\",\n"
          + "    \"prod\": \"ticket flight\"\n"
          + "},\n"
          + "\"mode\": \"test\",\n"
          + "\"created_on\": \"2018-09-28T05:21:36.380557Z\"\n"
          + "}";

  public static String createRefundFakeRequest =
      "{\n"
          + "  \"amount\": 15,\n"
          + "  \"currency\": \"JPY\",\n"
          + "  \"reason\": \"customer_request\",\n"
          + "  \"message\": \"10% off\",\n"
          + "  \"metadata\": {\n"
          + "    \"cod\": \"504547895\",\n"
          + "    \"prod\": \"ticket flight\"\n"
          + "  }\n"
          + "}";

  public static String createRefundWithoutMetadataFakeRequest =
      "{\n"
          + "  \"amount\": 15,\n"
          + "  \"currency\": \"JPY\",\n"
          + "  \"reason\": \"customer_request\",\n"
          + "  \"message\": \"10% off\"\n"
          + "}";

  public static String createRefundMetadataFloatFakeRequest =
      "{\n"
          + "  \"amount\": 15,\n"
          + "  \"currency\": \"JPY\",\n"
          + "  \"reason\": \"customer_request\",\n"
          + "  \"metadata\": {\n"
          + "      \"float\": \"10.3\"\n"
          + "  }\n"
          + "}";

  public static String createRefundFakeResponse =
      "{\n"
          + "  \"id\": \"677471f5-2781-458b-9797-2a3548dccc5a\",\n"
          + "  \"charge_id\": \"6791acdd-d901-49b8-a46f-24a7a39e894f\",\n"
          + "  \"store_id\":\"653ef5a3-73f2-408a-bac5-7058835f7700\","
          + "  \"status\": \"pending\",\n"
          + "  \"amount\": 15,\n"
          + "  \"currency\": \"JPY\",\n"
          + "  \"amount_formatted\":15,"
          + "  \"reason\": \"customer_request\",\n"
          + "  \"message\": \"10% off\",\n"
          + "  \"error\": null,\n"
          + "  \"metadata\": {\n"
          + "    \"cod\": \"504547895\",\n"
          + "    \"prod\": \"ticket flight\"\n"
          + "  },\n"
          + "  \"mode\": \"test\",\n"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "}";

  public static String createRefundWithoutMetadataFakeResponse =
      "{\n"
          + "  \"id\": \"677471f5-2781-458b-9797-2a3548dccc5a\",\n"
          + "  \"charge_id\": \"6791acdd-d901-49b8-a46f-24a7a39e894f\",\n"
          + "  \"status\": \"pending\",\n"
          + "  \"amount\": 15,\n"
          + "  \"currency\": \"JPY\",\n"
          + "  \"reason\": \"customer_request\",\n"
          + "  \"message\": \"10% off\",\n"
          + "  \"error\": null,\n"
          + "  \"mode\": \"test\",\n"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "}";

  public static String createRefundMetadataFloatFakeResponse =
      "{\n"
          + "  \"id\": \"677471f5-2781-458b-9797-2a3548dccc5a\",\n"
          + "  \"charge_id\": \"6791acdd-d901-49b8-a46f-24a7a39e894f\",\n"
          + "  \"status\": \"pending\",\n"
          + "  \"amount\": 15,\n"
          + "  \"currency\": \"JPY\",\n"
          + "  \"reason\": \"customer_request\",\n"
          + "  \"error\": null,\n"
          + "  \"metadata\": {\n"
          + "      \"float\": 10.3\n"
          + "  },\n"
          + "  \"mode\": \"test\",\n"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "}";

  public static String createSubscriptionFakeRequest =
      "{"
          + "  \"currency\":\"JPY\","
          + "  \"period\":\"monthly\","
          + "  \"transaction_token_id\":\"11e897ac-8cb5-36c2-aea6-0fe2b400c0b1\","
          + "  \"amount\":10000,"
          + "  \"initial_amount\":1000,"
          + "  \"schedule_settings\": {\n"
          + "      \"start_on\": \"2020-08-31\",\n"
          + "      \"zone_id\": \"America/Cancun\",\n"
          + "      \"preserve_end_of_month\": true\n"
          + "  },\n"
          + "  \"only_direct_currency\": true,\n"
          + "  \"descriptor\": \"test descriptor\",\n"
          + "  \"metadata\":{"
          + "    \"some_key\":\"some value\""
          + "  },\n"
          + "  \"first_charge_capture_after\": \"PT48H\",\n"
          + "  \"first_charge_authorization_only\": true"
          + "}";

  public static String createFixedCycleInstallmentsSubscriptionFakeRequest =
      "{\"amount\":12000,\"currency\":\"JPY\",\"period\":\"biweekly\",\"initial_amount\":1000,\"schedule_settings\":{\"start_on\":\"2018-08-31\", \"zone_id\":\"Asia/Tokyo\"},\"installment_plan\":{\"plan_type\":\"fixed_cycles\",\"fixed_cycles\":5}, \"metadata\":{\"service\":\"product payments\"},\"transaction_token_id\":\"11e89704-fa2f-ec0e-8f78-ab45170ecd0d\"}";

  public static String createFixedCycleAmountInstallmentsSubscriptionFakeRequest =
      "{\"amount\":12000,\"currency\":\"JPY\",\"period\":\"biweekly\",\"initial_amount\":1000,\"schedule_settings\":{\"start_on\":\"2018-08-31\", \"zone_id\" : \"Asia/Tokyo\"},\"installment_plan\":{\"plan_type\":\"fixed_cycle_amount\",\"fixed_cycle_amount\":5000}, \"metadata\":{\"service\":\"refrigerator\"},\"transaction_token_id\":\"11e89704-fa2f-ec0e-8f78-ab45170ecd0d\"}";

  public static String createRevolvingInstallmentsSubscriptionFakeRequest =
      "{   \"currency\":\"JPY\",\n" +
              "   \"amount\":1000,\n" +
              "   \"transaction_token_id\":\"7f5eecc8-3b38-4cec-86bb-644af74cb186\",\n" +
              "   \"period\":\"daily\",\n" +
              "   \"metadata\":{\n" +
              "      \"reason\":\"monthly magazine\"\n" +
              "   \n" +
              "},\n" +
              "   \"installment_plan\":{\n" +
              "      \"plan_type\":\"revolving\"\n" +
              "   \n" +
              "},\n" +
              "   \"schedule_settings\":{\n" +
              "\n" +
              "   \n" +
              "}\n" +
              "}";



  public static String createSubscriptionMetadataFakeRequest(boolean useDescriptor) {
    String descriptor = "";
    if (useDescriptor) {
      descriptor = "  \"descriptor\": \"test descriptor\",\n";
    }

    return "{"
        + "  \"currency\":\"JPY\","
        + "  \"period\":\"monthly\","
        + "  \"transaction_token_id\":\"11e897ac-8cb5-36c2-aea6-0fe2b400c0b1\","
        + "  \"amount\":10000,"
        + descriptor
        + "  \"initial_amount\":1000,"
        + "  \"schedule_settings\": {\n"
        + "      \"start_on\": \"2020-08-31\",\n"
        + "      \"zone_id\": \"America/Cancun\",\n"
        + "      \"preserve_end_of_month\": true\n"
        + "  },\n"
        + "  \"metadata\": {\n"
        + "      \"string_value\" : \"hola\",\n"
        + "      \"biginteger_value\" : \"989223112\",\n"
        + "      \"bigdecimal_value\" : \"1234.7981723987\",\n"
        + "      \"boolean_value\" : \"true\",\n"
        + "      \"float_value\" : \"3.141592\"\n"
        + "  }"
        + "}";
  }

  public static String createSubscriptionFakeResponse =
      "{\n"
          + "   \"id\":\"11e897ac-8cc6-5178-aea8-b7d42ee9639f\",\n"
          + "   \"store_id\":\"11e82dbf-7e6a-d146-9423-03ae2c18d764\",\n"
          + "   \"transaction_token_id\":\"11e897ac-8cb5-36c2-aea6-0fe2b400c0b1\",\n"
          + "   \"amount\":10000,\n"
          + "   \"currency\":\"JPY\",\n"
          + "   \"amount_formatted\":10000,\n"
          + "   \"period\":\"monthly\",\n"
          + "   \"amount_left\":0,"
          + "   \"amount_left_formatted\":0"
          + "   \"initial_amount\":1000,\n"
          + "   \"initial_amount_formatted\":1000,\n"
          + "   \"subsequent_cycles_start\":null,\n"
          + "   \"schedule_settings\":{\n"
          + "      \"start_on\":\"2018-08-31\",\n"
          + "      \"zone_id\":\"America/Cancun\",\n"
          + "      \"preserve_end_of_month\":null\n"
          + "   },\n"
          + "   \"first_charge_capture_after\": \"PT48H\",\n"
          + "   \"first_charge_authorization_only\": true, \n"
          + "   \"only_direct_currency\": true,\n"
          + "   \"status\":\"unverified\",\n"
          + "   \"descriptor\": \"test descriptor\",\n"
          + "   \"metadata\":{\n"
          + "      \"some_key\":\"some value\"\n"
          + "   },\n"
          + "   \"mode\":\"test\",\n"
          + "   \"created_on\":\"2018-08-04T06:06:38.677459Z\",\n"
          + "   \"next_payment\":{\n"
          + "      \"id\":\"11e897ac-8ccc-b176-aea9-37b0f1f6fa10\",\n"
          + "      \"due_date\":\"2018-08-04\",\n"
          + "      \"zone_id\":\"America/Cancun\",\n"
          + "      \"amount\":1000,\n"
          + "      \"currency\":\"JPY\",\n"
          + "      \"amount_formatted\":1000,\n"
          + "      \"is_paid\":false,\n"
          + "      \"is_last_payment\":false,\n"
          + "      \"created_on\":\"2018-08-04T06:06:38.677459Z\",\n"
          + "      \"updated_on\":\"2018-08-04T06:06:38.677459Z\"\n"
          + "   },\n"
          + "   \"payments_left\":null,\n"
          + "   \"installment_plan\":null,\n"
          + "   \"amount_left\":null,\n"
          + "   \"amount_left_formatted\":null\n"
          + "}";

  public static String createFixedCycleInstallmentsSubscriptionFakeResponse =
      "{\"id\":\"11e89704-fa3e-b22a-b482-4f70aef2a039\",\"store_id\":\"11e82dbf-7e6a-d146-9423-03ae2c18d764\",\"transaction_token_id\":\"11e89704-fa2f-ec0e-8f78-ab45170ecd0d\",\"amount\":12000,\"amount_left\":0,\"amount_left_formatted\":0,\"currency\":\"JPY\",\"amount_formatted\":12000,\"period\":\"biweekly\",\"initial_amount\":1000,\"initial_amount_formatted\":1000,\"subsequent_cycles_start\":null,\"schedule_settings\":{\"start_on\":\"2018-08-31\",\"zone_id\":\"Asia/Tokyo\",\"preserve_end_of_month\":null},\"status\":\"current\",\"metadata\":{},\"mode\":\"test\",\"created_on\":\"2018-08-03T10:07:06.887446Z\",\"next_payment\":{\"id\":\"11e89704-fa47-ea16-b484-dfc0efdd7c9f\",\"due_date\":\"2018-08-03\",\"zone_id\":\"Asia/Tokyo\",\"amount\":1000,\"currency\":\"JPY\",\"amount_formatted\":1000,\"is_paid\":false,\"is_last_payment\":false,\"created_on\":\"2018-08-03T10:07:06.887446Z\",\"updated_on\":\"2018-08-03T10:07:06.887446Z\"},\"payments_left\":5,\"installment_plan\":{\"plan_type\":\"fixed_cycles\",\"fixed_cycles\":5},\"amount_left\":0,\"amount_left_formatted\":0}";

  public static String createFixedCycleAmountInstallmentsSubscriptionFakeResponse =
      "{\"id\":\"11e89704-9726-f526-b47f-7b879e7ce5b4\",\"store_id\":\"11e82dbf-7e6a-d146-9423-03ae2c18d764\",\"transaction_token_id\":\"11e89704-9718-fa84-b47d-03e96438cedb\",\"amount\":12000,\"amount_left\":0,\"amount_left_formatted\":0,\"currency\":\"JPY\",\"amount_formatted\":12000,\"period\":\"biweekly\",\"initial_amount\":1000,\"initial_amount_formatted\":1000,\"subsequent_cycles_start\":null,\"schedule_settings\":{\"start_on\":\"2018-08-31\",\"zone_id\":\"Asia/Tokyo\",\"preserve_end_of_month\":null},\"status\":\"current\",\"metadata\":{},\"mode\":\"test\",\"created_on\":\"2018-08-03T10:04:20.637209Z\",\"next_payment\":{\"id\":\"11e89704-9733-65ae-b481-a30a06a542dc\",\"due_date\":\"2018-08-03\",\"zone_id\":\"Asia/Tokyo\",\"amount\":1000,\"currency\":\"JPY\",\"amount_formatted\":1000,\"is_paid\":false,\"is_last_payment\":false,\"created_on\":\"2018-08-03T10:04:20.637209Z\",\"updated_on\":\"2018-08-03T10:04:20.637209Z\"},\"payments_left\":4,\"installment_plan\":{\"plan_type\":\"fixed_cycle_amount\",\"fixed_cycle_amount\":5000},\"amount_left\":0,\"amount_left_formatted\":0}";

  public static String createRevolvingInstallmentsSubscriptionFakeResponse =
      "      {  \n"
          + "         \"id\":\"11e821e9-8078-c3bc-851f-8b3cff59a635\",\n"
          + "         \"store_id\":\"11e821e9-806e-7a06-a00c-fb1ee377211d\",\n"
          + "         \"transaction_token_id\":\"11e821e9-8072-e88e-9dbc-f3aef6074045\",\n"
          + "         \"amount\":1000,\n"
          + "         \"amount_left\":0,"
          + "         \"amount_left_formatted\":0,"
          + "         \"currency\":\"JPY\",\n"
          + "         \"amount_formatted\":1000,\n"
          + "         \"period\":\"daily\",\n"
          + "         \"status\":\"current\",\n"
          + "         \"active\":true,\n"
          + "         \"metadata\":{  \n"
          + "            \"test\":\"ok\"\n"
          + "         },\n"
          + "         \"mode\":\"live_test\",\n"
          + "         \"created_on\":\"2018-03-07T18:25:40.128999+09:00\",\n"
          + "         \"installment_plan\":{  \n"
          + "           \"plan_type\":\"revolving\",\n"
          + "           \"initial_amount\":100\n"
          + "         }"
          + "      }\n";

  public static String createSubscriptionMetadataFakeResponse(boolean useDescriptor) {
    String descriptor = "";
    if (useDescriptor) {
      descriptor = "  \"descriptor\": \"test descriptor\",\n";
    }

    return "{\n"
        + "   \"id\":\"11e897ac-8cc6-5178-aea8-b7d42ee9639f\",\n"
        + "   \"store_id\":\"11e82dbf-7e6a-d146-9423-03ae2c18d764\",\n"
        + "   \"transaction_token_id\":\"11e897ac-8cb5-36c2-aea6-0fe2b400c0b1\",\n"
        + "   \"amount\":10000,\n"
        + "   \"amount_left\":0,"
        + "   \"amount_left_formatted\":0,"
        + "   \"currency\":\"JPY\",\n"
        + "   \"amount_formatted\":10000,\n"
        + "   \"period\":\"monthly\",\n"
        + descriptor
        + "   \"initial_amount\":1000,\n"
        + "   \"initial_amount_formatted\":1000,\n"
        + "   \"subsequent_cycles_start\":null,\n"
        + "   \"schedule_settings\":{\n"
        + "      \"start_on\":\"2018-08-31\",\n"
        + "      \"zone_id\":\"America/Cancun\",\n"
        + "      \"preserve_end_of_month\":null\n"
        + "   },\n"
        + "   \"status\":\"unverified\",\n"
        + "   \"metadata\": {\n"
        + "      \"string_value\" : \"hola\",\n"
        + "      \"biginteger_value\" : \"989223112\",\n"
        + "      \"bigdecimal_value\" : \"1234.7981723987\",\n"
        + "      \"boolean_value\" : \"true\",\n"
        + "      \"float_value\" : \"3.141592\"\n"
        + "   },"
        + "   \"mode\":\"test\",\n"
        + "   \"created_on\":\"2018-08-04T06:06:38.677459Z\",\n"
        + "   \"next_payment\":{\n"
        + "      \"id\":\"11e897ac-8ccc-b176-aea9-37b0f1f6fa10\",\n"
        + "      \"due_date\":\"2018-08-04\",\n"
        + "      \"zone_id\":\"America/Cancun\",\n"
        + "      \"amount\":1000,\n"
        + "      \"currency\":\"JPY\",\n"
        + "      \"amount_formatted\":1000,\n"
        + "      \"is_paid\":false,\n"
        + "      \"is_last_payment\":false,\n"
        + "      \"created_on\":\"2018-08-04T06:06:38.677459Z\",\n"
        + "      \"updated_on\":\"2018-08-04T06:06:38.677459Z\"\n"
        + "   },\n"
        + "   \"payments_left\":null,\n"
        + "   \"installment_plan\":null,\n"
        + "   \"amount_left\":null,\n"
        + "   \"amount_left_formatted\":null\n"
        + "}";
  }

  public static String updateSubscriptionFakeRequest =
      "{"
          + "  \"transaction_token_id\":\"7f5eecc8-3b38-4cec-86bb-644af74cb186\","
          + "  \"period\":\"biweekly\","
          + "  \"initial_amount\":2000,"
          + "  \"only_direct_currency\": true,\n"
          + "  \"descriptor\": \"test descriptor\",\n"
          + "  \"status\":\"unpaid\","
          + "  \"installment_plan\" : {\n"
          + "    \"fixed_cycles\" : 10,\n"
          + "    \"plan_type\" : \"fixed_cycles\"\n"
          + "  },"
          + "  \"metadata\": {\n"
          + "      \"string_value\" : \"hola\",\n"
          + "      \"biginteger_value\" : \"989223112\",\n"
          + "      \"bigdecimal_value\" : \"1234.7981723987\",\n"
          + "      \"boolean_value\" : \"true\",\n"
          + "      \"float_value\" : \"3.141592\"\n"
          + "  },"
          + "  \"schedule_settings\":{\"start_on\":\"2018-09-07\", \"preserve_end_of_month\":true}"
          + "}";

  public static String updateToFixedCycleInstallmentsSubscriptionFakeRequest =
      ""
          + "{\"installment_plan\": {\n"
          + "            \"plan_type\": \"fixed_cycles\",\n"
          + "            \"fixed_cycles\": 8\n"
          + "    },"
          + "\"transaction_token_id\" : \"7f5eecc8-3b38-4cec-86bb-644af74cb186\""
          + "}";

  public static String removeInstallmentsPlanFakeRequest =
      ""
          + "{\"installment_plan\": null,"
          + "\"transaction_token_id\" : \"7f5eecc8-3b38-4cec-86bb-644af74cb186\""
          + "}";

  public static String updateSubscriptionFakeResponse =
      "{\n"
          + "   \"id\":\"11e897ac-8cc6-5178-aea8-b7d42ee9639f\",\n"
          + "   \"store_id\":\"11e82dbf-7e6a-d146-9423-03ae2c18d764\",\n"
          + "   \"transaction_token_id\":\"11e897ac-8cb5-36c2-aea6-0fe2b400c0b1\",\n"
          + "   \"amount\":500,\n"
          + "   \"amount_left\":0,"
          + "   \"amount_left_formatted\":0"
          + "   \"currency\":\"JPY\",\n"
          + "   \"amount_formatted\":500,\n"
          + "   \"period\":\"monthly\",\n"
          + "   \"initial_amount\":2000,\n"
          + "   \"initial_amount_formatted\":2000,\n"
          + "   \"subsequent_cycles_start\":null,\n"
          + "   \"schedule_settings\":{\n"
          + "      \"start_on\":\"2018-09-07\",\n"
          + "      \"zone_id\":\"America/Cancun\",\n"
          + "      \"preserve_end_of_month\":null\n"
          + "   },\n"
          + "   \"status\":\"unverified\",\n"
          + "   \"metadata\":{\n"
          + "      \"some_key\":\"some value\"\n"
          + "   },\n"
          + "   \"mode\":\"test\",\n"
          + "   \"created_on\":\"2018-08-04T06:06:38.677459Z\",\n"
          + "   \"next_payment\":{\n"
          + "      \"id\":\"11e897ac-8ccc-b176-aea9-37b0f1f6fa10\",\n"
          + "      \"due_date\":\"2018-08-04\",\n"
          + "      \"zone_id\":\"America/Cancun\",\n"
          + "      \"amount\":1000,\n"
          + "      \"currency\":\"JPY\",\n"
          + "      \"amount_formatted\":1000,\n"
          + "      \"is_paid\":false,\n"
          + "      \"is_last_payment\":false,\n"
          + "      \"created_on\":\"2018-08-04T06:06:38.677459Z\",\n"
          + "      \"updated_on\":\"2018-08-04T06:06:38.677459Z\"\n"
          + "   },\n"
          + "   \"payments_left\":null,\n"
          + "   \"installment_plan\":null,\n"
          + "   \"amount_left\":null,\n"
          + "   \"amount_left_formatted\":null\n"
          + "}";

  public static String updateSubscriptionMetadataFloatFakeRequest =
      "{\n" + "  \"metadata\": {\n" + "      \"float\": \"10.3\"\n" + "  }\n" + "}";

  public static String listAllStoreSubscriptionsFakeResponse =
      "{  \n"
          + "   \"items\":[  \n"
          + "      {  \n"
          + "         \"id\":\"11e897b6-0191-b412-ad05-87632bade75a\",\n"
          + "         \"store_id\":\"11e82dbf-7e6a-d146-9423-03ae2c18d764\",\n"
          + "         \"transaction_token_id\":\"11e897b6-017d-9108-ad03-c72af7e345ea\",\n"
          + "         \"amount\":1000,\n"
          + "         \"currency\":\"JPY\",\n"
          + "         \"amount_formatted\":1000,\n"
          + "         \"period\":\"monthly\",\n"
          + "         \"initial_amount\":10000,\n"
          + "         \"initial_amount_formatted\":10000,\n"
          + "         \"subsequent_cycles_start\":null,\n"
          + "         \"schedule_settings\":{  \n"
          + "            \"start_on\":\"2018-08-31\",\n"
          + "            \"zone_id\":\"America/Cancun\",\n"
          + "            \"preserve_end_of_month\":null\n"
          + "         },\n"
          + "         \"status\":\"current\",\n"
          + "         \"metadata\":{  \n"
          + "            \"some_key\":\"some value\"\n"
          + "         },\n"
          + "         \"mode\":\"test\",\n"
          + "         \"created_on\":\"2018-08-04T07:14:20.096588Z\"\n"
          + "      },\n"
          + "      {  \n"
          + "         \"id\":\"11e897ac-8cc6-5178-aea8-b7d42ee9639f\",\n"
          + "         \"store_id\":\"11e82dbf-7e6a-d146-9423-03ae2c18d764\",\n"
          + "         \"transaction_token_id\":\"11e897ac-8cb5-36c2-aea6-0fe2b400c0b1\",\n"
          + "         \"amount\":10000,\n"
          + "         \"currency\":\"JPY\",\n"
          + "         \"amount_formatted\":10000,\n"
          + "         \"period\":\"monthly\",\n"
          + "         \"initial_amount\":1000,\n"
          + "         \"initial_amount_formatted\":1000,\n"
          + "         \"subsequent_cycles_start\":null,\n"
          + "         \"schedule_settings\":{  \n"
          + "            \"start_on\":\"2018-08-31\",\n"
          + "            \"zone_id\":\"America/Cancun\",\n"
          + "            \"preserve_end_of_month\":null\n"
          + "         },\n"
          + "         \"status\":\"current\",\n"
          + "         \"metadata\":{  \n"
          + "            \"some_key\":\"some value\"\n"
          + "         },\n"
          + "         \"mode\":\"test\",\n"
          + "         \"created_on\":\"2018-08-04T06:06:38.677459Z\"\n"
          + "      } "
          + "   ],\n"
          + "   \"has_more\":false\n"
          + "}";

  public static String listAllMerchantSubscriptionsFakeResponse =
      "{  \n"
          + "   \"items\":[  \n"
          + "      {  \n"
          + "         \"id\":\"11e897b6-0191-b412-ad05-87632bade75a\",\n"
          + "         \"store_id\":\"11e82dbf-7e6a-d146-9423-03ae2c18d764\",\n"
          + "         \"transaction_token_id\":\"11e897b6-017d-9108-ad03-c72af7e345ea\",\n"
          + "         \"amount\":1000,\n"
          + "         \"currency\":\"JPY\",\n"
          + "         \"amount_formatted\":1000,\n"
          + "         \"period\":\"monthly\",\n"
          + "         \"initial_amount\":10000,\n"
          + "         \"initial_amount_formatted\":10000,\n"
          + "         \"subsequent_cycles_start\":null,\n"
          + "         \"schedule_settings\":{  \n"
          + "            \"start_on\":\"2018-08-31\",\n"
          + "            \"zone_id\":\"America/Cancun\",\n"
          + "            \"preserve_end_of_month\":null\n"
          + "         },\n"
          + "         \"only_direct_currency\": true,\n"
          + "         \"status\":\"current\",\n"
          + "         \"descriptor\": \"test descriptor\",\n"
          + "         \"metadata\":{  \n"
          + "            \"some_key\":\"some value\"\n"
          + "         },\n"
          + "         \"mode\":\"test\",\n"
          + "         \"created_on\":\"2018-08-04T07:14:20.096588Z\"\n"
          + "      },\n"
          + "      {  \n"
          + "         \"id\":\"11e897ac-8cc6-5178-aea8-b7d42ee9639f\",\n"
          + "         \"store_id\":\"11e82dbf-7e6a-d146-9423-03ae2c18d764\",\n"
          + "         \"transaction_token_id\":\"11e897ac-8cb5-36c2-aea6-0fe2b400c0b1\",\n"
          + "         \"amount\":10000,\n"
          + "         \"currency\":\"JPY\",\n"
          + "         \"amount_formatted\":10000,\n"
          + "         \"period\":\"monthly\",\n"
          + "         \"initial_amount\":1000,\n"
          + "         \"initial_amount_formatted\":1000,\n"
          + "         \"subsequent_cycles_start\":null,\n"
          + "         \"schedule_settings\":{  \n"
          + "            \"start_on\":\"2018-08-31\",\n"
          + "            \"zone_id\":\"America/Cancun\",\n"
          + "            \"preserve_end_of_month\":null\n"
          + "         },\n"
          + "         \"only_direct_currency\": false,\n"
          + "         \"status\":\"current\",\n"
          + "         \"descriptor\": null,\n"
          + "         \"metadata\":{  \n"
          + "            \"some_key\":\"some value\"\n"
          + "         },\n"
          + "         \"mode\":\"test\",\n"
          + "         \"created_on\":\"2018-08-04T06:06:38.677459Z\"\n"
          + "      } "
          + "   ],\n"
          + "   \"has_more\":false\n"
          + "}";

  public static String getSubscriptionFakeResponse =
      "{\n"
          + "   \"id\":\"11e897ac-8cc6-5178-aea8-b7d42ee9639f\",\n"
          + "   \"store_id\":\"11e82dbf-7e6a-d146-9423-03ae2c18d764\",\n"
          + "   \"transaction_token_id\":\"11e897ac-8cb5-36c2-aea6-0fe2b400c0b1\",\n"
          + "   \"amount\":10000,\n"
          + "   \"amount_left\":0,\n"
          + "   \"amount_left_formatted\":0,\n"
          + "   \"currency\":\"JPY\",\n"
          + "   \"amount_formatted\":10000,\n"
          + "   \"period\":\"monthly\",\n"
          + "   \"initial_amount\":1000,\n"
          + "   \"initial_amount_formatted\":1000,\n"
          + "   \"subsequent_cycles_start\":null,\n"
          + "   \"schedule_settings\":{\n"
          + "      \"start_on\":\"2018-08-31\",\n"
          + "      \"zone_id\":\"America/Cancun\",\n"
          + "      \"preserve_end_of_month\":null\n"
          + "   },\n"
          + "   \"only_direct_currency\": true,\n"
          + "   \"status\":\"unverified\",\n"
          + "   \"descriptor\": \"test descriptor\","
          + "   \"metadata\":{\n"
          + "      \"some_key\":\"some value\"\n"
          + "   },\n"
          + "   \"mode\":\"test\",\n"
          + "   \"created_on\":\"2018-08-04T06:06:38.677459Z\",\n"
          + "   \"next_payment\":{\n"
          + "      \"id\":\"11e897ac-8ccc-b176-aea9-37b0f1f6fa10\",\n"
          + "      \"due_date\":\"2018-08-04\",\n"
          + "      \"zone_id\":\"America/Cancun\",\n"
          + "      \"amount\":1000,\n"
          + "      \"currency\":\"JPY\",\n"
          + "      \"amount_formatted\":1000,\n"
          + "      \"is_paid\":false,\n"
          + "      \"is_last_payment\":false,\n"
          + "      \"created_on\":\"2018-08-04T06:06:38.677459Z\",\n"
          + "      \"updated_on\":\"2018-08-04T06:06:38.677459Z\"\n"
          + "   },\n"
          + "   \"payments_left\":null,\n"
          + "   \"installment_plan\":null\n"
          + "}";

  public static String getSubscriptionCompletedFakeResponse =
      "{\n"
          + "   \"id\":\"11e897ac-8cc6-5178-aea8-b7d42ee9639f\",\n"
          + "   \"store_id\":\"11e82dbf-7e6a-d146-9423-03ae2c18d764\",\n"
          + "   \"transaction_token_id\":\"11e897ac-8cb5-36c2-aea6-0fe2b400c0b1\",\n"
          + "   \"amount\":10000,\n"
          + "   \"amount_left\":0,\n"
          + "   \"amount_left_formatted\":0,\n"
          + "   \"currency\":\"JPY\",\n"
          + "   \"amount_formatted\":10000,\n"
          + "   \"period\":\"monthly\",\n"
          + "   \"initial_amount\":1000,\n"
          + "   \"initial_amount_formatted\":1000,\n"
          + "   \"subsequent_cycles_start\":null,\n"
          + "   \"schedule_settings\":{\n"
          + "      \"start_on\":\"2018-08-31\",\n"
          + "      \"zone_id\":\"America/Cancun\",\n"
          + "      \"preserve_end_of_month\":null\n"
          + "   },\n"
          + "   \"status\":\"completed\",\n"
          + "   \"metadata\":{\n"
          + "      \"some_key\":\"some value\"\n"
          + "   },\n"
          + "   \"mode\":\"test\",\n"
          + "   \"created_on\":\"2018-08-04T06:06:38.677459Z\",\n"
          + "   \"next_payment\":{\n"
          + "      \"id\":\"11e897ac-8ccc-b176-aea9-37b0f1f6fa10\",\n"
          + "      \"due_date\":\"2018-08-04\",\n"
          + "      \"zone_id\":\"America/Cancun\",\n"
          + "      \"amount\":1000,\n"
          + "      \"currency\":\"JPY\",\n"
          + "      \"amount_formatted\":1000,\n"
          + "      \"is_paid\":false,\n"
          + "      \"is_last_payment\":false,\n"
          + "      \"created_on\":\"2018-08-04T06:06:38.677459Z\",\n"
          + "      \"updated_on\":\"2018-08-04T06:06:38.677459Z\"\n"
          + "   },\n"
          + "   \"payments_left\":null,\n"
          + "   \"installment_plan\":null\n"
          + "}";

  public static String getPaymentFakeResponse =
      "{  \n"
          + "   \"id\":\"11e89925-967d-1bd2-a831-43c06e016572\",\n"
          + "   \"due_date\":\"2019-01-30\",\n"
          + "   \"zone_id\":\"Asia/Tokyo\",\n"
          + "   \"amount\":21963,\n"
          + "   \"currency\":\"JPY\",\n"
          + "   \"amount_formatted\":21963,\n"
          + "   \"is_paid\":false,\n"
          + "   \"is_last_payment\":false,\n"
          + "   \"created_on\":\"2018-08-06T03:05:35.097867Z\",\n"
          + "   \"updated_on\":\"2018-08-06T03:05:35.097867Z\"\n"
          + "}";

  public static String simulatePlanFakeResponse =
      "[\n"
          + "    {\n"
          + "        \"due_date\": \"2018-08-06\",\n"
          + "        \"zone_id\": \"America/Cancun\",\n"
          + "        \"amount\": 10000,\n"
          + "        \"currency\": \"JPY\",\n"
          + "        \"is_paid\": false,\n"
          + "        \"is_last_payment\": false\n"
          + "    },\n"
          + "    {\n"
          + "        \"due_date\": \"2020-01-01\",\n"
          + "        \"zone_id\": \"America/Cancun\",\n"
          + "        \"amount\": 95000,\n"
          + "        \"currency\": \"JPY\",\n"
          + "        \"is_paid\": false,\n"
          + "        \"is_last_payment\": false\n"
          + "    },\n"
          + "    {\n"
          + "        \"due_date\": \"2020-04-01\",\n"
          + "        \"zone_id\": \"America/Cancun\",\n"
          + "        \"amount\": 95000,\n"
          + "        \"currency\": \"JPY\",\n"
          + "        \"is_paid\": false,\n"
          + "        \"is_last_payment\": true\n"
          + "    }\n"
          + "]";

  public static String simulatePlanFakeRequest =
      "{"
          + "\"amount\": 200000,"
          + "\"currency\": \"JPY\","
          + "\"payment_type\": \"card\","
          + "\"period\": \"quarterly\","
          + "\"initial_amount\": 10000,"
          + "\"installment_plan\": {"
          + "\"plan_type\": \"fixed_cycles\","
          + "\"fixed_cycles\": 3"
          + "},"
          + "\"schedule_settings\": {"
          + "\"start_on\": \"2020-01-01\","
          + "\"zone_id\": \"America/Cancun\","
          + "\"preserve_end_of_month\": true"
          + "}"
          + "}";

  public static String listPaymentsFakeResponse =
      "{\n"
          + "   \"items\":[\n"
          + "      {\n"
          + "         \"id\":\"11e89932-7c9b-efce-a32a-73219ce0ccd1\",\n"
          + "         \"due_date\":\"2018-09-30\",\n"
          + "         \"zone_id\":\"Asia/Tokyo\",\n"
          + "         \"amount\":6000,\n"
          + "         \"currency\":\"JPY\",\n"
          + "         \"amount_formatted\":6000,\n"
          + "         \"is_paid\":false,\n"
          + "         \"is_last_payment\":true,\n"
          + "         \"created_on\":\"2018-08-06T04:37:55.261216Z\",\n"
          + "         \"updated_on\":\"2018-08-06T04:37:55.261216Z\"\n"
          + "      },\n"
          + "      {\n"
          + "         \"id\":\"11e89932-7c98-f4fe-a329-fb3d1441fc35\",\n"
          + "         \"due_date\":\"2018-08-31\",\n"
          + "         \"zone_id\":\"Asia/Tokyo\",\n"
          + "         \"amount\":6000,\n"
          + "         \"currency\":\"JPY\",\n"
          + "         \"amount_formatted\":6000,\n"
          + "         \"is_paid\":false,\n"
          + "         \"is_last_payment\":false,\n"
          + "         \"created_on\":\"2018-08-06T04:37:55.261216Z\",\n"
          + "         \"updated_on\":\"2018-08-06T04:37:55.261216Z\"\n"
          + "      },\n"
          + "      {\n"
          + "         \"id\":\"11e89932-7c6a-8376-b749-7b3852eeec56\",\n"
          + "         \"due_date\":\"2018-08-06\",\n"
          + "         \"zone_id\":\"Asia/Tokyo\",\n"
          + "         \"amount\":3000,\n"
          + "         \"currency\":\"JPY\",\n"
          + "         \"amount_formatted\":3000,\n"
          + "         \"is_paid\":true,\n"
          + "         \"is_last_payment\":false,\n"
          + "         \"created_on\":\"2018-08-06T04:37:54.884225Z\",\n"
          + "         \"updated_on\":\"2018-08-06T04:37:55.198877Z\"\n"
          + "      }\n"
          + "   ],\n"
          + "   \"has_more\":false\n"
          + "}";

  public static String listChargesForPaymentFakeRequest =
      "{\n"
          + "   \"items\":[\n"
          + "      {\n"
          + "         \"id\":\"11e89932-7c77-9bc4-a326-b7d009577283\",\n"
          + "         \"store_id\":\"11e82dbf-7e6a-d146-9423-03ae2c18d764\",\n"
          + "         \"transaction_token_id\":\"11e89932-7c53-02fa-b745-03bca6731d36\",\n"
          + "         \"transaction_token_type\":\"subscription\",\n"
          + "         \"subscription_id\":\"11e89932-7c5f-78c8-b747-27cf2f8ee9b4\",\n"
          + "         \"requested_amount\":3000,\n"
          + "         \"requested_currency\":\"JPY\",\n"
          + "         \"requested_amount_formatted\":3000,\n"
          + "         \"charged_amount\":3000,\n"
          + "         \"charged_currency\":\"JPY\",\n"
          + "         \"charged_amount_formatted\":3000,\n"
          + "         \"capture_at\":null,\n"
          + "         \"status\":\"successful\",\n"
          + "         \"error\":null,\n"
          + "         \"metadata\":{\n"
          + "\n"
          + "         },\n"
          + "         \"mode\":\"test\",\n"
          + "         \"created_on\":\"2018-08-06T04:37:55.042608Z\"\n"
          + "      }\n"
          + "   ],\n"
          + "   \"has_more\":false\n"
          + "}";

  public static String updatePaymentPaidStatusFakeRequest = "{" + "\"is_paid\": true" + "}";

  public static String listSubscriptionCharges =
      "{\n"
          + "  \"items\": [\n"
          + "    {\n"
          + "      \"id\": \"96fe72ee-e932-11e6-8f46-0bed6cd5f04d\",\n"
          + "      \"store_id\": \"33fb1370-e930-11e6-8e89-07a500c3935d\",\n"
          + "      \"transaction_token_id\": \"5b7a7e3e-e932-11e6-8f46-d7104cb63448\",\n"
          + "      \"subscription_id\": \"96fcbd50-e932-11e6-8f46-d79febd4479a\",\n"
          + "      \"requested_amount\": 1000,\n"
          + "      \"requested_currency\": \"JPY\",\n"
          + "      \"requested_amount_formatted\": 1000,\n"
          + "      \"charged_amount\": 1000,\n"
          + "      \"charged_currency\": \"JPY\",\n"
          + "      \"charged_amount_formatted\": 1000,\n"
          + "      \"only_direct_currency\": true,\n"
          + "      \"status\": \"successful\",\n"
          + "      \"descriptor\": \"test descriptor\","
          + "      \"error\": null,\n"
          + "      \"metadata\": {},\n"
          + "      \"mode\": \"test\",\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "    }\n"
          + "  ],\n"
          + "  \"has_more\": false\n"
          + "}";

  public static String captureChargeFakeRequest =
      "{" + "\"amount\": 1000," + "\"currency\": \"JPY\"" + "}";

  public static String authorizeChargeFakeRequest(String captureAt) {
    return "{\n"
        + "  \"transaction_token_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
        + "  \"amount\": 1000,\n"
        + "  \"currency\": \"JPY\",\n"
        + "  \"capture\": false,\n"
        + "  \"capture_at\": \""
        + captureAt
        + "\"\n"
        + "}";
  }

  public static String authorizeChargeFakeResponse =
      "{\n"
          + "  \"id\": \"425e88b7-b588-4247-80ee-0ea0caff1190\",\n"
          + "  \"store_id\": \"653ef5a3-73f2-408a-bac5-7058835f7700\",\n"
          + "  \"requested_amount\": 1000,\n"
          + "  \"requested_currency\": \"JPY\",\n"
          + "  \"charged_amount\": null,\n"
          + "  \"charged_currency\": null,\n"
          + "  \"status\": \"pending\",\n"
          + "  \"error\": null,\n"
          + "  \"test_mode\": true,\n"
          + "  \"created_on\": null\n"
          + "}";
}
