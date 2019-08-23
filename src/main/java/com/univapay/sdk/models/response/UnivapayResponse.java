package com.univapay.sdk.models.response;

import javax.annotation.Nullable;
import com.univapay.sdk.types.IdempotencyStatus;

public abstract class UnivapayResponse {

  private IdempotencyStatus idempotencyStatus;

  public IdempotencyStatus getIdempotencyStatus() {
    return idempotencyStatus;
  }

  public void setIdempotencyStatus(@Nullable IdempotencyStatus idempotencyStatus) {
    this.idempotencyStatus = idempotencyStatus;
  }
}
