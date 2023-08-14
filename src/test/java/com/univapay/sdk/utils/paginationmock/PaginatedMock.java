package com.univapay.sdk.utils.paginationmock;

import com.univapay.sdk.types.CursorDirection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class PaginatedMock {
  private final List<OneItemMock> mockList;

  public PaginatedMock(List<OneItemMock> mockList) {
    this.mockList = mockList;
  }

  public boolean isEmpty() {
    return mockList.isEmpty();
  }

  public String getPaginatedResponse(String url) {
    Map<String, String> paramMap = createParamMap(url);

    String limitStr = paramMap.get("limit");
    int limit;
    if (isInt(limitStr)) {
      limit = Integer.valueOf(limitStr);
    } else {
      limit = 10;
    }

    String directionStr = paramMap.get("cursor_direction");
    CursorDirection direction;
    if ("ASC".equalsIgnoreCase(directionStr)) {
      direction = CursorDirection.ASC;
    } else {
      direction = CursorDirection.DESC;
    }

    Pair<List<OneItemMock>, Boolean> paginatedMock =
        createMockList(paramMap.get("cursor"), limit, direction);
    return buildPaginatedResponse(paginatedMock);
  }

  private String buildPaginatedResponse(Pair<List<OneItemMock>, Boolean> paginatedMockPair) {
    List<OneItemMock> paginatedMock = paginatedMockPair.getKey();
    StringBuilder result = new StringBuilder();
    if (paginatedMock.isEmpty()) {
      return StringUtils.EMPTY;
    }
    result.append("{\n").append("  \"items\": [\n");
    for (OneItemMock item : paginatedMock) {
      result.append(item.getResponseBody()).append(",\n");
    }
    int commaIndex = result.length() - 2;
    result.delete(commaIndex, commaIndex + 1);
    result
        .append("  ],\n")
        .append("  \"has_more\": ")
        .append(paginatedMockPair.getValue())
        .append("\n")
        .append("}");
    return result.toString();
  }

  private static final Pattern intPattern = Pattern.compile("^-?[0-9]+$");

  private static boolean isInt(String s) {
    if (s == null) {
      return false;
    }
    return intPattern.matcher(s).find();
  }

  private Pair<List<OneItemMock>, Boolean> createMockList(
      String cursor, int limit, CursorDirection direction) {
    int startIndex = 0;
    if (limit <= 0) {
      limit = 1;
    }
    if (StringUtils.isEmpty(cursor)) {
      if (direction.equals(CursorDirection.ASC)) {
        startIndex = 0;
      } else {
        startIndex = mockList.size();
      }
    } else {
      int count = -1;
      for (OneItemMock itemMock : mockList) {
        count++;
        if (cursor.equals(itemMock.getId())) {
          if (direction.equals(CursorDirection.ASC)) {
            startIndex = count + 1;
          } else {
            startIndex = count;
          }
          break;
        }
      }
    }
    ListIterator<OneItemMock> it = mockList.listIterator(startIndex);
    List<OneItemMock> resultList = new ArrayList<>();
    int resultCount = 0;
    boolean hasMore = false;
    switch (direction) {
      case ASC:
        while (it.hasNext()) {
          if (resultCount >= limit) {
            hasMore = true;
            break;
          }
          resultList.add(it.next());
          resultCount++;
        }
        break;
      case DESC:
        while (it.hasPrevious()) {
          if (resultCount >= limit) {
            hasMore = true;
            break;
          }
          resultList.add(it.previous());
          resultCount++;
        }
        break;
    }
    return Pair.of(resultList, hasMore);
  }

  private static final Pattern urlParamPattern = Pattern.compile("([^&]+)=([^&]*)");

  private Map<String, String> createParamMap(String path) {
    Map<String, String> paramMap = new HashMap<>();
    if (!(path == null || path.isEmpty())) {
      String urlParams = path.substring(path.indexOf("?") + 1);
      Matcher matcher = urlParamPattern.matcher(urlParams);
      while (matcher.find()) {
        paramMap.put(matcher.group(1), matcher.group(2));
      }
    }
    return paramMap;
  }
}
