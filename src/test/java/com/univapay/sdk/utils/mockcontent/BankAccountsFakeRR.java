package com.univapay.sdk.utils.mockcontent;

public class BankAccountsFakeRR {
  public static String listAllBankAccountsFakeResponse =
      "{\"items\":[{\"id\":\"6692a026-f2f6-499e-9d90-abd957bc89d9\",\"holder_name\":\"Person 2\",\"bank_name\":\"Bank 2\",\"branch_name\":\"Branch 2\",\"country\":\"JP\",\"bank_address\":\"Minato-ku\",\"currency\":\"JPY\",\"account_number\":\"579b06d365402\",\"swift_code\":\"BOJPJPJT\",\"last_four\":\"5402\",\"status\":\"new\",\"account_type\":\"savings\",\"created_on\":\"2017-06-22T16:00:55.436116+09:00\",\"primary\":false},{\"id\":\"6d50a40d-1785-42a6-9504-b003ce319851\",\"holder_name\":\"Person 2\",\"bank_name\":\"Bank 2 Corrected\",\"branch_name\":\"Branch 2\",\"country\":\"JP\",\"currency\":\"JPY\",\"bank_account_number\":\"XXXXXXXXXX402\",\"last_four\":\"5402\",\"status\":\"new\",\"account_type\":\"savings\",\"created_on\":\"2017-06-22T16:00:55.436116+09:00\",\"primary_account\":true},{\"id\":\"20cd9ba9-7fa5-4016-9925-f46731ff835e\",\"holder_name\":\"Person 3\",\"bank_name\":\"Bank 3\",\"branch_name\":\"Branch 3\",\"country\":\"JP\",\"currency\":\"JPY\",\"bank_account_number\":\"XXXXXXXXXX403\",\"last_four\":\"5403\",\"status\":\"new\",\"account_type\":\"savings\",\"created_on\":\"2017-06-22T16:00:55.436116+09:00\",\"primary_account\":false},{\"id\":\"42badbae-d34d-4c82-8ef2-1675ad8b3387\",\"holder_name\":\"Person 1\",\"bank_name\":\"Bank 1\",\"branch_name\":\"Branch 1\",\"country\":\"JP\",\"currency\":\"JPY\",\"account_number\":\"579b06d365402\",\"last_four\":\"5401\",\"status\":\"new\",\"account_type\":\"savings\",\"created_on\":\"2017-06-22T16:00:55.436116+09:00\",\"primary\":false}],\"has_more\":false}";

  public static String postBankAccountFakeRequestWithSwift =
      "{\"country\":\"JP\",\"holder_name\":\"Person 2\",\"bank_name\":\"Bank 2\",\"branch_name\":\"Branch 2\",\"currency\":\"JPY\",\"account_number\":\"579b06d365402\",\"swift_code\":\"BOJPJPJT\",\"account_type\":\"savings\"}";

  public static String postBankAccountFakeResponseWithSwift =
      "{\"id\":\"6692a026-f2f6-499e-9d90-abd957bc89d9\",\"holder_name\":\"Person 2\",\"bank_name\":\"Bank 2\",\"branch_name\":\"Branch 2\",\"country\":\"JP\",\"currency\":\"JPY\",\"account_number\":\"579b06d365402\",\"swift_code\":\"BOJPJPJT\",\"last_four\":\"5402\",\"status\":\"new\",\"account_type\":\"savings\",\"created_on\":\"2017-06-22T16:00:55.436116+09:00\",\"primary\":false}";

  public static String getBankAccountFakeResponse =
      "{\"id\":\"6d50a40d-1785-42a6-9504-b003ce319851\",\"holder_name\":\"Person 2\",\"bank_name\":\"Bank 2\",\"branch_name\":\"Branch 2\",\"country\":\"JP\",\"bank_address\":\"Minato-ku\",\"currency\":\"JPY\",\"account_number\":\"579b06d365402\",\"swift_code\":\"BOJPJPJT\",\"last_four\":\"5402\",\"status\":\"new\",\"account_type\":\"savings\",\"created_on\":\"2017-06-22T16:00:55.436116+09:00\",\"primary\":false}";

  public static String updatePostBankAccountFakeRequest =
      "{\"primary\":true,\"bank_name\":\"Bank 2 Corrected\"}";

  public static String updateBankAccountFakeResponse =
      "{\"id\":\"6d50a40d-1785-42a6-9504-b003ce319851\",\"holder_name\":\"Person 2\",\"bank_name\":\"Bank 2 Corrected\",\"branch_name\":\"Branch 2\",\"country\":\"JP\",\"bank_address\":\"Minato-ku\",\"currency\":\"JPY\",\"account_number\":\"579b06d365402\",\"swift_code\":\"BOJPJPJT\",\"last_four\":\"5402\",\"status\":\"new\",\"account_type\":\"savings\",\"created_on\":\"2017-06-22T16:00:55.436116+09:00\",\"primary\":true}";

  public static String getPrimaryBankAccountFakeResponse =
      "{\n"
          + "  \"id\": \"fc08d444-702a-4b6a-8abd-067afd3f437e\",\n"
          + "  \"holder_name\": \"Russell Berry\",\n"
          + "  \"bank_name\": \"Ozu Bank\",\n"
          + "  \"branch_name\": null,\n"
          + "  \"country\": \"AL\",\n"
          + "  \"bank_address\": null,\n"
          + "  \"currency\": \"EUR\",\n"
          + "  \"account_number\": \"FR21 1184 2544 44IF N3Y1 EMII D95\",\n"
          + "  \"swift_code\": \"HIDLFRZA\",\n"
          + "  \"last_four\": \" D95\",\n"
          + "  \"status\": \"errored\",\n"
          + "  \"account_type\": \"savings\", \n"
          + "  \"created_on\": \"2017-06-22T16:00:55.436116+09:00\",\n"
          + "  \"primary\": true\n"
          + "}";
}
