package com.univapay.sdk.models.response.ledger;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.LedgerId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.types.LedgerOrigin;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Ledger extends UnivapayResponse implements SimpleModel<LedgerId> {
  @SerializedName("id")
  private UUID id;

  @SerializedName("store_id")
  private UUID storeId;

  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("currency")
  private String currency;

  @SerializedName("amount_formatted")
  private BigDecimal amountFormatted;

  @SerializedName("percent_fee")
  private BigDecimal percentFee;

  @SerializedName("flat_fee_amount")
  private BigDecimal flatFeeAmount;

  @SerializedName("flat_fee_currency")
  private String flatFeeCurrency;

  @SerializedName("flat_fee_formatted")
  private BigDecimal flatFeeFormatted;

  @SerializedName("exchange_rate")
  private BigDecimal exchangeRate;

  @SerializedName("note")
  private String note;

  @SerializedName("origin")
  private LedgerOrigin origin;

  @SerializedName("created_on")
  private OffsetDateTime createdOn;

  @Override
  public LedgerId getId() {
    return new LedgerId(id);
  }

  public StoreId getStoreId() {
    return new StoreId(storeId);
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

  public BigDecimal getPercentFee() {
    return percentFee;
  }

  public BigDecimal getFlatFeeAmount() {
    return flatFeeAmount;
  }

  public String getFlatFeeCurrency() {
    return flatFeeCurrency;
  }

  public BigDecimal getFlatFeeFormatted() {
    return flatFeeFormatted;
  }

  public BigDecimal getExchangeRate() {
    return exchangeRate;
  }

  public String getNote() {
    return note;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }

  public LedgerOrigin getOrigin() {
    return origin;
  }
}
