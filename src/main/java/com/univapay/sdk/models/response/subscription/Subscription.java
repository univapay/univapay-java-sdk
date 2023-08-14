package com.univapay.sdk.models.response.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
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

public class Subscription extends UnivapayResponse implements SimpleModel<SubscriptionId> {
  @SerializedName("id")
  private UUID id;

  @SerializedName("store_id")
  private UUID storeId;

  @SerializedName("transaction_token_id")
  private UUID transactionTokenId;

  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("currency")
  private String currency;

  @SerializedName("amount_formatted")
  private BigDecimal amountFormatted;

  @SerializedName("period")
  private SubscriptionPeriod period;

  @SerializedName("initial_amount")
  private BigInteger initialAmount;

  @SerializedName("initial_amount_formatted")
  private BigDecimal initialAmountFormatted;

  @SerializedName("subsequent_cycles_start")
  private OffsetDateTime subsequentCyclesStart;

  @SerializedName("schedule_settings")
  private ScheduleSettings scheduleSettings;

  @SerializedName("first_charge_capture_after")
  private Duration firstChargeCaptureAfter;

  @SerializedName("first_charge_authorization_only")
  private Boolean firstChargeAuthorizationOnly;

  @SerializedName("only_direct_currency")
  private Boolean onlyDirectCurrency;

  @SerializedName("status")
  private SubscriptionStatus status;

  @SerializedName("descriptor")
  private String descriptor;

  @SerializedName("metadata")
  private Map<String, String> metadata;

  @SerializedName("mode")
  private ProcessingMode mode;

  @SerializedName("created_on")
  private OffsetDateTime createdOn;

  public SubscriptionId getId() {
    return new SubscriptionId(id);
  }

  public StoreId getStoreId() {
    return new StoreId(storeId);
  }

  public UUID getTransactionTokenId() {
    return transactionTokenId;
  }

  public BigInteger getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAmountFormatted() {
    return amountFormatted;
  }

  public SubscriptionPeriod getPeriod() {
    return period;
  }

  public BigInteger getInitialAmount() {
    return initialAmount;
  }

  public BigDecimal getInitialAmountFormatted() {
    return initialAmountFormatted;
  }

  public OffsetDateTime getSubsequentCyclesStart() {
    return subsequentCyclesStart;
  }

  public ScheduleSettings getScheduleSettings() {
    return scheduleSettings;
  }

  public Duration getFirstChargeCaptureAfter() {
    return firstChargeCaptureAfter;
  }

  public Boolean getFirstChargeAuthorizationOnly() {
    return firstChargeAuthorizationOnly;
  }

  public Boolean getOnlyDirectCurrency() {
    return onlyDirectCurrency;
  }

  public String getDescriptor() {
    return descriptor;
  }

  public SubscriptionStatus getStatus() {
    return status;
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }

  public ProcessingMode getMode() {
    return mode;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }
}
