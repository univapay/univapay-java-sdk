package com.univapay.sdk.models.response.gateway;

import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.types.Gateway;

public class UnivapayGateway extends UnivapayResponse {

  private Gateway gateway;

  public Gateway getGateway() {
    return gateway;
  }

  public UnivapayGateway(Gateway gateway) {
    this.gateway = gateway;
  }
}
