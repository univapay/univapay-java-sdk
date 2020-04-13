package com.univapay.sdk.models.response.subscription;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.ScheduledPaymentId;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class ScheduledPayment extends UnivapayResponse implements SimpleModel<ScheduledPaymentId> {

  @SerializedName("id")
  private UUID id;

  @SerializedName("due_date")
  private LocalDate dueDate;

  @SerializedName("zone_id")
  private ZoneId zoneId;

  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("amount_formatted")
  private BigDecimal amountFormatted;

  @SerializedName("currency")
  private String currency;

  @SerializedName("is_paid")
  private Boolean isPaid;

  @SerializedName("is_last_payment")
  private Boolean isLastPayment;

  @SerializedName("created_on")
  private OffsetDateTime createdOn;

  @SerializedName("updated_on")
  private OffsetDateTime updatedOn;

  @Override
  public ScheduledPaymentId getId() {
    return new ScheduledPaymentId(id);
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public ZoneId getZoneId() {
    return zoneId;
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

  public Boolean getIsPaid() {
    return isPaid;
  }

  public Boolean getIsLastPayment() {
    return isLastPayment;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }

  public OffsetDateTime getUpdatedOn() {
    return updatedOn;
  }

  public Boolean getPaid() {
    return isPaid;
  }

  public Boolean getLastPayment() {
    return isLastPayment;
  }
}
