package com.univapay.sdk.utils;

import com.univapay.sdk.models.common.IdempotencyKey;
import com.univapay.sdk.types.IdempotencyStatus;

public interface MockServer {
  MockServer withIdempotencySettings(
      IdempotencyKey idempotencyKey, IdempotencyStatus idempotencyStatus);

  MockServer withContentType(String contentType);
}
