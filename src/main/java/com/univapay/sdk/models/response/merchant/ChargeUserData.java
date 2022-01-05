package com.univapay.sdk.models.response.merchant;

import java.util.List;

public class ChargeUserData {
  private final List<RefundDetails> refunds;

  ChargeUserData(List<RefundDetails> refunds) {
    this.refunds = refunds;
  }

  public List<RefundDetails> getRefunds() {
    return refunds;
  }
}
