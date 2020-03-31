package com.univapay.sdk.types;

import com.univapay.sdk.utils.MetadataAdapter;
import java.util.LinkedHashMap;

public class MetadataMap extends LinkedHashMap<String, String> {
  public <T> T deserialize(MetadataAdapter<T> adapter) {
    return adapter.deserialize(this);
  }
}
