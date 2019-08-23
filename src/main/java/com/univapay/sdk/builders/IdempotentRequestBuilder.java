package com.univapay.sdk.builders;

import com.univapay.sdk.models.common.IdempotencyKey;

public interface IdempotentRequestBuilder<B> {

  IdempotencyKey getIdempotencyKey();

  B withIdempotencyKey(IdempotencyKey idempotencyKey);
}
