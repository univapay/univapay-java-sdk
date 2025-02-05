package com.univapay.sdk.models.response.transactiontoken;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.RecurringTokenInterval;
import com.univapay.sdk.types.TransactionTokenType;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;

@Getter
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
  private Map<String, Object> metadata;

  @SerializedName("confirmed")
  private Boolean confirmed;

  public TransactionTokenId getId() {
    return new TransactionTokenId(id);
  }

  public StoreId getStoreId() {
    return new StoreId(storeId);
  }
}
