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
      "{\n" + " \"company_contact_info\" : { }, \"system_manager_email\": \"\"\n" + "}";

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
      JsonLoader.loadJson("responses/transactionhistory/store/sample-transaction-history.json");

  public static String getGetStoreTransactionHistoryFakeResponseCreditCard =
      JsonLoader.loadJson(
          "responses/transactionhistory/store/sample-transaction-history-credit-card.json");
  public static String getGetStoreTransactionHistoryFakeResponseConvenienceStore =
      JsonLoader.loadJson(
          "responses/transactionhistory/store/sample-transaction-history-convenience-store.json");
  public static String getGetStoreTransactionHistoryFakeResponseQrCpm =
      JsonLoader.loadJson(
          "responses/transactionhistory/store/sample-transaction-history-qr-code-cpm.json");
  public static String getGetStoreTransactionHistoryFakeResponseQrMpm =
      JsonLoader.loadJson(
          "responses/transactionhistory/store/sample-transaction-history-qr-code-mpm.json");
  public static String getGetStoreTransactionHistoryFakeResponseOnline =
      JsonLoader.loadJson(
          "responses/transactionhistory/store/sample-transaction-history-online.json");
  public static String getGetStoreTransactionHistoryFakeResponsePaidy =
      JsonLoader.loadJson(
          "responses/transactionhistory/store/sample-transaction-history-paidy.json");
}
