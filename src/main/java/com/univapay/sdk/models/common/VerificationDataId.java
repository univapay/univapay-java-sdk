package com.univapay.sdk.models.common;

import java.util.UUID;

public class VerificationDataId extends BaseId {

  public VerificationDataId(String id) {
    super(id);
  }

  public VerificationDataId(UUID id) {
    super(id);
  }
}
