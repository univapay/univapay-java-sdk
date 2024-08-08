package com.univapay.sdk.utils.mockcontent;

public class StoreFakeRR {
  public static String listAllStoresFakeResponse =
      "{\n"
          + "  \"items\": [\n"
          + "    {\n"
          + "      \"id\": \"100b8ac0-b76a-42b0-b5bd-a1e06f406056\",\n"
          + "      \"name\": \"New Store 5\",\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"id\": \"294eb164-71c5-475a-9baf-207381109dcf\",\n"
          + "      \"name\": \"New Store 4\",\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"id\": \"c7cdb063-b29a-49d1-acec-6693363ece11\",\n"
          + "      \"name\": \"New Store 3\",\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"id\": \"3dea7eb6-b03b-4c44-834e-cd9358ceeb61\",\n"
          + "      \"name\": \"New Store 2\",\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"id\": \"8486dc98-9836-41dd-b598-bbf49d5bc861\",\n"
          + "      \"name\": \"New Store 1\",\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "    }\n"
          + "  ],\n"
          + "  \"has_more\": false\n"
          + "}";

  public static String listAllStoresSearchParamFakeResponse =
      "{\n"
          + "  \"items\": [\n"
          + "    {\n"
          + "      \"id\": \"294eb164-71c5-475a-9baf-207381109dcf\",\n"
          + "      \"name\": \"New Store 4\",\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "    }\n"
          + "  ],\n"
          + "  \"has_more\": false\n"
          + "}";

  public static String listAllStoresPaginationParamFakeResponse =
      "{\n"
          + "  \"items\": [\n"
          + "    {\n"
          + "      \"id\": \"61cefa1f-ecee-42f8-932d-28dd233e5cbc\",\n"
          + "      \"name\": \"New Store 10\",\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"id\": \"8486dc98-9836-41dd-b598-bbf49d5bc861\",\n"
          + "      \"name\": \"New Store 1\",\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "    }\n"
          + "  ],\n"
          + "  \"has_more\": false\n"
          + "}";

  public static String createStoreFakeRequest =
      JsonLoader.loadJson("requests/store/post-store.json");

  public static String createStoreFakeResponse =
      JsonLoader.loadJson("responses/store/post-store.json");

  public static String createStoreFakeEmptyRequest =
      JsonLoader.loadJson("requests/store/post-store-empty.json");

  public static String createStoreFakeEmptyResponse =
      JsonLoader.loadJson("responses/store/post-store-empty.json");

  public static String getStoreFakeResponse =
      "{\n"
          + "    \"id\": \"11e751a6-15b1-169c-8d58-47c3d241a399\",\n"
          + "    \"name\": \"UnivaPay Store\",\n"
          + "    \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "    \"configuration\": {\n"
          + "        \"percent_fee\": 0.08,\n"
          + "        \"flat_fees\": null,\n"
          + "        \"logo_url\": \"http://www.logo.url\",\n"
          + "  	 	 \"country\": \"null\",\n"
          + "  	 	 \"language\": \"de\",\n"
          + "        \"card_configuration\": {\n"
          + "            \"enabled\": true,\n"
          + "            \"debit_enabled\": true,\n"
          + "            \"prepaid_enabled\": false\n"
          + "   		 \"forbidden_card_brands\": [ \"jcb\", \"maestro\" ],\n"
          + "   		 \"allowed_countries_by_ip\": [ \"AS\", \"AR\", \"FJ\" ],\n"
          + "   		 \"foreign_cards_allowed\": null,\n"
          + "   		 \"fail_on_new_email\": false,\n"
          + "   		 \"card_limit\": null,\n"
          + "			 \"allow_empty_cvv\": null\n"
          + "        },\n"
          + "        \"qr_scan_configuration\": {\n"
          + "            \"enabled\": true\n,"
          + "			 \"forbidden_qr_scan_gateways\": null\n"
          + "        },\n"
          + "		 \"convenience_configuration\": {\n"
          + "            \"enabled\": false\n"
          + "        }"
          + "        \"recurring_token_configuration\": {\n"
          + "            \"recurring_type\": \"infinite\",\n"
          + "            \"charge_wait_period\": null\n"
          + "        },\n"
          + "        \"security_configuration\": {\n"
          + "            \"inspect_suspicious_login_after\": \"P20D\",\n"
          + "    		 \"refund_percent_limit\": null,\n"
          + "   		 \"limit_charge_by_card_configuration\": null\n"
          + "        },\n"
          + "   \"installments_configuration\":{  \n"
          + "      \"enabled\":true,\n"
          + "      \"min_charge_amount\":{  \n"
          + "         \"amount\":10000,\n"
          + "         \"currency\":\"jpy\"\n"
          + "      },\n"
          + "      \"max_payout_period\":\"P30D\",\n"
          + "      \"failed_cycles_to_cancel\":2\n"
          + "   },\n"
          + "		 \"card_brand_percent_fees\": {\n"
          + "            \"visa\": null,\n"
          + "            \"american_express\": null,\n"
          + "            \"mastercard\": null,\n"
          + "            \"maestro\": null,\n"
          + "            \"discover\": null,\n"
          + "            \"jcb\": null,\n"
          + "            \"diners_club\": null,\n"
          + "            \"union_pay\": null\n"
          + "        }"
          + "    }\n"
          + "}";

  public static String updateStoreFakeRequest =
      JsonLoader.loadJson("requests/store/patch-store.json");

  public static String updateStoreFakeResponse =
      JsonLoader.loadJson("responses/store/patch-store.json");

  public static String updateStoreFakeEmptyRequest =
      JsonLoader.loadJson("requests/store/patch-store-empty.json");

  public static String updateStoreFakeEmptyResponse =
      JsonLoader.loadJson("responses/store/patch-store-empty.json");

  public static String listAllMerchantWebhooksResponse =
      "{\n"
          + "    \"items\": [\n"
          + "    {\n"
          + "        \"id\": \"11e7a41f-8a04-9980-a983-c34a960d1344\",\n"
          + "		 \"merchant_id\": \"11e81d1c-0990-29f6-8391-9b9a12e8726e\",\n"
          + "		 \"store_id\": \"11e786da-4714-5028-8280-bb9bc7cf54e9\",\n"
          + "        \"triggers\": [\n"
          + "            \"transfer_finalized\"\n"
          + "        ],\n"
          + "        \"url\": \"www.webhook.com\",\n"
          + "        \"created_on\": \"2017-09-28T07:35:02.000000+09:00\",\n"
          + "        \"updated_on\": \"2017-09-28T07:35:02.000000+09:00\"\n"
          + "    },\n"
          + "    {\n"
          + "        \"id\": \"11e79e8e-5eb2-2540-a5e6-f7fda438424b\",\n"
          + "		 \"merchant_id\": \"11e81d1c-0990-29f6-8391-9b9a12e8726e\",\n"
          + "		 \"store_id\": \"11e786da-4714-5028-8280-bb9bc7cf54e9\",\n"
          + "        \"triggers\": [\n"
          + "            \"charge_finished\",\n"
          + "            \"subscription_payment\",\n"
          + "            \"refund_finished\",\n"
          + "            \"subscription_canceled\",\n"
          + "            \"transfer_finalized\",\n"
          + "            \"subscription_failure\"\n"
          + "        ],\n"
          + "        \"url\": \"www.webhook.com\",\n"
          + "        \"created_on\": \"2017-09-21T05:33:16.000000+09:00\",\n"
          + "        \"updated_on\": \"2017-09-21T05:33:16.000000+09:00\"\n"
          + "    }\n"
          + "    ],\n"
          + "    \"has_more\": false\n"
          + "}";

  public static String getMerchantWebhookResponse =
      "{\n"
          + "\"id\": \"11e796c8-a853-8928-9665-a709cfc94f15\",\n"
          + "\"merchant_id\": \"11e81d1c-0990-29f6-8391-9b9a12e8726e\",\n"
          + "\"store_id\": \"11e786da-4714-5028-8280-bb9bc7cf54e9\",\n"
          + "        \"triggers\": [\n"
          + "            \"charge_finished\",\n"
          + "            \"subscription_failure\"\n"
          + "        ],\n"
          + "\"url\": \"http://www.webhook.com\",\n"
          + "\"created_on\": \"2017-09-11T08:10:21.000000+09:00\",\n"
          + "\"updated_on\": \"2017-09-11T08:10:21.000000+09:00\"\n"
          + "}";

  public static String updateMerchantWebhookResponse =
      "{\n"
          + "\"id\": \"11e7a41f-8a04-9980-a983-c34a960d1344\",\n"
          + "\"merchant_id\": \"11e81d1c-0990-29f6-8391-9b9a12e8726e\",\n"
          + "\"store_id\": \"11e786da-4714-5028-8280-bb9bc7cf54e9\",\n"
          + "\"triggers\": [\n"
          + "\"transfer_finalized\"\n"
          + "],\n"
          + "\"url\": \"http://www.webhook.com\",\n"
          + "\"created_on\": \"2017-09-28T07:35:02.000000+09:00\",\n"
          + "\"updated_on\": \"2017-09-28T07:35:02.000000+09:00\"\n"
          + "}";

  public static String createMerchantWebhookResponse =
      "{\n"
          + "\"id\": \"11e7a4e4-5a21-fc74-8205-17c780e6c7ee\",\n"
          + "\"merchant_id\": \"11e81d1c-0990-29f6-8391-9b9a12e8726e\",\n"
          + "\"store_id\": \"11e786da-4714-5028-8280-bb9bc7cf54e9\",\n"
          + "      \"triggers\": [\n"
          + "        \"charge_finished\",\n"
          + "        \"subscription_suspended\"\n"
          + "      ],\n"
          + "\"url\": \"http://www.webhook.com\",\n"
          + "\"created_on\": \"2017-09-29T07:03:52.000000+09:00\",\n"
          + "\"updated_on\": \"2017-09-29T07:03:52.000000+09:00\"\n"
          + "}";

  public static String listAllStoreWebhooksResponse =
      "{\n"
          + "  \"items\": [\n"
          + "    {\n"
          + "      \"id\": \"9a363dc6-ce7d-4b6d-af5b-b92aebd0bf40\",\n"
          + "	   \"merchant_id\": \"11e81d1c-0990-29f6-8391-9b9a12e8726e\",\n"
          + "      \"store_id\": \"8486dc98-9836-41dd-b598-bbf49d5bc861\",\n"
          + "      \"triggers\": [\n"
          + "        \"charge_finished\",\n"
          + "        \"subscription_canceled\",\n"
          + "        \"refund_finished\",\n"
          + "        \"transfer_finalized\"\n"
          + "      ],\n"
          + "      \"url\": \"http://www.anotherwebhook.com\",\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "      \"updated_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"id\": \"f3002de3-c076-4194-bef5-c88882e4f5fe\",\n"
          + "	   \"merchant_id\": \"11e81d1c-0990-29f6-8391-9b9a12e8726e\","
          + "      \"store_id\": \"8486dc98-9836-41dd-b598-bbf49d5bc861\",\n"
          + "      \"triggers\": [\n"
          + "        \"charge_finished\",\n"
          + "        \"subscription_payment\",\n"
          + "        \"refund_finished\",\n"
          + "        \"subscription_canceled\",\n"
          + "        \"transfer_finalized\",\n"
          + "        \"subscription_failure\"\n"
          + "      ],\n"
          + "      \"url\": \"http://www.webhook.com\",\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "      \"updated_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "    }\n"
          + "  ],\n"
          + "  \"has_more\": false\n"
          + "}";

  public static String getStoreWebhookResponse =
      "{\n"
          + "  \"id\": \"f3002de3-c076-4194-bef5-c88882e4f5fe\",\n"
          + "  \"merchant_id\": \"11e81d1c-0990-29f6-8391-9b9a12e8726e\",\n"
          + "  \"store_id\": \"8486dc98-9836-41dd-b598-bbf49d5bc861\",\n"
          + "  \"triggers\": [\n"
          + "    \"charge_finished\",\n"
          + "    \"subscription_payment\",\n"
          + "    \"refund_finished\",\n"
          + "    \"subscription_canceled\",\n"
          + "    \"transfer_finalized\",\n"
          + "    \"subscription_failure\"\n"
          + "  ],\n"
          + "  \"url\": \"http://www.webhook.com\",\n"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "  \"updated_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "}";

  public static String updateStoreWebhookResponse =
      "{\n"
          + "  \"id\": \"21c2906c-cd5e-455b-81f8-1a416349e810\",\n"
          + "  \"store_id\": \"f5cc70be-da82-4fad-affc-e79888189066\",\n"
          + "  \"merchant_id\": \"11e81d1c-0990-29f6-8391-9b9a12e8726e\",\n"
          + "  \"triggers\": [],\n"
          + "  \"url\": \"http://www.anotherurlcorrected.com\",\n"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "  \"updated_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "}";

  public static String createStoreWebhookResponse =
      "{\n"
          + "  \"id\": \"21c2906c-cd5e-455b-81f8-1a416349e810\",\n"
          + "  \"store_id\": \"f5cc70be-da82-4fad-affc-e79888189066\",\n"
          + "  \"merchant_id\": \"11e81d1c-0990-29f6-8391-9b9a12e8726e\",\n"
          + "      \"triggers\": [\n"
          + "        \"charge_finished\",\n"
          + "        \"subscription_suspended\"\n"
          + "      ],\n"
          + "  \"url\": \"http://www.anotherurl.com\",\n"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "  \"updated_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "}";

  public static String getStoreAppTokenFakeResponse =
      "{\"items\":[{\"id\":\"c0c9176b-0dd8-4644-85f9-adea6a7fca81\",\"store_id\":\"bf75472e-7f2d-4745-a66d-9b96ae031c7a\",\"token\":\"dWDBXuPjccKAslUsdMSK\",\"secret\":\"z8TStKJoRUFzQgHLTlI4\",\"domains\":[\"www.test.com\"],\"mode\":\"test\",\"created_on\":\"2017-06-22T16:00:55.436116+09:00\"}],\"has_more\":false}";

  public static String createStoreAppTokenFakeResponse =
      "{\"id\":\"90389195-ce76-43de-935b-7f1d417d23df\",\"store_id\":\"bf75472e-7f2d-4745-a66d-9b96ae031c7a\",\"token\":\"qjjSz5NdM4MWcqbnH2xd\",\"secret\":\"4XkqM1ZC0mJsQFnIQeHF\",\"domains\":[\"www.test.com\"],\"mode\":\"test\",\"created_on\":\"2017-06-22T16:00:55.436116+09:00\"}";

  public static String updateStoreAppTokenFakeResponse =
      "{\"id\":\"90389195-ce76-43de-935b-7f1d417d23df\",\"store_id\":\"bf75472e-7f2d-4745-a66d-9b96ae031c7a\",\"token\":\"qjjSz5NdM4MWcqbnH2xd\",\"secret\":\"4XkqM1ZC0mJsQFnIQeHF\",\"domains\":[\"www.something.com\",\"www.somethingelse.com\"],\"mode\":\"test\",\"created_on\":\"2017-06-22T16:00:55.436116+09:00\"}";

  public static String createTransactionTokenFakeResponse =
      "{"
          + "\"id\":\"004b391f-1c98-43f8-87de-28b21aaaca00\","
          + "\"store_id\":\"bf75472e-7f2d-4745-a66d-9b96ae031c7a\","
          + "\"mode\":\"test\","
          + "\"metadata\": {\n"
          + "    \"float\": 10.3\n"
          + "},\n"
          + "\"created_on\":\"2017-06-22T16:00:55.436116+09:00\","
          + "\"last_used_on\":null,"
          + "\"payment_type\":\"card\",\n"
          + "\"confirmed\": false,\n"
          + "\"data\":"
          + "{"
          + "\"card\":{"
          + "\"cardholder\":\"full name\","
          + "\"exp_month\":12,"
          + "\"exp_year\":2018,"
          + "\"last_four\":\"5276\","
          + "\"brand\":\"visa\""
          + "},"
          + "\"billing\":{"
          + "\"line1\":\"somewhere\","
          + "\"line2\":null,"
          + "\"state\":null,"
          + "\"city\":\"TYO\","
          + "\"country\":\"JP\","
          + "\"zip\":\"111-1111\""
          + "}"
          + "}"
          + "}";

  public static String createTransactionTokenForCustomerRequest =
      JsonLoader.loadJson("requests/transactiontoken/post-card-with-customer-id.json");

  public static String createTransactionTokenForCustomerResponse =
      "{"
          + "\"id\":\"004b391f-1c98-43f8-87de-28b21aaaca00\","
          + "\"store_id\":\"bf75472e-7f2d-4745-a66d-9b96ae031c7a\","
          + "\"mode\":\"test\","
          + "\"metadata\": {\n"
          + "    \"float\": 10.3,\n"
          + "    \"customer_id\": \"7680e246-2d10-42bf-8bbb-2230e1ed712c\"\n"
          + "},\n"
          + "\"created_on\":\"2017-06-22T16:00:55.436116+09:00\","
          + "\"last_used_on\":null,"
          + "\"payment_type\":\"card\","
          + "\"data\":"
          + "{"
          + "\"card\":{"
          + "\"cardholder\":\"full name\","
          + "\"exp_month\":12,"
          + "\"exp_year\":2018,"
          + "\"last_four\":\"5276\","
          + "\"brand\":\"visa\""
          + "},"
          + "\"billing\":{"
          + "\"line1\":\"somewhere\","
          + "\"line2\":null,"
          + "\"state\":null,"
          + "\"city\":\"TYO\","
          + "\"country\":\"JP\","
          + "\"zip\":\"111-1111\""
          + "}"
          + "}"
          + "}";

  public static String createTransactionTokenFakeRequest =
      "{\"payment_type\":\"card\",\"email\":\"some@email.com\",\"type\":\"one_time\",\"metadata\":{\"float\": 10.3 },\"use_confirmation\": true,\"data\":{\"cardholder\":\"full name\",\"card_number\":\"4556137309615276\",\"exp_month\":12,\"exp_year\":2018,\"cvv\":\"599\",\"line1\":\"somewhere\",\"city\":\"Tokyo\",\"country\":\"JP\",\"zip\":\"111-1111\"}}";

  public static String createTransactionTokenWithoutCVVFakeRequest =
      "{\"payment_type\":\"card\",\"email\":\"some@email.com\",\"type\":\"one_time\",\"metadata\" : { },\"data\":{\"cardholder\":\"full name\",\"card_number\":\"4556137309615276\",\"exp_month\":12,\"exp_year\":2018,\"line1\":\"somewhere\",\"city\":\"Tokyo\",\"country\":\"JP\",\"zip\":\"111-1111\"}}";

  public static String createTransactionTokenWithQrScanFakeResponse =
      "{\n"
          + "  \"id\": \"004b391f-1c98-43f8-87de-28b21aaaca00\",\n"
          + "  \"store_id\": \"bf75472e-7f2d-4745-a66d-9b96ae031c7a\",\n"
          + "  \"mode\": \"test\",\n"
          + "  \"type\": one_time,\n"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "  \"last_used_on\": null,\n"
          + "  \"payment_type\": \"qr_scan\",\n"
          + "  \"data\": {}\n"
          + "}";

  public static String createRecurringTransactionTokenFakeResponse =
      "{"
          + "\"id\":\"004b391f-1c98-43f8-87de-28b21aaaca00\","
          + "\"store_id\":\"bf75472e-7f2d-4745-a66d-9b96ae031c7a\","
          + "\"mode\":\"test\","
          + "\"created_on\":\"2017-06-22T16:00:55.436116+09:00\","
          + "\"last_used_on\":null,"
          + "\"usage_limit\":\"weekly\","
          + "\"payment_type\":\"card\","
          + "\"data\":"
          + "{"
          + "\"card\":{"
          + "\"cardholder\":\"full name\","
          + "\"exp_month\":12,"
          + "\"exp_year\":2018,"
          + "\"last_four\":\"5276\","
          + "\"brand\":\"visa\""
          + "},"
          + "\"billing\":{"
          + "\"line1\":\"somewhere\","
          + "\"line2\":null,"
          + "\"state\":null,"
          + "\"city\":\"TYO\","
          + "\"country\":\"JP\","
          + "\"zip\":\"111-1111\""
          + "}"
          + "}"
          + "}";

  public static String createTransactionTokenWithApplePayFakeResponse =
      "{"
          + "\"id\":\"004b391f-1c98-43f8-87de-28b21aaaca00\","
          + "\"store_id\":\"bf75472e-7f2d-4745-a66d-9b96ae031c7a\","
          + "\"mode\":\"test\","
          + "\"created_on\":\"2017-06-22T16:00:55.436116+09:00\","
          + "\"last_used_on\":null,"
          + "\"payment_type\":\"apple_pay\","
          + "\"data\":"
          + "{"
          + "\"card\":{"
          + "\"cardholder\":\"someperson\","
          + "\"exp_month\":12,"
          + "\"exp_year\":2018,"
          + "\"last_four\":\"5276\","
          + "\"brand\":\"visa\""
          + "},"
          + "\"billing\":{"
          + "\"line1\":\"somewhere\","
          + "\"line2\":null,"
          + "\"state\":null,"
          + "\"city\":\"TYO\","
          + "\"country\":\"JP\","
          + "\"zip\":\"111-1111\""
          + "}"
          + "}"
          + "}";

  public static String createTransactionTokenWithKonbiniPaymentFakeResponse =
      "{"
          + "\"id\":\"004b391f-1c98-43f8-87de-28b21aaaca00\","
          + "\"store_id\":\"bf75472e-7f2d-4745-a66d-9b96ae031c7a\","
          + "\"mode\":\"test\","
          + "\"created_on\":\"2017-06-22T16:00:55.436116+09:00\","
          + "\"last_used_on\":null,"
          + "\"payment_type\":\"konbini\","
          + "\"data\":"
          + "{"
          + "\"customer_name\": \"okyakusama\", "
          + "\"convenience_store\": \"family_mart\", "
          + "\"expiration_period\": \"PT216H\", "
          + "\"phone_number\":{ \"local_number\" : \"4799318900\", \"country_code\" : 55 } "
          + "}"
          + "}";

  public static String createTransactionTokenWithPaidyFakeResponse =
      "{\n"
          + "    \"id\": \"11e8dcf3-1c95-be98-a370-5fb11e03b325\",\n"
          + "    \"store_id\": \"11e8dcdb-52a6-bf5e-b126-277449999f80\",\n"
          + "    \"email\": \"paidy-test@univapay.com\",\n"
          + "    \"payment_type\": \"paidy\",\n"
          + "    \"mode\": \"test\",\n"
          + "    \"type\": \"one_time\",\n"
          + "    \"confirmed\": true,\n"
          + "    \"metadata\": {},\n"
          + "    \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "    \"last_used_on\": null,\n"
          + "    \"data\": {\n"
          + "        \"paidy_token\": \"test-Paidy-token\",\n"
          + "        \"phone_number\": {\n"
          + "            \"country_code\": 81,\n"
          + "            \"local_number\": \"12345678\"\n"
          + "        },\n"
          + "        \"shipping_address\": {\n"
          + "            \"line1\": \"六本木\",\n"
          + "            \"line2\": \"1-1-1\",\n"
          + "            \"city\": \"港区\",\n"
          + "            \"state\": \"東京都\",\n"
          + "            \"zip\": \"106-0032\"\n"
          + "        }\n"
          + "    }\n"
          + "}";

  public static String createNoEmailTransactionTokenWithPaidyFakeResponse =
      "{\n"
          + "    \"id\": \"11e8dcf3-1c95-be98-a370-5fb11e03b325\",\n"
          + "    \"store_id\": \"11e8dcdb-52a6-bf5e-b126-277449999f80\",\n"
          + "    \"email\": null,\n"
          + "    \"payment_type\": \"paidy\",\n"
          + "    \"mode\": \"test\",\n"
          + "    \"type\": \"one_time\",\n"
          + "    \"confirmed\": true,\n"
          + "    \"metadata\": {},\n"
          + "    \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "    \"last_used_on\": null,\n"
          + "    \"data\": {\n"
          + "        \"paidy_token\": \"test-Paidy-token\",\n"
          + "        \"phone_number\": {\n"
          + "            \"country_code\": 81,\n"
          + "            \"local_number\": \"12345678\"\n"
          + "        },\n"
          + "        \"shipping_address\": {\n"
          + "            \"line1\": \"六本木\",\n"
          + "            \"line2\": \"1-1-1\",\n"
          + "            \"city\": \"港区\",\n"
          + "            \"state\": \"東京都\",\n"
          + "            \"zip\": \"106-0032\"\n"
          + "        }\n"
          + "    }\n"
          + "}";

  public static String createNoEmailTransactionTokenWithQrFakeResponse =
      "{\n"
          + "    \"id\": \"11e8dcf3-1c95-be98-a370-5fb11e03b325\",\n"
          + "    \"store_id\": \"11e8dcdb-52a6-bf5e-b126-277449999f80\",\n"
          + "    \"email\": null,\n"
          + "    \"payment_type\": \"qr_scan\",\n"
          + "    \"mode\": \"test\",\n"
          + "    \"type\": \"one_time\",\n"
          + "    \"confirmed\": true,\n"
          + "    \"metadata\": {},\n"
          + "    \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "    \"last_used_on\": null,\n"
          + "    \"data\": {\n"
          + "        \"gateway\": \"alipay\"\n"
          + "    }"
          + "}";

  public static String createNoEmailTransactionTokenWithQrMerchantFakeResponse =
      "{\n"
          + "    \"id\": \"11e8dcf3-1c95-be98-a370-5fb11e03b325\",\n"
          + "    \"store_id\": \"11e8dcdb-52a6-bf5e-b126-277449999f80\",\n"
          + "    \"email\": null,\n"
          + "    \"payment_type\": \"qr_merchant\",\n"
          + "    \"mode\": \"test\",\n"
          + "    \"type\": \"one_time\",\n"
          + "    \"confirmed\": true,\n"
          + "    \"metadata\": {},\n"
          + "    \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "    \"last_used_on\": null,\n"
          + "    \"data\": {\n"
          + "        \"qr_image_url\": \"http://qr-image.png\",\n"
          + "        \"brand\": \"alipay_merchant_qr\"\n"
          + "    }"
          + "}";

  public static String createTransactionTokenWithQrMerchantFakeResponse =
      "{\n"
          + "    \"id\": \"11e8dcf3-1c95-be98-a370-5fb11e03b325\",\n"
          + "    \"store_id\": \"11e8dcdb-52a6-bf5e-b126-277449999f80\",\n"
          + "    \"email\": \"test@univapay.com\",\n"
          + "    \"payment_type\": \"qr_merchant\",\n"
          + "    \"mode\": \"test\",\n"
          + "    \"type\": \"one_time\",\n"
          + "    \"confirmed\": true,\n"
          + "    \"metadata\": {},\n"
          + "    \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "    \"last_used_on\": null,\n"
          + "    \"data\": {\n"
          + "        \"qr_image_url\": \"http://qr-image.png\",\n"
          + "        \"brand\": \"alipay_merchant_qr\"\n"
          + "    }"
          + "}";

  public static String createNoEmailTransactionTokenWithOnlinePaymentFakeResponse =
      JsonLoader.loadJson("responses/transactiontoken/post-online-no-email.json");
  public static String createNoEmailTransactionTokenWithOnlinePaymentFakeRequest =
      JsonLoader.loadJson("requests/transactiontoken/post-online-no-email.json");

  public static String createFullTransactionTokenWithOnlinePaymentFakeResponse =
      JsonLoader.loadJson("responses/transactiontoken/post-online-full.json");
  public static String createFullTransactionTokenWithOnlinePaymentFakeRequest =
      JsonLoader.loadJson("requests/transactiontoken/post-online-full.json");

  public static String createRecurringTransactionTokenFakeRequest =
      "{\"payment_type\":\"card\",\"email\":\"some@email.com\",\"type\":\"recurring\", \"metadata\" : { }, \"usage_limit\":\"weekly\" ,\"data\":{\"cardholder\":\"full name\",\"card_number\":\"4556137309615276\",\"exp_month\":12,\"exp_year\":2018,\"cvv\":599,\"line1\":\"somewhere\",\"city\":\"Tokyo\",\"country\":\"JP\",\"zip\":\"111-1111\"}}";

  public static String createTransactionTokenWithApplePayFakeRequest =
      "{\"payment_type\":\"apple_pay\",\"email\":\"some@email.com\",\"type\":\"one_time\" ,\"metadata\" : { }, \"data\":{\"cardholder\":\"someperson\",\"applepay_token\":\"sometoken\",\"line1\":\"somewhere\",\"city\":\"Tokyo\",\"country\":\"JP\",\"zip\":\"111-1111\"}}";

  public static String createTransactionTokenWithKonbiniPaymentFakeRequest =
      "{\"payment_type\":\"konbini\",\"email\":\"some@email.com\",\"type\":\"one_time\" ,\"metadata\" : { },\"data\":{\"customer_name\" : \"okyakusama\", \"convenience_store\" : \"family_mart\", \"expiration_period\" : \"PT216H\", \"phone_number\":{ \"local_number\" : \"4799318900\", \"country_code\" : 55 }}}";

  public static String createTransactionTokenWithKonbiniPaymentNoExpirationFakeRequest =
      "{\"payment_type\":\"konbini\",\"email\":\"some@email.com\",\"type\":\"one_time\" ,\"metadata\" : { },\"data\":{\"customer_name\" : \"okyakusama\", \"convenience_store\" : \"family_mart\", \"phone_number\":{ \"local_number\" : \"4799318900\", \"country_code\" : 55 }}}";

  public static String createTransactionTokenWithQrScanFakeRequest =
      "{\"payment_type\":\"qr_scan\",\"email\":\"some@email.com\",\"type\":\"one_time\",\"metadata\" : { },\"data\":{\"scanned_qr\":\"oiajsdfipojasdfipas\"}}";

  public static String createTransactionTokenWithPaidyFakeRequest =
      "{\n"
          + "    \"payment_type\": \"paidy\",\n"
          + "    \"email\": \"paidy-test@univapay.com\",\n"
          + "    \"type\": \"one_time\",\n"
          + "    \"metadata\" : { },"
          + "    \"data\": {\n"
          + "        \"paidy_token\": \"test-Paidy-token\",\n"
          + "        \"phone_number\": {\n"
          + "            \"local_number\": \"12345678\",\n"
          + "            \"country_code\": 81\n"
          + "        },\n"
          + "        \"shipping_address\": {\n"
          + "            \"line1\": \"六本木\",\n"
          + "            \"line2\": \"1-1-1\",\n"
          + "            \"city\": \"港区\",\n"
          + "            \"state\": \"東京都\",\n"
          + "            \"zip\": \"106-0032\"\n"
          + "        }\n"
          + "    }\n"
          + "}";

  public static String createNoEmailTransactionTokenWithPaidyFakeRequest =
      "{\n"
          + "    \"payment_type\": \"paidy\",\n"
          + "    \"type\": \"one_time\",\n"
          + "    \"metadata\" : { },"
          + "    \"data\": {\n"
          + "        \"paidy_token\": \"test-Paidy-token\",\n"
          + "        \"phone_number\": {\n"
          + "            \"local_number\": \"12345678\",\n"
          + "            \"country_code\": 81\n"
          + "        },\n"
          + "        \"shipping_address\": {\n"
          + "            \"line1\": \"六本木\",\n"
          + "            \"line2\": \"1-1-1\",\n"
          + "            \"city\": \"港区\",\n"
          + "            \"state\": \"東京都\",\n"
          + "            \"zip\": \"106-0032\"\n"
          + "        }\n"
          + "    }\n"
          + "}";

  public static String createNoEmailTransactionTokenWithQrFakeRequest =
      "{\n"
          + "    \"payment_type\":\"qr_scan\",\n"
          + "    \"type\":\"one_time\",\n"
          + "    \"metadata\" : { },"
          + "    \"data\":{\n"
          + "        \"scanned_qr\":\"2088234789789462\"\n"
          + "    }\n"
          + "}";

  public static String createTransactionTokenWithQrMerchantFakeRequest =
      "{\n"
          + "    \"payment_type\": \"qr_merchant\",\n"
          + "    \"email\": \"test@univapay.com\",\n"
          + "    \"type\": \"one_time\",\n"
          + "    \"metadata\" : { },"
          + "    \"data\": {\n"
          + "        \"brand\": \"alipay_merchant_qr\"\n"
          + "        }\n"
          + "    }\n"
          + "}";

  public static String createNoEmailTransactionTokenWithQrMerchantFakeRequest =
      "{\n"
          + "    \"payment_type\": \"qr_merchant\",\n"
          + "    \"type\": \"one_time\",\n"
          + "    \"metadata\" : { },"
          + "    \"data\": {\n"
          + "        \"brand\": \"alipay_merchant_qr\"\n"
          + "        }\n"
          + "    }\n"
          + "}";

  public static String getTransactionTokenFakeResponse =
      JsonLoader.loadJson("responses/transactiontoken/get-card-full.json");

  public static String getTransactionTokenCvvAuthPendingResponse =
      JsonLoader.loadJson("responses/transactiontoken/get-card-cvv-auth-pending.json");
  public static String getTransactionTokenCvvAuthCurrentResponse =
      JsonLoader.loadJson("responses/transactiontoken/get-card-cvv-auth-current.json");

  public static String getTransactionTokenFakeResponseCardBrand =
      "{"
          + "  \"id\":\"004b391f-1c98-43f8-87de-28b21aaaca00\","
          + "  \"store_id\":\"bf75472e-7f2d-4745-a66d-9b96ae031c7a\","
          + "  \"mode\":\"live\","
          + "  \"type\":\"one_time\","
          + "  \"created_on\":\"2017-06-22T16:00:55.436116+09:00\","
          + "  \"payment_type\":\"card\","
          + "  \"data\":{"
          + "    \"card\":{"
          + "      \"cardholder\":\"full name\","
          + "      \"exp_month\":12,"
          + "      \"exp_year\":2018,"
          + "      \"last_four\":\"5276\","
          + "      \"brand\":\"illegal card brand\","
          + "      \"category\": \"classic\",\n"
          + "      \"issuer\": \"test issuer\",\n"
          + "      \"sub_brand\": \"none\""
          + "    },"
          + "    \"billing\":{"
          + "      \"line1\":\"somewhere\","
          + "      \"line2\":null,"
          + "      \"state\":null,"
          + "      \"city\":\"TYO\","
          + "      \"country\":\"JP\","
          + "      \"zip\":\"111-1111\""
          + "    }"
          + "  }"
          + "}";

  public static String listTransactionTokenFakeResponse =
      "{\n"
          + "    \"has_more\": false, \n"
          + "    \"items\": [\n"
          + "        {\n"
          + "            \"active\": true, \n"
          + "            \"created_on\": \"2017-08-24T02:23:53.000000+09:00\", \n"
          + "            \"id\": \"11e78873-45e4-5046-862b-a7a5e83cd63c\", \n"
          + "            \"mode\": \"test\", \n"
          + "            \"payment_type\": \"card\", \n"
          + "            \"store_id\": \"11e786da-4714-5028-8280-bb9bc7cf54e9\", \n"
          + "            \"type\": \"recurring\"\n"
          + "        }, \n"
          + "        {\n"
          + "            \"active\": true, \n"
          + "            \"created_on\": \"2017-08-24T02:04:31.000000+09:00\", \n"
          + "            \"id\": \"11e78870-912b-d69e-9cb4-ff984d158c41\", \n"
          + "            \"mode\": \"test\", \n"
          + "            \"payment_type\": \"card\", \n"
          + "            \"store_id\": \"11e786da-4714-5028-8280-bb9bc7cf54e9\", \n"
          + "            \"type\": \"recurring\"\n"
          + "        }\n"
          + "    ]\n"
          + "}";

  public static String updateTransactionTokenFakeResponse =
      "{"
          + "\"id\":\"004b391f-1c98-43f8-87de-28b21aaaca00\","
          + "\"store_id\":\"bf75472e-7f2d-4745-a66d-9b96ae031c7a\","
          + "\"email\": \"test@email.com\","
          + "\"payment_type\":\"card\","
          + "\"mode\":\"test\","
          + "\"metadata\": {\n"
          + "    \"float\": 10.3\n"
          + "},\n"
          + "\"created_on\":\"2017-06-22T16:00:55.436116+09:00\","
          + "\"last_used_on\":null,"
          + "\"data\":"
          + "{"
          + "\"card\":{"
          + "\"cardholder\":\"full name\","
          + "\"exp_month\":12,"
          + "\"exp_year\":2018,"
          + "\"last_four\":\"5276\","
          + "\"brand\":\"visa\""
          + "},"
          + "\"billing\":{"
          + "\"line1\":\"somewhere\","
          + "\"line2\":null,"
          + "\"state\":null,"
          + "\"city\":\"TYO\","
          + "\"country\":\"JP\","
          + "\"zip\":\"111-1111\""
          + "}"
          + "}"
          + "}";

  public static String updateNoMailTransactionTokenFakeResponse =
      "{\n"
          + "  \"id\": \"004b391f-1c98-43f8-87de-28b21aaaca00\",\n"
          + "  \"store_id\": \"bf75472e-7f2d-4745-a66d-9b96ae031c7a\",\n"
          + "  \"email\": null,\n"
          + "  \"payment_type\": \"card\",\n"
          + "  \"mode\": \"test\",\n"
          + "  \"metadata\": {\n"
          + "    \"float\": 10.3\n"
          + "  },\n"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "  \"last_used_on\": null,\n"
          + "  \"data\": {\n"
          + "    \"card\":{\n"
          + "      \"cardholder\": \"full name\",\n"
          + "      \"exp_month\": 12,\n"
          + "      \"exp_year\": 2018,\n"
          + "      \"last_four\": \"5276\",\n"
          + "      \"brand\": \"visa\"\n"
          + "    },\n"
          + "      \"billing\": {\n "
          + "      \"line1\": \"somewhere\",\n"
          + "      \"line2\": null,\n"
          + "      \"state\": null,\n"
          + "      \"city\": \"TYO\",\n"
          + "      \"country\": \"JP\",\n"
          + "      \"zip\": \"111-1111\"\n"
          + "    }\n"
          + "  }\n"
          + "}";

  public static String updateTransactionTokenFakeRequest =
      "{\n"
          + "  \"email\": \"test@email.com\",\n"
          + "  \"metadata\": {\n"
          + "      \"float\": 10.3\n"
          + "  },\n"
          + "  \"data\" : {\n"
          + "    \"cvv\" : 123\n"
          + "  }\n"
          + "}";

  public static String updateTransactionTokenMetadataFakeRequest =
      "{\n" + "  \"metadata\": {\n" + "      \"float\": 10.3\n" + "  }\n" + "}";

  public static String updateNoMailTransactionTokenFakeRequest =
      "{\n" + "  \"email\": \"\",\n" + "  \"metadata\" : {}}";

  public static String confirmTransactionTokenFakeRequest =
      "{\n" + "    \"confirmation_code\": \"1111\"\n" + "}";

  public static String confirmTransactionTokenFakeResponse =
      "{\n"
          + "    \"id\": \"11e8ed6c-c920-994c-ae3c-5324be320d7c\",\n"
          + "    \"platform_id\": \"11e8ed6a-2af0-b8e4-b8e6-df5dc02b4c11\",\n"
          + "    \"merchant_id\": \"11e8ed6a-35e0-af02-ae3c-a715a54d6c81\",\n"
          + "    \"store_id\": \"11e8ed6b-6237-578a-991b-57a25a9c1725\",\n"
          + "    \"email\": \"successful.payment@fake-paidy.com\",\n"
          + "    \"payment_type\": \"paidy\",\n"
          + "    \"active\": true,\n"
          + "    \"mode\": \"test\",\n"
          + "    \"type\": \"one_time\",\n"
          + "    \"confirmed\": true,\n"
          + "    \"metadata\": {},\n"
          + "    \"created_on\": \"2018-11-21T09:06:52.132667Z\",\n"
          + "    \"last_used_on\": null,\n"
          + "    \"data\": {\n"
          + "        \"paidy_token\": \"fyghjk\",\n"
          + "        \"phone_number\": {\n"
          + "            \"country_code\": 81,\n"
          + "            \"local_number\": \"8000000003\"\n"
          + "        }\n"
          + "    }\n"
          + "}";

  public static String getCheckoutInfoFakeResponse =
      JsonLoader.loadJson("responses/checkoutInfo/get-checkout-info.json");

  public static String listMerchantAppJWTFakeResponse =
      "{\n"
          + "  \"has_more\" : false,\n"
          + "  \"items\" : [ {\n"
          + "    \"merchant_id\" : \"11e83951-b3c5-d3ac-8d91-5be6827e5e4c\",\n"
          + "    \"creator_id\" : \"11e83951-b3c5-d3ac-8d91-5be6827e5e4c\",\n"
          + "    \"jti\" : \"11e83951-b2fb-06de-9ee2-ff98594e423b\",\n"
          + "    \"jwt\" : \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcHBfdG9rZW4iLCJpYXQiOjE1MjI5ODgzNjksIm1lcmNoYW50X2lkIjoiMTFlODM5NTEtYjNjNS1kM2FjLThkOTEtNWJlNjgyN2U1ZTRjIiwic3RvcmVfaWQiOiIxMWU4Mzk1MS1iM2NhLWNjMmMtOTRhZS0wN2E2NTQ3NjJiOGMiLCJkb21haW5zIjpbXSwibW9kZSI6ImxpdmVfdGVzdCIsImNyZWF0b3JfaWQiOiIxMWU4Mzk1MS1iM2M1LWQzYWMtOGQ5MS01YmU2ODI3ZTVlNGMiLCJ2ZXJzaW9uIjoxLCJqdGkiOiIxMWU4Mzk1MS1iMmZiLTA2ZGUtOWVlMi1mZjk4NTk0ZTQyM2IifQ.YNIYpOo4DRiGy4t8PyE99qE0JupV1C3gw4K2aIw9rCU\",\n"
          + "    \"created_on\" : \"2018-04-06T13:19:30.639099+09:00\"\n"
          + "  }, {\n"
          + "    \"merchant_id\" : \"11e83951-b3c5-d3ac-8d91-5be6827e5e4c\",\n"
          + "    \"creator_id\" : \"11e83951-b3b6-452c-8d91-e3186e432c7d\",\n"
          + "    \"jti\" : \"11e83951-b2f8-6ecb-9ee2-01546294b9d3\",\n"
          + "    \"jwt\" : \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcHBfdG9rZW4iLCJpYXQiOjE1MjI5ODgzNjksIm1lcmNoYW50X2lkIjoiMTFlODM5NTEtYjNjNS1kM2FjLThkOTEtNWJlNjgyN2U1ZTRjIiwiY3JlYXRvcl9pZCI6IjExZTgzOTUxLWIzYjYtNDUyYy04ZDkxLWUzMTg2ZTQzMmM3ZCIsInZlcnNpb24iOjEsImp0aSI6IjExZTgzOTUxLWIyZjgtNmVjYi05ZWUyLTAxNTQ2Mjk0YjlkMyJ9.2imhH1TuChZFImdTCqiz5ZLr21AGon2ptuhN851wDa4\",\n"
          + "    \"created_on\" : \"2018-04-06T13:19:30.622559+09:00\"\n"
          + "  }, {\n"
          + "    \"merchant_id\" : \"11e83951-b3c5-d3ac-8d91-5be6827e5e4c\",\n"
          + "    \"creator_id\" : \"11e83951-b34c-56bc-b01c-1b22d721b2ca\",\n"
          + "    \"jti\" : \"11e83951-b2f7-846a-9ee2-09166253975e\",\n"
          + "    \"jwt\" : \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcHBfdG9rZW4iLCJpYXQiOjE1MjI5ODgzNjksIm1lcmNoYW50X2lkIjoiMTFlODM5NTEtYjNjNS1kM2FjLThkOTEtNWJlNjgyN2U1ZTRjIiwiY3JlYXRvcl9pZCI6IjExZTgzOTUxLWIzNGMtNTZiYy1iMDFjLTFiMjJkNzIxYjJjYSIsInZlcnNpb24iOjEsImp0aSI6IjExZTgzOTUxLWIyZjctODQ2YS05ZWUyLTA5MTY2MjUzOTc1ZSJ9.V3r1khlmYMjIRqyAafS3wJTEfk2h21PPZgH24DHcrEs\",\n"
          + "    \"created_on\" : \"2018-04-06T13:19:30.616268+09:00\"\n"
          + "  }, {\n"
          + "    \"merchant_id\" : \"11e83951-b3c5-d3ac-8d91-5be6827e5e4c\",\n"
          + "    \"creator_id\" : \"11e83951-b3c5-d3ac-8d91-5be6827e5e4c\",\n"
          + "    \"jti\" : \"11e83951-b2f6-e829-9ee2-f999d444b611\",\n"
          + "    \"jwt\" : \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcHBfdG9rZW4iLCJpYXQiOjE1MjI5ODgzNjksIm1lcmNoYW50X2lkIjoiMTFlODM5NTEtYjNjNS1kM2FjLThkOTEtNWJlNjgyN2U1ZTRjIiwiY3JlYXRvcl9pZCI6IjExZTgzOTUxLWIzYzUtZDNhYy04ZDkxLTViZTY4MjdlNWU0YyIsInZlcnNpb24iOjEsImp0aSI6IjExZTgzOTUxLWIyZjYtZTgyOS05ZWUyLWY5OTlkNDQ0YjYxMSJ9.AIszCNgURKZZfL6gokR2BBrexqpg_BKykzHBi7Jl8sA\",\n"
          + "    \"created_on\" : \"2018-04-06T13:19:30.611679+09:00\"\n"
          + "  } ]\n"
          + "}";

  public static String listStoreAppJWTFakeResponse =
      "{\n"
          + "  \"has_more\" : false,\n"
          + "  \"items\" : [ {\n"
          + "    \"merchant_id\" : \"11e83951-b3c5-d3ac-8d91-5be6827e5e4c\",\n"
          + "    \"store_id\" : \"11e83951-b3ca-cc2c-94ae-07a654762b8c\",\n"
          + "    \"creator_id\" : \"11e83951-b3c5-d3ac-8d91-5be6827e5e4c\",\n"
          + "    \"jti\" : \"11e83951-b2fb-06de-9ee2-ff98594e423b\",\n"
          + "    \"jwt\" : \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcHBfdG9rZW4iLCJpYXQiOjE1MjI5ODgzNjksIm1lcmNoYW50X2lkIjoiMTFlODM5NTEtYjNjNS1kM2FjLThkOTEtNWJlNjgyN2U1ZTRjIiwic3RvcmVfaWQiOiIxMWU4Mzk1MS1iM2NhLWNjMmMtOTRhZS0wN2E2NTQ3NjJiOGMiLCJkb21haW5zIjpbXSwibW9kZSI6ImxpdmVfdGVzdCIsImNyZWF0b3JfaWQiOiIxMWU4Mzk1MS1iM2M1LWQzYWMtOGQ5MS01YmU2ODI3ZTVlNGMiLCJ2ZXJzaW9uIjoxLCJqdGkiOiIxMWU4Mzk1MS1iMmZiLTA2ZGUtOWVlMi1mZjk4NTk0ZTQyM2IifQ.YNIYpOo4DRiGy4t8PyE99qE0JupV1C3gw4K2aIw9rCU\",\n"
          + "    \"created_on\" : \"2018-04-06T13:19:30.639099+09:00\"\n"
          + "  }"
          + "]\n"
          + "}";

  public static String createMerchantAppJWTFakeResponse =
      "{\n"
          + "    \"merchant_id\": \"11e83c6c-14db-30de-b9a5-17a790ae22a2\",\n"
          + "    \"creator_id\": \"11e83c6c-14db-30de-b9a5-17a790ae22a2\",\n"
          + "    \"roles\": [\n"
          + "        \"merchant\"\n"
          + "    ],\n"
          + "    \"jti\": \"11e83c70-ce67-e858-b28f-1dc0c2892b77\",\n"
          + "    \"secret\": \"m4YkAf3B4A0h6JDeouN1\",\n"
          + "    \"jwt\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcHBfdG9rZW4iLCJpYXQiOjE1MjMzMzE1ODMsIm1lcmNoYW50X2lkIjoiMTFlODNjNmMtMTRkYi0zMGRlLWI5YTUtMTdhNzkwYWUyMmEyIiwicm9sZXMiOlsibWVyY2hhbnQiXSwiY3JlYXRvcl9pZCI6IjExZTgzYzZjLTE0ZGItMzBkZS1iOWE1LTE3YTc5MGFlMjJhMiIsInZlcnNpb24iOjEsImp0aSI6IjExZTgzYzcwLWNlNjctZTg1OC1iMjhmLTFkYzBjMjg5MmI3NyJ9.6VKtNDfvFuiuaW6wRtPThRCA3w4v4AQJeKUHL0Ly8ZU\",\n"
          + "    \"created_on\": \"2018-04-10T12:39:45.408204+09:00\"\n"
          + "}";

  public static String createMerchantAppJWTFakeRequest =
      "{\n" + "	\"roles\": [\"merchant\"]\n" + "}";

  public static String createStoreAppJWTFakeResponse =
      "{\n"
          + "    \"merchant_id\": \"11e83c6c-14db-30de-b9a5-17a790ae22a2\",\n"
          + "    \"store_id\": \"11e83c6c-16a4-e7a2-b9a5-cfab31f2d2e5\",\n"
          + "    \"creator_id\": \"11e83c6c-14db-30de-b9a5-17a790ae22a2\",\n"
          + "    \"jti\": \"11e83c6f-8587-4002-b28f-abc9bcd96cd3\",\n"
          + "    \"secret\": \"FWCnurxE1dOKUZ4oAuTz\",\n"
          + "    \"jwt\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcHBfdG9rZW4iLCJpYXQiOjE1MjMzMzEwMzEsIm1lcmNoYW50X2lkIjoiMTFlODNjNmMtMTRkYi0zMGRlLWI5YTUtMTdhNzkwYWUyMmEyIiwic3RvcmVfaWQiOiIxMWU4M2M2Yy0xNmE0LWU3YTItYjlhNS1jZmFiMzFmMmQyZTUiLCJkb21haW5zIjpbIioiXSwibW9kZSI6InRlc3QiLCJjcmVhdG9yX2lkIjoiMTFlODNjNmMtMTRkYi0zMGRlLWI5YTUtMTdhNzkwYWUyMmEyIiwidmVyc2lvbiI6MSwianRpIjoiMTFlODNjNmYtODU4Ny00MDAyLWIyOGYtYWJjOWJjZDk2Y2QzIn0.mXXUWcbmBuIe3xCQSEjzMd7kTwQRsOW05yd9mE6gbwo\",\n"
          + "    \"created_on\": \"2018-04-10T12:30:33.284708+09:00\"\n"
          + "}";

  public static String createStoreAppJWTFakeRequest =
      "{\n" + "\"mode\":\"test\",\n" + "\"domains\":[\"*\"]\n" + "}";

  public static String createCustomerIdFakeRequest =
      "{\n" + "    \"customer_id\": \"sdfiosjdfoisfapsdiofuopszaidfu\"\n" + "}";

  public static String createCustomerIdFakeResponse =
      "{\n" + "    \"customer_id\": \"004a7593-8071-439a-8000-788a79579ac4\"\n" + "}";

  public static String getNoEmailTransactionTokenWithOnlinePaymentFakeResponse =
      JsonLoader.loadJson("responses/transactiontoken/get-online-no-email-full.json");

  public static String createTransactionTokenWithQrMerchantAlipayConnectFakeResponse =
      JsonLoader.loadJson("responses/transactiontoken/create-alipay-connect-mpm.json");

  public static String createTransactionTokenWithQrMerchantAlipayConnectFakeRequest =
      JsonLoader.loadJson("requests/transactiontoken/create-alipay-connect-mpm.json");

  public static String createCardTransactionTokenFullResponse =
      JsonLoader.loadJson("responses/transactiontoken/post-card-full.json");

  public static String createCardTransactionTokenFullRequest =
      JsonLoader.loadJson("requests/transactiontoken/post-card-full.json");
}
