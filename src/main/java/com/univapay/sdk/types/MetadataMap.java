package com.univapay.sdk.types;

import java.util.LinkedHashMap;
import com.univapay.sdk.utils.MetadataAdapter;

public class MetadataMap extends LinkedHashMap<String, String> {
  public <T> T deserialize(MetadataAdapter<T> adapter) {
    return adapter.deserialize(this);
  }
}
