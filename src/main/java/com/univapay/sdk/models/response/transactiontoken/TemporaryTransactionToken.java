package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.utils.MetadataAdapter;
import java.math.BigInteger;

public class TemporaryTransactionToken extends TransactionToken {

  @SerializedName("alias_metadata")
  private MetadataMap aliasMetadata;

  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("currency")
  private String currency;

  public BigInteger getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public MetadataMap getAliasMetadata() {
    return aliasMetadata;
  }

  public <T> T getAliasMetadata(MetadataAdapter<T> deserializer) {
    return deserializer.deserialize(aliasMetadata);
  }
}
