package com.univapay.sdk.models.response.store;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.Gateway;
import java.util.List;

public class QrScanConfiguration {
  @SerializedName("enabled")
  private Boolean enabled;

  @SerializedName("forbidden_qr_scan_gateways")
  private List<Gateway> forbiddenQrScanGateways;

  public Boolean getEnabled() {
    return enabled;
  }

  public List<Gateway> getForbiddenQrScanGateways() {
    return forbiddenQrScanGateways;
  }

  public QrScanConfiguration(Boolean enabled, List<Gateway> forbiddenQrScanGateways) {
    this.enabled = enabled;
    this.forbiddenQrScanGateways = forbiddenQrScanGateways;
  }
}
