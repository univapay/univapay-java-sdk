package com.univapay.sdk.utils.mockcontent;

public class CancelsFakeRR {

  public static String listCancelsFakeResponse =
      "{\n"
          + " \"items\": [\n"
          + "  {\n"
          + "   \"id\": \"cdf3ba40-b333-11e7-a37d-d75967ccf22e\",\n"
          + "   \"charge_id\": \"11e7b333-cb82-3d54-a37d-036f78f60e1c\",\n"
          + "   \"store_id\": \"11e7b331-ee33-f8ee-a37d-1b150f2ba2f6\",\n"
          + "   \"status\": \"successful\",\n"
          + "   \"error\": null,\n"
          + "   \"metadata\": {\n"
          + "    \"product_id\": \"updated123\"\n"
          + "   },\n"
          + "   \"mode\": \"test\",\n"
          + "   \"created_on\": \"2017-10-17T12:07:53.809331Z\"\n"
          + "  }\n"
          + " ],\n"
          + " \"has_more\": false\n"
          + "}";

  public static String getCancelFakeResponse =
      ""
          + "{\n"
          + " \"id\": \"cdf3ba40-b333-11e7-a37d-d75967ccf22e\",\n"
          + " \"charge_id\": \"11e7b333-cb82-3d54-a37d-036f78f60e1c\",\n"
          + " \"store_id\": \"11e7b331-ee33-f8ee-a37d-1b150f2ba2f6\",\n"
          + " \"status\": \"successful\",\n"
          + " \"error\": null,\n"
          + " \"metadata\": {\n"
          + "  \"product_id\": \"1245\",\n"
          + "  \"customer_id\": \"12345678\"\n"
          + " },\n"
          + " \"mode\": \"test\",\n"
          + " \"created_on\": \"2017-10-17T12:07:53.809331Z\"\n"
          + "}";

  public static String createCancelFakeRequest =
      "{\n"
          + "\"metadata\": {\n"
          + "  \"product_id\": \"1245\",\n"
          + "  \"customer_id\": \"12345678\"\n"
          + " }\n"
          + "}";

  public static String createCancelFakeRequestMetadata =
      "{\n"
          + "  \"metadata\": {\n"
          + "      \"float\": \"10.3\",\n"
          + "      \"number\": \"10\",\n"
          + "      \"string\": \"string something\"\n"
          + "  }\n"
          + "}";

  public static String cancelFakeRequestMetadataFloat =
      "{\n" + "  \"metadata\": {\n" + "      \"float\": \"10.3\"\n" + "  }\n" + "}";

  public static String cancelFakeRequestMetadataArray =
      "{\n"
          + "  \"metadata\": {\n"
          + "      \"key1\": \"a,b,c\",\n"
          + "      \"key2\": \"x,y,z\"\n"
          + "  }\n"
          + "}";

  public static String createCancelFakeResponse =
      "{\n"
          + " \"id\": \"cdf3ba40-b333-11e7-a37d-d75967ccf22e\",\n"
          + " \"charge_id\": \"11e7b333-cb82-3d54-a37d-036f78f60e1c\",\n"
          + " \"store_id\": \"11e7b331-ee33-f8ee-a37d-1b150f2ba2f6\",\n"
          + " \"status\": \"pending\",\n"
          + " \"error\": null,\n"
          + " \"metadata\": {\n"
          + "  \"product_id\": \"1245\",\n"
          + "  \"customer_id\": \"12345678\"\n"
          + " },\n"
          + " \"mode\": \"test\",\n"
          + " \"created_on\": \"2017-10-17T12:07:53.809331Z\"\n"
          + "}";

  public static String createCancelFakeResponseMetadata =
      "{\n"
          + " \"id\": \"cdf3ba40-b333-11e7-a37d-d75967ccf22e\",\n"
          + " \"charge_id\": \"11e7b333-cb82-3d54-a37d-036f78f60e1c\",\n"
          + " \"store_id\": \"11e7b331-ee33-f8ee-a37d-1b150f2ba2f6\",\n"
          + " \"status\": \"pending\",\n"
          + " \"error\": null,\n"
          + "  \"metadata\": {\n"
          + "      \"float\": \"10.3\",\n"
          + "      \"number\": \"10\",\n"
          + "      \"string\": \"string something\"\n"
          + "  },\n"
          + " \"mode\": \"test\",\n"
          + " \"created_on\": \"2017-10-17T12:07:53.809331Z\"\n"
          + "}";

  public static String cancelFakeResponseMetadataFloat =
      "{\n"
          + " \"id\": \"cdf3ba40-b333-11e7-a37d-d75967ccf22e\",\n"
          + " \"charge_id\": \"11e7b333-cb82-3d54-a37d-036f78f60e1c\",\n"
          + " \"store_id\": \"11e7b331-ee33-f8ee-a37d-1b150f2ba2f6\",\n"
          + " \"status\": \"pending\",\n"
          + " \"error\": null,\n"
          + "  \"metadata\": {\n"
          + "      \"float\": \"10.3\"\n"
          + "  },\n"
          + " \"mode\": \"test\",\n"
          + " \"created_on\": \"2017-10-17T12:07:53.809331Z\"\n"
          + "}";

  public static String cancelFakeResponseMetadataArray =
      "{\n"
          + " \"id\": \"cdf3ba40-b333-11e7-a37d-d75967ccf22e\",\n"
          + " \"charge_id\": \"11e7b333-cb82-3d54-a37d-036f78f60e1c\",\n"
          + " \"store_id\": \"11e7b331-ee33-f8ee-a37d-1b150f2ba2f6\",\n"
          + " \"status\": \"pending\",\n"
          + " \"error\": null,\n"
          + "  \"metadata\": {\n"
          + "      \"key1\": \"a,b,c\",\n"
          + "      \"key2\": \"x,y,z\"\n"
          + "  },\n"
          + " \"mode\": \"test\",\n"
          + " \"created_on\": \"2017-10-17T12:07:53.809331Z\"\n"
          + "}";

  public static String updateCancelFakeRequest =
      "        {\n" + "\"metadata\": {\n" + "  \"product_id\": \"updated123\"\n" + "}\n" + "}";

  public static String updateCancelFakeRequestMetadata =
      "        {\n"
          + "  \"metadata\": {\n"
          + "      \"array\": \"[string, 12.3]\",\n"
          + "      \"float\": \"10.3\",\n"
          + "      \"number\": \"10\",\n"
          + "      \"string\": \"string\"\n"
          + "  }\n"
          + "}";

  public static String updateCancelFakeResponse =
      "{\n"
          + " \"id\": \"cdf3ba40-b333-11e7-a37d-d75967ccf22e\",\n"
          + " \"charge_id\": \"11e7b333-cb82-3d54-a37d-036f78f60e1c\",\n"
          + " \"store_id\": \"11e7b331-ee33-f8ee-a37d-1b150f2ba2f6\",\n"
          + " \"status\": \"successful\",\n"
          + " \"error\": null,\n"
          + " \"metadata\": {\n"
          + "  \"product_id\": \"updated123\"\n"
          + " },\n"
          + " \"mode\": \"test\",\n"
          + " \"created_on\": \"2017-10-17T12:07:53.809331Z\"\n"
          + "}";

  public static String updateCancelFakeResponseMetadata =
      "{\n"
          + " \"id\": \"cdf3ba40-b333-11e7-a37d-d75967ccf22e\",\n"
          + " \"charge_id\": \"11e7b333-cb82-3d54-a37d-036f78f60e1c\",\n"
          + " \"store_id\": \"11e7b331-ee33-f8ee-a37d-1b150f2ba2f6\",\n"
          + " \"status\": \"successful\",\n"
          + " \"error\": null,\n"
          + "  \"metadata\": {\n"
          + "      \"array\": \"[string, 12.3]\",\n"
          + "      \"float\": 10.3,\n"
          + "      \"number\": 10,\n"
          + "      \"string\": \"string\"\n"
          + "  },\n"
          + " \"mode\": \"test\",\n"
          + " \"created_on\": \"2017-10-17T12:07:53.809331Z\"\n"
          + "}";
}
