package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.types.*;
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.RecurringTokenInterval;
import com.univapay.sdk.types.TransactionTokenType;
import com.univapay.sdk.utils.MetadataAdapter;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TransactionToken extends UnivapayResponse implements SimpleModel<TransactionTokenId> {
  @SerializedName("id")
  private UUID id;

  @SerializedName("store_id")
  private UUID storeId;

  @SerializedName("email")
  private String email;

  @SerializedName("active")
  private Boolean active;

  @SerializedName("mode")
  private ProcessingMode mode;

  @SerializedName("type")
  private TransactionTokenType type;

  @SerializedName("usage_limit")
  private RecurringTokenInterval usageLimit;

  @SerializedName("created_on")
  private OffsetDateTime createdOn;

  @SerializedName("last_used_on")
  private OffsetDateTime lastUsedOn;

  @SerializedName("payment_type")
  private PaymentTypeName paymentTypeName;

  @SerializedName("metadata")
  private MetadataMap metadata;

  @SerializedName("confirmed")
  private Boolean confirmed;

  public TransactionTokenId getId() {
    return new TransactionTokenId(id);
  }

  public StoreId getStoreId() {
    return new StoreId(storeId);
  }

  public String getEmail() {
    return email;
  }

  public Boolean getActive() {
    return active;
  }

  public ProcessingMode getMode() {
    return mode;
  }

  public TransactionTokenType getType() {
    return type;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }

  public OffsetDateTime getLastUsedOn() {
    return lastUsedOn;
  }

  public PaymentTypeName getPaymentTypeName() {
    return paymentTypeName;
  }

  public RecurringTokenInterval getUsageLimit() {
    return usageLimit;
  }

  public MetadataMap getMetadata() {
    return metadata;
  }

  public <T> T getMetadata(MetadataAdapter<T> deserializer) {
    return deserializer.deserialize(metadata);
  }

  public Boolean getConfirmed() {
    return confirmed;
  }
}
