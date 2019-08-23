package com.univapay.sdk.models.common;

import java.util.UUID;

public class BankAccountId extends BaseId {
  public BankAccountId(String id) {
    super(id);
  }

  public BankAccountId(UUID id) {
    super(id);
  }
}
