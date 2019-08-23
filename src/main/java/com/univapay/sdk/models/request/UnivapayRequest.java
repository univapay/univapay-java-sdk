package com.univapay.sdk.models.request;

import com.univapay.sdk.utils.UnivapayCall;

public interface UnivapayRequest<T> {

  UnivapayCall<T> execute();
}
