package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.PaymentTypeName;
import java.time.Period;
import java.util.List;

public class InstallmentsConfiguration {
  @SerializedName("enabled")
  private Boolean enabled;

  @SerializedName("only_with_processor")
  private Boolean onlyWithProcessor;

  @SerializedName("supported_payment_types")
  private List<PaymentTypeName> supportedPaymentTypes;

  @SerializedName("min_charge_amount")
  private MoneyLike minChargeAmount;

  @SerializedName("max_payout_period")
  private Period maxPayoutPeriod;

  @SerializedName("failed_cycles_to_cancel")
  private Integer failedCyclesToCancel;

  public Boolean getEnabled() {
    return enabled;
  }

  public MoneyLike getMinChargeAmount() {
    return minChargeAmount;
  }

  public Period getMaxPayoutPeriod() {
    return maxPayoutPeriod;
  }

  public Integer getFailedCyclesToCancel() {
    return failedCyclesToCancel;
  }

  public Boolean getOnlyWithProcessor() {
    return onlyWithProcessor;
  }

  public List<PaymentTypeName> getSupportedPaymentTypes() {
    return supportedPaymentTypes;
  }
}
