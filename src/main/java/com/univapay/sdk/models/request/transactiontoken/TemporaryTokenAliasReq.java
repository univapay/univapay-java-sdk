package com.univapay.sdk.models.request.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.types.MetadataMap;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

public class TemporaryTokenAliasReq {

  @SerializedName("transaction_token_id")
  private UUID transactionTokenId;

  @SerializedName("metadata")
  private MetadataMap metadata;

  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("currency")
  private String currency;

  @SerializedName("valid_until")
  private Date validUntil;

  public TransactionTokenId getTransactionTokenId() {
    return new TransactionTokenId(transactionTokenId);
  }

  public Date getValidUntil() {
    return validUntil;
  }

  public MetadataMap getMetadata() {
    return metadata;
  }

  public BigInteger getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public TemporaryTokenAliasReq(
      TransactionTokenId transactionTokenId, Date validUntil, MetadataMap metadata) {
    this.transactionTokenId = transactionTokenId.toUUID();
    this.validUntil = validUntil;
    this.metadata = metadata;
  }

  public TemporaryTokenAliasReq(
      TransactionTokenId transactionTokenId,
      Date validUntil,
      MetadataMap metadata,
      MoneyLike money) {
    this.transactionTokenId = transactionTokenId.toUUID();
    this.validUntil = validUntil;
    this.metadata = metadata;
    this.amount = money.getAmount();
    this.currency = money.getCurrency();
  }
}
