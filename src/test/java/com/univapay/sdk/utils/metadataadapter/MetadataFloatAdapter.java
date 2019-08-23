package com.univapay.sdk.utils.metadataadapter;

import java.util.LinkedHashMap;
import java.util.Map;
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.utils.MetadataAdapter;

public class MetadataFloatAdapter implements MetadataAdapter<Map<String, Float>> {
  @Override
  public MetadataMap serialize(Map<String, Float> obj) {
    MetadataMap map = new MetadataMap();
    for (Map.Entry<String, Float> entry : obj.entrySet()) {
      map.put(entry.getKey(), entry.getValue().toString());
    }
    return map;
  }

  @Override
  public Map<String, Float> deserialize(MetadataMap metadata) {
    Map<String, Float> map = new LinkedHashMap<>();
    for (Map.Entry<String, String> entry : metadata.entrySet()) {
      map.put(entry.getKey(), Float.valueOf(entry.getValue()));
    }
    return map;
  }
}
