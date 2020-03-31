package com.univapay.sdk.utils.metadataadapter;

import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.utils.MetadataAdapter;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ManyTypesAdapter implements MetadataAdapter<ManyTypesMetadata> {

  @Override
  public MetadataMap serialize(ManyTypesMetadata obj) {
    MetadataMap map = new MetadataMap();
    map.put("string_value", obj.getStringValue());
    map.put("biginteger_value", obj.getBigIntegerValue().toString());
    map.put("bigdecimal_value", obj.getBigDecimalValue().toString());
    map.put("boolean_value", obj.getBooleanValue().toString());
    map.put("float_value", obj.getFloatValue().toString());
    return map;
  }

  @Override
  public ManyTypesMetadata deserialize(MetadataMap metadata) {
    return new ManyTypesMetadata(
        metadata.get("string_value"),
        new BigInteger(metadata.get("biginteger_value")),
        new BigDecimal(metadata.get("bigdecimal_value")),
        metadata.get("boolean_value").equalsIgnoreCase("true"),
        Float.parseFloat(metadata.get("float_value")));
  }
}
