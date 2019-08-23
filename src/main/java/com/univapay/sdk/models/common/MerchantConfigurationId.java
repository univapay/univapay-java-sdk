package com.univapay.sdk.models.common;

import java.util.UUID;

public class MerchantConfigurationId extends BaseId {
  public MerchantConfigurationId(String id) {
    super(id);
  }

  public MerchantConfigurationId(UUID id) {
    super(id);
  }
}
