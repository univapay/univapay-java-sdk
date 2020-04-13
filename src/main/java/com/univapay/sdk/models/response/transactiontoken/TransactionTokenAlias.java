package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.response.UnivapayResponse;
import java.time.OffsetDateTime;

public class TransactionTokenAlias extends UnivapayResponse {

  @SerializedName("key")
  private TokenAliasKey key;

  @SerializedName("valid_until")
  private OffsetDateTime validUntil;

  public TokenAliasKey getKey() {
    return key;
  }

  public OffsetDateTime getValidUntil() {
    return validUntil;
  }
}
