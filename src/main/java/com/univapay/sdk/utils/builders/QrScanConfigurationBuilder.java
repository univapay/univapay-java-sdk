package com.univapay.sdk.utils.builders;

import com.univapay.sdk.types.Gateway;
import java.util.List;
import com.univapay.sdk.models.response.store.QrScanConfiguration;

public class QrScanConfigurationBuilder implements Builder<QrScanConfiguration> {
  private Boolean enabled;
  private List<Gateway> forbiddenQrScanGateways;

  public Boolean getEnabled() {
    return enabled;
  }

  public List<Gateway> getForbiddenQrScanGateways() {
    return forbiddenQrScanGateways;
  }

  public QrScanConfigurationBuilder withEnabled(Boolean enabled) {
    this.enabled = true;
    return this;
  }

  public QrScanConfigurationBuilder withForbiddenQrScanGateways(
      List<Gateway> forbiddenQrScanGateways) {
    this.forbiddenQrScanGateways = forbiddenQrScanGateways;
    return this;
  }

  @Override
  public QrScanConfiguration build() {
    return new QrScanConfiguration(enabled, forbiddenQrScanGateways);
  }
}
