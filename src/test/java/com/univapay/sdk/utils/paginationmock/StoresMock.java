package com.univapay.sdk.utils.paginationmock;

import java.util.ArrayList;
import java.util.List;

public class StoresMock {
  public static final List<OneItemMock> storesMock =
      new ArrayList<OneItemMock>() {
        {
          add(
              new OneItemMock(
                  "11e81b94-4f53-a5c8-8ab3-d75ea65c02fc",
                  "        {\n"
                      + "            \"id\": \"11e81b94-4f53-a5c8-8ab3-d75ea65c02fc\",\n"
                      + "            \"name\": \"Store 1\",\n"
                      + "            \"created_on\": \"2018-02-27T17:00:43.476016+09:00\"\n"
                      + "        }"));
          add(
              new OneItemMock(
                  "8486dc98-9836-41dd-b598-bbf49d5bc861",
                  "    {\n"
                      + "      \"id\": \"8486dc98-9836-41dd-b598-bbf49d5bc861\",\n"
                      + "      \"name\": \"Store 2\",\n"
                      + "      \"created_on\": \"2018-02-27T17:00:43.476016+09:00\"\n"
                      + "    }"));
          add(
              new OneItemMock(
                  "11e81b94-4f52-1398-8ab3-230675bcb38f",
                  "     {\n"
                      + "            \"id\": \"11e81b94-4f52-1398-8ab3-230675bcb38f\",\n"
                      + "            \"name\": \"Store 3\",\n"
                      + "            \"created_on\": \"2018-02-27T17:00:43.476016+09:00\"\n"
                      + "        }"));
        }
      };
}
