package com.univapay.sdk.builders;

public interface RequestBuilder<E, T extends Request<E>> {
  T build();
}
