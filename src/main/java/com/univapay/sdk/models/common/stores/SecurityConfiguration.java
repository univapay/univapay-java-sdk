package com.univapay.sdk.models.common.stores;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import java.time.Period;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecurityConfiguration {
  @SerializedName("inspect_suspicious_login_after")
  private Period inspectSuspiciousLoginAfter;

  @SerializedName("refund_percent_limit")
  private BigDecimal refundPercentLimit;

  @SerializedName("limit_charge_by_card_configuration")
  private LimitChargeByCardConfiguration limitChargeByCardConfiguration;

  @SerializedName("confirmation_required")
  private Boolean confirmationRequired;
}
