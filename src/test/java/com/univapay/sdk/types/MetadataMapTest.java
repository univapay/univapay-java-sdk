package com.univapay.sdk.types;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.univapay.sdk.utils.metadataadapter.MetadataArrayAdapter;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class MetadataMapTest {

  @Test
  public void shouldDeserialize() {
    MetadataMap metadataMap = new MetadataMap();
    metadataMap.put("key1", "a,b,c");
    metadataMap.put("key2", "x,y,z");
    Map<String, List<String>> deserialized = metadataMap.deserialize(new MetadataArrayAdapter());
    assertThat(deserialized.get("key1").get(0), is("a"));
    assertThat(deserialized.get("key2").get(1), is("y"));
  }
}
