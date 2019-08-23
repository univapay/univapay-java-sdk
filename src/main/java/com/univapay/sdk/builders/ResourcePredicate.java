package com.univapay.sdk.builders;

public interface ResourcePredicate<T> {
  boolean test(T resource);
}
