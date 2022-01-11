package com.univapay.sdk.models.response;

import com.univapay.sdk.types.IdempotencyStatus;
import org.jetbrains.annotations.Nullable;

public abstract class UnivapayResponse {

  private IdempotencyStatus idempotencyStatus;

  public IdempotencyStatus getIdempotencyStatus() {
    return idempotencyStatus;
  }

  public void setIdempotencyStatus(@Nullable IdempotencyStatus idempotencyStatus) {
    this.idempotencyStatus = idempotencyStatus;
  }
}
