package com.univapay.sdk.utils.metadataadapter;

import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.utils.MetadataAdapter;
import java.util.*;

public class MetadataArrayAdapter implements MetadataAdapter<Map<String, List<String>>> {
  private static final String COMMA = ",";

  @Override
  public MetadataMap serialize(Map<String, List<String>> obj) {
    MetadataMap result = new MetadataMap();
    for (Map.Entry<String, List<String>> entry : obj.entrySet()) {
      StringBuilder values = new StringBuilder();
      for (String s : entry.getValue()) {
        values.append(s).append(COMMA);
      }
      values.deleteCharAt(values.length() - 1);
      result.put(entry.getKey(), values.toString());
    }
    return result;
  }

  @Override
  public Map<String, List<String>> deserialize(MetadataMap metadata) {
    Map<String, List<String>> result = new LinkedHashMap<>();
    for (Map.Entry<String, String> entry : metadata.entrySet()) {
      List<String> list = new ArrayList<>(Arrays.asList(entry.getValue().split(",")));
      result.put(entry.getKey(), list);
    }
    return result;
  }
}
