package com.univapay.sdk.builders;

public interface DescriptorRetry<B, T> {

  B withDescriptor(String descriptor);

  B withDescriptor(String descriptor, Boolean ignoreDescriptorOnError);

  Request<T> retryIgnoringDescriptor(Request<T> originalRequest);
}
