package com.univapay.sdk.models.response.merchant;

import com.univapay.sdk.types.RefundReason;

public class RefundUserData {

  private final RefundReason refundReason;

  RefundUserData(RefundReason refundReason) {
    this.refundReason = refundReason;
  }

  public RefundReason getRefundReason() {
    return refundReason;
  }
}
