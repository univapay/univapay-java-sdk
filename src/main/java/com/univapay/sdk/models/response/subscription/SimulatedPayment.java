package com.univapay.sdk.models.response.subscription;

import com.google.gson.annotations.SerializedName;
import java.math.BigInteger;
import org.joda.time.LocalDate;
import org.threeten.bp.ZoneId;

public class SimulatedPayment {

  @SerializedName("due_date")
  private LocalDate dueDate;

  @SerializedName("zone_id")
  private ZoneId zoneId;

  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("currency")
  private String currency;

  @SerializedName("is_paid")
  private Boolean isPaid;

  @SerializedName("is_last_payment")
  private Boolean isLastPayment;

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

  public Boolean getIsPaid() {
    return isPaid;
  }

  public Boolean getIsLastPayment() {
    return isLastPayment;
  }
}
