package com.univapay.sdk.models.request.configuration;

public class PreconfiguredSemimonthlySchedule extends PreconfiguredTransferSchedule {
  @Override
  public String getConstant() {
    return "semimonthly";
  }
}
