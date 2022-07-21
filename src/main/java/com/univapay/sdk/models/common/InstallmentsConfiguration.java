package com.univapay.sdk.models.common;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.PaymentTypeName;
import java.math.BigInteger;
import java.time.Period;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstallmentsConfiguration {

  @SerializedName("enabled")
  private final Boolean enabled;

  @SerializedName("only_with_processor")
  private final Boolean onlyWithProcessor;

  @SerializedName("card_processor")
  private final CardProcessor cardProcessor;

  @SerializedName("supported_payment_types")
  private final List<PaymentTypeName> supportedPaymentTypes;

  @SerializedName("min_charge_amount")
  private final InstallmentsMinChargeAmount minChargeAmount;

  @SerializedName("max_payout_period")
  private final Period maxPayoutPeriod;

  @Data
  @AllArgsConstructor
  public static class CardProcessor {

    @SerializedName("fixed_cycle")
    private final Boolean fixedCycle;

    @SerializedName("revolving")
    private final Boolean revolving;
  }

  @Data
  @AllArgsConstructor
  @Builder
  public static class InstallmentsMinChargeAmount {

    @SerializedName("amount")
    private final BigInteger minChargeAmount;

    @SerializedName("currency")
    private final String minChargeCurrency;

    public MoneyLike getMoney() {
      if (minChargeAmount != null && minChargeCurrency != null) {
        return new MoneyLike(minChargeAmount, minChargeCurrency);
      } else {
        return null;
      }
    }
  }
}
