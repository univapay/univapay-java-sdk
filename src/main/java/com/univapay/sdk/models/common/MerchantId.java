package com.univapay.sdk.models.common;

import java.util.UUID;

public class MerchantId extends BaseId {
  public MerchantId(String id) {
    super(id);
  }

  public MerchantId(UUID id) {
    super(id);
  }
}
