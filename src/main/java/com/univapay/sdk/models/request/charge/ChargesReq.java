package com.univapay.sdk.models.request.charge;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.MoneyLike;
import com.univapay.sdk.models.common.TransactionTokenId;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("FieldCanBeLocal")
public class ChargesReq {
  @SerializedName("transaction_token_id")
  private UUID transactionTokenId;

  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("currency")
  private String currency;

  @SerializedName("only_direct_currency")
  private Boolean onlyDirectCurrency;

  @SerializedName("capture")
  private Boolean capture;

  @SerializedName("capture_at")
  private OffsetDateTime captureAt;

  @SerializedName("descriptor")
  private String descriptor;

  @SerializedName("metadata")
  private Map<String, String> metadata;

  public ChargesReq(
      TransactionTokenId transactionTokenId,
      MoneyLike money,
      Boolean capture,
      OffsetDateTime captureAt,
      Map<String, String> metadata,
      Boolean onlyDirectCurrency,
      String descriptor) {
    this.transactionTokenId = transactionTokenId.toUUID();
    this.amount = money.getAmount();
    this.currency = money.getCurrency();
    this.onlyDirectCurrency = onlyDirectCurrency;
    this.capture = capture;
    this.captureAt = captureAt;
    this.descriptor = descriptor;
    this.metadata = metadata;
  }
}
