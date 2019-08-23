package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.response.UnivapayResponse;
import java.util.Date;

public class TransactionTokenAlias extends UnivapayResponse {

  @SerializedName("key")
  private TokenAliasKey key;

  @SerializedName("valid_until")
  private Date validUntil;

  public TokenAliasKey getKey() {
    return key;
  }

  public Date getValidUntil() {
    return validUntil;
  }
}
