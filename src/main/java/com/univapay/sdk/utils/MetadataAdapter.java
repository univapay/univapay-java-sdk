package com.univapay.sdk.utils;

import com.univapay.sdk.types.MetadataMap;

/**
 * This is an interface to adapt a metadata from {@link MetadataMap}({@literal
 * LinkedHashMap<String,String>}) to something.<br>
 * If you need to deal with a metadata as another type, implement this interface.<br>
 *
 * @param <T> a type to be converted
 */
public interface MetadataAdapter<T> {

  MetadataMap serialize(T obj);

  T deserialize(MetadataMap metadata);
}
