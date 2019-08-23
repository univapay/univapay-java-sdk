package com.univapay.sdk.models.common;

import java.util.UUID;

public class TransactionTokenId extends BaseId {
  public TransactionTokenId(String id) {
    super(id);
  }

  public TransactionTokenId(UUID id) {
    super(id);
  }
}
