package com.univapay.sdk.models.response.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.common.threeDs.ChargeThreeDsData;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.SubscriptionPeriod;
import com.univapay.sdk.types.SubscriptionStatus;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;

public class Subscription extends UnivapayResponse implements SimpleModel<SubscriptionId> {
  @SerializedName("id")
  private UUID id;

  @SerializedName("store_id")
  private UUID storeId;

  @Getter
  @SerializedName("transaction_token_id")
  private UUID transactionTokenId;

  @Getter
  @SerializedName("amount")
  private BigInteger amount;

  @Getter
  @SerializedName("currency")
  private String currency;

  @Getter
  @SerializedName("amount_formatted")
  private BigDecimal amountFormatted;

  @Getter
  @SerializedName("period")
  private SubscriptionPeriod period;

  @Getter
  @SerializedName("initial_amount")
  private BigInteger initialAmount;

  @Getter
  @SerializedName("initial_amount_formatted")
  private BigDecimal initialAmountFormatted;

  @Getter
  @SerializedName("subsequent_cycles_start")
  private OffsetDateTime subsequentCyclesStart;

  @Getter
  @SerializedName("schedule_settings")
  private ScheduleSettings scheduleSettings;

  @Getter
  @SerializedName("first_charge_capture_after")
  private Duration firstChargeCaptureAfter;

  @Getter
  @SerializedName("first_charge_authorization_only")
  private Boolean firstChargeAuthorizationOnly;

  @Getter
  @SerializedName("only_direct_currency")
  private Boolean onlyDirectCurrency;

  @Getter
  @SerializedName("status")
  private SubscriptionStatus status;

  @Getter
  @SerializedName("metadata")
  private Map<String, Object> metadata;

  @Getter
  @SerializedName("mode")
  private ProcessingMode mode;

  @Getter
  @SerializedName("created_on")
  private OffsetDateTime createdOn;

  @Getter
  @SerializedName("three_ds")
  private ChargeThreeDsData threeDs;

  public SubscriptionId getId() {
    return new SubscriptionId(id);
  }

  public StoreId getStoreId() {
    return new StoreId(storeId);
  }
}
