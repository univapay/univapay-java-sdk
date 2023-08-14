package com.univapay.sdk.models.response.transfer;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.BankAccountId;
import com.univapay.sdk.models.common.TransferId;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.types.TransferStatus;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

public class Transfer extends UnivapayResponse implements SimpleModel<TransferId> {
  @SerializedName("id")
  private UUID transferId;

  @SerializedName("bank_account_id")
  private UUID bankAccountId;

  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("currency")
  private String currency;

  @SerializedName("amount_formatted")
  private BigDecimal amountFormatted;

  @SerializedName("status")
  private TransferStatus status;

  @SerializedName("error_code")
  private String errorCode;

  @SerializedName("error_text")
  private String errorText;

  @SerializedName("metadata")
  private Map<String, String> metadata;

  @SerializedName("note")
  private String note;

  @SerializedName("from")
  private LocalDate from;

  @SerializedName("to")
  private LocalDate to;

  @SerializedName("created_on")
  private OffsetDateTime createdOn;

  public TransferId getTransferId() {
    return new TransferId(transferId);
  }

  public BankAccountId getBankAccountId() {
    return new BankAccountId(bankAccountId);
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

  public TransferStatus getStatus() {
    return status;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorText() {
    return errorText;
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }

  public String getNote() {
    return note;
  }

  public LocalDate getFrom() {
    return from;
  }

  public LocalDate getTo() {
    return to;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }

  @Override
  public TransferId getId() {
    return new TransferId(transferId);
  }
}
