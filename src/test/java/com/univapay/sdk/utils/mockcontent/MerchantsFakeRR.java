package com.univapay.sdk.utils.mockcontent;

public class MerchantsFakeRR {

  public static String getMerchantVerificationFakeResponse =
      "{\n"
          + "  \"id\": \"0198f0a2-d5f7-4888-817d-947cc44250ed\",\n"
          + "  \"homepage_url\": \"http://www.montavistatedx.com\",\n"
          + "  \"company_description\": \"Coast Guard\",\n"
          + "  \"company_contact_info\": {\n"
          + "    \"name\": \"Ivory R. Grace\",\n"
          + "    \"company_name\": \"Pomeroy's\",\n"
          + "    \"phone_number\": {\"country_code\": 503, \"local_number\":\"701-268-0116\"},\n"
          + "    \"line1\": \"3124 Catherine Drive\",\n"
          + "    \"line2\": null,\n"
          + "    \"state\": \"ND\",\n"
          + "    \"city\": \"Maxbass\",\n"
          + "    \"country\": \"US\",\n"
          + "    \"zip\": \"58760\"\n"
          + "  },\n"
          + "  \"business_type\": \"digital_sales\",\n"
          + "  \"system_manager_name\": \"Rachael R. Simon\",\n"
          + "  \"system_manager_number\": {\"country_code\": 503, \"local_number\":\"540-220-8465\"},\n"
          + "  \"system_manager_email\": \"RachaelRSimon@dayrep.com\",\n"
          + "  \"recurring_token_request\":\"infinite\",\n"
          + "  \"recurring_token_request_reason\":\"testing\",\n"
          + "  \"allow_empty_cvv\":true,"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "  \"updated_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "}";

  public static String postMerchantVerificationFakeRequest =
      "{\n"
          + "  \"homepage_url\": \"http://www.montavistatedx.com\",\n"
          + "  \"company_description\": \"Coast Guard\",\n"
          + "  \"company_contact_info\": {\n"
          + "    \"name\": \"Ivory R. Grace\",\n"
          + "    \"company_name\": \"Pomeroy's\",\n"
          + "    \"phone_number\": {\"country_code\": 503, \"local_number\":\"701-268-0116\"},\n"
          + "    \"line1\": \"3124 Catherine Drive\",\n"
          + "    \"line2\": \"\",\n"
          + "    \"state\": \"ND\",\n"
          + "    \"city\": \"Maxbass\",\n"
          + "    \"country\": \"US\",\n"
          + "    \"zip\": \"58760\"\n"
          + "  },\n"
          + "  \"business_type\": \"digital_sales\",\n"
          + "  \"recurring_token_request\":\"infinite\",\n"
          + "  \"recurring_token_request_reason\":\"testing\",\n"
          + "  \"allow_empty_cvv\":true,"
          + "  \"system_manager_name\": \"Rachael R. Simon\",\n"
          + "  \"system_manager_number\":  {\"country_code\": 503, \"local_number\":\"540-220-8465\"},\n"
          + "  \"system_manager_email\": \"RachaelRSimon@dayrep.com\"\n"
          + "}";

  public static String postMerchantVerificationFakeResponse =
      "{\n"
          + "  \"id\": \"0198f0a2-d5f7-4888-817d-947cc44250ed\",\n"
          + "  \"homepage_url\": \"http://www.montavistatedx.com\",\n"
          + "  \"company_description\": \"Coast Guard\",\n"
          + "  \"company_contact_info\": {\n"
          + "    \"name\": \"Ivory R. Grace\",\n"
          + "    \"company_name\": \"Pomeroy's\",\n"
          + "    \"phone_number\": {\"country_code\": 503, \"local_number\":\"701-268-0116\"},\n"
          + "    \"line1\": \"3124 Catherine Drive\",\n"
          + "    \"line2\": null,\n"
          + "    \"state\": \"ND\",\n"
          + "    \"city\": \"Maxbass\",\n"
          + "    \"country\": \"US\",\n"
          + "    \"zip\": \"58760\"\n"
          + "  },\n"
          + "  \"business_type\": \"digital_sales\",\n"
          + "  \"system_manager_name\": \"Rachael R. Simon\",\n"
          + "  \"system_manager_number\":  {\"country_code\": 503, \"local_number\":\"540-220-8465\"},\n"
          + "  \"system_manager_email\": \"RachaelRSimon@dayrep.com\",\n"
          + "  \"recurring_token_request\":\"infinite\",\n"
          + "  \"recurring_token_request_reason\":\"testing\",\n"
          + "  \"allow_empty_cvv\":true,"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "  \"updated_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "}";

  public static String updateMerchantVerificationFakeRequest =
      "{\n"
          + "  \"homepage_url\": \"http://www.montavistatedx.com/updated\",\n"
          + "  \"company_description\": \"Coast Guard Updated\",\n"
          + "  \"recurring_token_request\":\"bounded\",\n"
          + "  \"recurring_token_request_reason\":\"updating\",\n"
          + "  \"allow_empty_cvv\":false,"
          + "  \"system_manager_email\": \"RachaelRSimon@dayrep.com\",\n"
          + "  \"company_contact_info\": {\n"
          + "    \"name\": \"Ivory R. Grace Updated\",\n"
          + "    \"company_name\": \"Pomeroy's Updated\",\n"
          + "    \"phone_number\": {\"country_code\": 503, \"local_number\":\"701-268-1160\"},\n"
          + "    \"country\" : \"JP\",\n"
          + "    \"line1\": \"3124 Catherine Drive Updated\",\n"
          + "    \"line2\": \"address line 2 Updated\"\n"
          + "  }\n"
          + "}";

  public static String updateMerchantVerificationWithEmptyEmailFakeRequest =
      "{\n" + "  \"system_manager_email\": \"\"\n" + "}";

  public static String updateMerchantVerificationFakeResponse =
      "{\n"
          + "  \"id\": \"0198f0a2-d5f7-4888-817d-947cc44250ed\",\n"
          + "  \"homepage_url\": \"http://www.montavistatedx.com/updated\",\n"
          + "  \"company_description\": \"Coast Guard Updated\",\n"
          + "  \"company_contact_info\": {\n"
          + "    \"name\": \"Ivory R. Grace Updated\",\n"
          + "    \"company_name\": \"Pomeroy's Updated\",\n"
          + "    \"phone_number\": {\"country_code\": 503, \"local_number\":\"701-268-1160\"},\n"
          + "    \"line1\": \"3124 Catherine Drive Updated\",\n"
          + "    \"line2\": \"address line 2 Updated\",\n"
          + "    \"state\": \"WA\",\n"
          + "    \"city\": \"Seattle\",\n"
          + "    \"country\": \"JP\",\n"
          + "    \"zip\": \"98777\"\n"
          + "  },\n"
          + "  \"business_type\": \"cosmetics\",\n"
          + "  \"system_manager_name\": \"Rachael R. Simon\",\n"
          + "  \"system_manager_number\": \"540-220-8465\",\n"
          + "  \"system_manager_email\": \"RachaelRSimon@dayrep.com\",\n"
          + "  \"recurring_token_request\":\"bounded\",\n"
          + "  \"recurring_token_request_reason\":\"updating\",\n"
          + "  \"allow_empty_cvv\":false,"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "  \"updated_on\": \"2017-06-22T16:00:59.436116+09:00\"\n"
          + "}";

  public static String updateMerchantVerificationWithEmptyEmailFakeResponse =
      "{\n"
          + "  \"id\": \"0198f0a2-d5f7-4888-817d-947cc44250ed\",\n"
          + "  \"homepage_url\": \"http://www.montavistatedx.com/updated\",\n"
          + "  \"company_description\": \"Coast Guard Updated\",\n"
          + "  \"company_contact_info\": {\n"
          + "    \"name\": \"Ivory R. Grace Updated\",\n"
          + "    \"company_name\": \"Pomeroy's Updated\",\n"
          + "    \"phone_number\": {\"country_code\": 503, \"local_number\":\"701-268-1160\"},\n"
          + "    \"line1\": \"3124 Catherine Drive Updated\",\n"
          + "    \"line2\": \"address line 2 Updated\",\n"
          + "    \"state\": \"WA\",\n"
          + "    \"city\": \"Seattle\",\n"
          + "    \"country\": \"JP\",\n"
          + "    \"zip\": \"98777\"\n"
          + "  },\n"
          + "  \"business_type\": \"cosmetics\",\n"
          + "  \"system_manager_name\": \"Rachael R. Simon\",\n"
          + "  \"system_manager_number\": \"540-220-8465\",\n"
          + "  \"system_manager_email\": null,\n"
          + "  \"recurring_token_request\":\"bounded\",\n"
          + "  \"recurring_token_request_reason\":\"updating\",\n"
          + "  \"allow_empty_cvv\":false,"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "  \"updated_on\": \"2017-06-22T16:00:59.436116+09:00\"\n"
          + "}";

  public static String getMerchantTransactionHistoryFakeResponse =
      "{\n"
          + "  \"items\": [\n"
          + "    {\n"
          + "      \"resource_id\": \"63d3e4ad-ca05-435a-82ca-addb276cb01b\",\n"
          + "      \"store_id\": \"7ce26c3f-cf47-41d4-b7fe-ba373d00fc07\",\n"
          + "      \"amount\": 15,\n"
          + "      \"amount_currency\": \"JPY\",\n"
          + "      \"transaction_type\": \"refund\",\n"
          + "      \"created_on\": 2017-06-22T16:00:55.436116+09:00\n"
          + "    },\n"
          + "    {\n"
          + "      \"resource_id\": \"d2aecb69-7d8b-42b1-802f-534197797ba2\",\n"
          + "      \"store_id\": \"7ce26c3f-cf47-41d4-b7fe-ba373d00fc07\",\n"
          + "      \"amount\": 1000,\n"
          + "      \"amount_currency\": \"JPY\",\n"
          + "      \"transaction_type\": \"charge\",\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\"\n"
          + "    }\n"
          + "  ],\n"
          + "  \"has_more\": false\n"
          + "}";

  public static String getStoreTransactionHistoryFakeResponse =
      "{\n"
          + "  \"items\": [\n"
          + "    {\n"
          + "      \"store_id\": \"45facc11-efc8-4156-8ef3-e363a70a54c3\",\n"
          + "      \"resource_id\": \"e1771339-b989-4a43-99c1-5e35d8008426\",\n"
          + "      \"charge_id\": \"e1771339-b989-4a43-99c1-5e35d8008426\",\n"
          + "      \"amount\": 50000,\n"
          + "      \"currency\": \"JPY\",\n"
          + "      \"amount_formatted\": 50000,\n"
          + "      \"type\": \"charge\",\n"
          + "      \"status\": \"failed\",\n"
          + "      \"metadata\": {\n"
          + "          \"float\": 10.3\n"
          + "      },\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "      \"mode\": \"test\",\n"
          + "      \"merchant_name\": \"merchant_name\",\n"
          + "      \"user_data\": {\n"
          + "          \"card_brand\": \"mastercard\",\n"
          + "          \"type\": \"charge\",\n"
          + "          \"cardholder_name\": \"CARD HOLDER0\",\n"
          + "          \"gateway\": \"test\",\n"
          + "          \"refunds\": [\n"
          + "              {\n"
          + "                  \"refund_id\": \"11e922c2-2b72-7cca-9cf3-1b0af83dce8a\",\n"
          + "                  \"amount\": 9000,\n"
          + "                  \"currency\": \"JPY\",\n"
          + "                  \"amount_formatted\": 9000,\n"
          + "                  \"reason\": \"duplicate\"\n"
          + "              }\n"
          + "          ]\n"
          + "      },\n"
          + "      \"payment_type\": \"card\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"store_id\": \"45facc11-efc8-4156-8ef3-e363a70a54c3\",\n"
          + "      \"resource_id\": \"bae4fd94-aace-437c-bf63-8908008dff81\",\n"
          + "      \"charge_id\": \"bae4fd94-aace-437c-bf63-8908008dff81\",\n"
          + "      \"amount\": 400,\n"
          + "      \"currency\": \"CAD\",\n"
          + "      \"amount_formatted\": 400.5,\n"
          + "      \"type\": \"charge\",\n"
          + "      \"status\": \"failed\",\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "      \"mode\": \"test\",\n"
          + "      \"merchant_name\": \"root admin\",\n"
          + "      \"user_data\": {\n"
          + "          \"type\": \"charge\",\n"
          + "          \"customer_name\": \"Konbini Customer\",\n"
          + "          \"convenience_store\": \"lawson\",\n"
          + "          \"gateway\": \"test\",\n"
          + "          \"refunds\": []\n"
          + "      },\n"
          + "      \"payment_type\": \"konbini\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"store_id\": \"45facc11-efc8-4156-8ef3-e363a70a54c3\",\n"
          + "      \"resource_id\": \"6da25a85-754c-41db-97ec-c2e50f7591d5\",\n"
          + "      \"charge_id\": \"6da25a85-754c-41db-97ec-c2e50f7591d5\",\n"
          + "      \"amount\": 700,\n"
          + "      \"currency\": \"EUR\",\n"
          + "      \"amount_formatted\": 700.9,\n"
          + "      \"type\": \"refund\",\n"
          + "      \"status\": \"successful\",\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "      \"mode\": \"test\",\n"
          + "      \"merchant_name\": \"one merchant\",\n"
          + "      \"user_data\": {\n"
          + "          \"type\": \"refund\",\n"
          + "          \"reason\": \"customer_request\",\n"
          + "          \"cardholder_email_address\": \"cardholder_mail@univapay.com\",\n"
          + "          \"cardholder_phone_number\": {\n"
          + "              \"country_code\": 81,\n"
          + "              \"local_number\": \"0312345678\"\n"
          + "          },\n"
          + "          \"gateway\": \"test\"\n"
          + "      },\n"
          + "      \"payment_type\": \"paidy\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"store_id\": \"45facc11-efc8-4156-8ef3-e363a70a54c3\",\n"
          + "      \"resource_id\": \"6da25a85-754c-41db-97ec-c2e50f7591d5\",\n"
          + "      \"charge_id\": \"6da25a85-754c-41db-97ec-c2e50f7591d5\",\n"
          + "      \"amount\": 700,\n"
          + "      \"currency\": \"EUR\",\n"
          + "      \"amount_formatted\": 700.9,\n"
          + "      \"type\": \"refund\",\n"
          + "      \"status\": \"successful\",\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "      \"mode\": \"test\",\n"
          + "      \"merchant_name\": \"one merchant\",\n"
          + "      \"user_data\": {\n"
          + "          \"type\": \"refund\",\n"
          + "          \"reason\": \"customer_request\",\n"
          + "          \"cardholder_email_address\": \"qr_email@univapay.com\",\n"
          + "          \"gateway\": \"test\"\n"
          + "      },\n"
          + "      \"payment_type\": \"qr_scan\"\n"
          + "    },\n"
          + "    {\n"
          + "      \"store_id\": \"45facc11-efc8-4156-8ef3-e363a70a54c3\",\n"
          + "      \"resource_id\": \"e1771339-b989-4a43-99c1-5e35d8008426\",\n"
          + "      \"charge_id\": \"e1771339-b989-4a43-99c1-5e35d8008426\",\n"
          + "      \"amount\": 50000,\n"
          + "      \"currency\": \"JPY\",\n"
          + "      \"amount_formatted\": 50000,\n"
          + "      \"type\": \"charge\",\n"
          + "      \"status\": \"failed\",\n"
          + "      \"metadata\": {\n"
          + "          \"float\": 10.3\n"
          + "      },\n"
          + "      \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "      \"mode\": \"test\",\n"
          + "      \"merchant_name\": \"merchant_name\",\n"
          + "      \"user_data\": {\n"
          + "          \"card_brand\": \"mastercard\",\n"
          + "          \"type\": \"charge\",\n"
          + "          \"cardholder_name\": \"CARD HOLDER4\",\n"
          + "          \"gateway\": \"test\",\n"
          + "          \"refunds\": [\n"
          + "              {\n"
          + "                  \"refund_id\": \"11e922c2-2b72-7cca-9cf3-1b0af83dce8a\",\n"
          + "                  \"amount\": 9000,\n"
          + "                  \"currency\": \"JPY\",\n"
          + "                  \"amount_formatted\": 9000,\n"
          + "                  \"reason\": \"duplicate\"\n"
          + "              }\n"
          + "          ]\n"
          + "      },\n"
          + "      \"payment_type\": \"apple_pay\"\n"
          + "    }\n"
          + "  ],\n"
          + "  \"has_more\": false\n"
          + "}";

  public static String getMeFakeResponse =
      "{\n"
          + "    \"id\": \"51b26a3e-e90e-11e6-bb73-eb35a317b43b\",\n"
          + "    \"verification_data_id\": \"11e77594-7419-5606-937e-9756decfe262\",\n"
          + "    \"name\": \"newaccount1\",\n"
          + "    \"email\": \"new@account1.com\",\n"
          + "    \"verified\": false,\n"
          + "    \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "    \"configuration\": "
          + "{\n"
          + "        \"percent_fee\": 0.08,\n"
          + "        \"flat_fees\": [{\"amount\":3000, \"currency\":\"jpy\"}],\n"
          + "        \"logo_url\" : \"http://www.store.com/some-logo.png\",\n"
          + "        \"country\":  \"JP\",\n"
          + "        \"language\": \"de\",\n"
          + "        \"display_time_zone\": \"Asia/Tokyo\",\n"
          + "        \"min_transfer_payout\": {"
          + "           \"amount\": 100000,\n"
          + "           \"currency\": \"JPY\"\n"
          + "        },\n"
          + "        \"maximum_charge_amounts\": [{\"amount\":200000, \"currency\":\"USD\"}],\n"
          + "        \"user_transactions_configuration\": {\n"
          + "            \"enabled\": true,\n"
          + "            \"notify_customer\": true\n"
          + "        },\n"
          + "        \"card_configuration\": {\n"
          + "            \"enabled\": true,\n"
          + "            \"debit_enabled\": true,\n"
          + "            \"prepaid_enabled\": true,\n"
          + "            \"forbidden_card_brands\": [ \"jcb\", \"maestro\" ],\n"
          + "            \"allowed_countries_by_ip\": [ \"AS\", \"AR\", \"FJ\" ],\n"
          + "            \"foreign_cards_allowed\": false,\n"
          + "            \"fail_on_new_email\": false,\n"
          + "            \"card_limit\": {\"amount\":100000,\"currency\":\"jpy\",\"amount_formatted\":\"100000\",\"duration\":\"P35D\"},\n"
          + "            \"allow_empty_cvv\": true\n"
          + "        },\n"
          + "        \"qr_scan_configuration\": {\n"
          + "            \"enabled\": true,\n"
          + "            \"forbidden_qr_scan_gateways\" : [ \"qq\" ]\n"
          + "        },\n"
          + "        \"convenience_configuration\": {\n"
          + "            \"enabled\": false\n"
          + "        },\n"
          + "       \"transfer_schedule\": {\n"
          + "            \"wait_period\": \"P7D\",\n"
          + "            \"period\": \"monthly\",\n"
          + "            \"full_period_required\": true,\n"
          + "            \"day_of_week\": \"thursday\",\n"
          + "            \"week_of_month\": \"fourth\",\n"
          + "            \"day_of_month\": 27,\n"
          + "            \"weekly_closing_day\": null,\n"
          + "            \"weekly_payout_day\": null\n"
          + "        },"
          + "        \"recurring_token_configuration\": {\n"
          + "            \"recurring_type\": \"bounded\",\n"
          + "            \"charge_wait_period\": \"PT72H\",\n"
          + "            \"card_charge_cvv_confirmation\": {\n"
          + "                \"enabled\": true,\n"
          + "                \"threshold\": [\n"
          + "                    {\n"
          + "                        \"amount\": 10000,\n"
          + "                        \"currency\": \"JPY\"\n"
          + "                    }\n"
          + "                ]\n"
          + "            }\n"
          + "        },\n"
          + "        \"security_configuration\": {\n"
          + "            \"inspect_suspicious_login_after\": \"P20D\",\n"
          + "            \"refund_percent_limit\": 0.75,\n"
          + "            \"limit_charge_by_card_configuration\": {\"quantity_of_charges\":30, \"duration_window\":\"PT2H\"}\n"
          + "        },\n"
          + "        \"subscription_configuration\": {\n"
          + "            \"failed_charges_to_cancel\": 3,\n"
          + "            \"suspend_on_cancel\": true\n"
          + "        },\n"
          + "        \"installments_configuration\": {\n"
          + "            \"enabled\": true,\n"
          + "            \"failed_cycles_to_cancel\": 2,\n"
          + "            \"min_charge_amount\": {"
          + "               \"amount\": 1000,"
          + "               \"currency\": \"jpy\""
          + "            },\n"
          + "            \"max_payout_period\": \"P50D\",\n"
          + "            \"only_with_processor\": true,\n"
          + "            \"supported_payment_types\": [\"card\", \"qr_scan\"]\n"
          + "        },\n"
          + "        \"card_brand_percent_fees\": {\n"
          + "            \"visa\": 0.025,\n"
          + "            \"american_express\": 0.03,\n"
          + "            \"mastercard\": 0.035,\n"
          + "            \"maestro\": 0.04,\n"
          + "            \"discover\": 0.045,\n"
          + "            \"jcb\": 0.05,\n"
          + "            \"diners_club\": 0.055,\n"
          + "            \"unionpay\": 0.06\n"
          + "        }\n"
          + "    }\n"
          + "}";
}
