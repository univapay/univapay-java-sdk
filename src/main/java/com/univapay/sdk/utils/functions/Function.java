package com.univapay.sdk.utils.functions;

public interface Function<T, R> {

  R apply(T arg);
}
