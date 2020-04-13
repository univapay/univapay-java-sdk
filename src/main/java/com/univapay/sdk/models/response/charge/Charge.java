package com.univapay.sdk.models.response.charge;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.response.PaymentError;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.types.ChargeStatus;
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.TransactionTokenType;
import com.univapay.sdk.utils.MetadataAdapter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Charge extends UnivapayResponse implements SimpleModel<ChargeId> {
  @SerializedName("id")
  private UUID id;

  @SerializedName("store_id")
  private UUID storeId;

  @SerializedName("transaction_token_id")
  private UUID transactionTokenId;

  @SerializedName("transaction_token_type")
  private TransactionTokenType transactionTokenType;

  @SerializedName("subscription_id")
  private UUID subscriptionId;

  @SerializedName("requested_amount")
  private BigInteger requestedAmount;

  @SerializedName("requested_currency")
  private String requestedCurrency;

  @SerializedName("requested_amount_formatted")
  private BigDecimal requestedAmountFormatted;

  @SerializedName("charged_amount")
  private BigInteger chargedAmount;

  @SerializedName("charged_currency")
  private String chargedCurrency;

  @SerializedName("charged_amount_formatted")
  private BigDecimal chargedAmountFormatted;

  @SerializedName("only_direct_currency")
  private Boolean onlyDirectCurrency;

  @SerializedName("capture_at")
  private OffsetDateTime captureAt;

  @SerializedName("descriptor")
  private String descriptor;

  @SerializedName("status")
  private ChargeStatus status;

  @SerializedName("error")
  private PaymentError error;

  @SerializedName("metadata")
  private MetadataMap metadata;

  @SerializedName("mode")
  private ProcessingMode mode;

  @SerializedName("created_on")
  private OffsetDateTime createdOn;

  public ChargeId getId() {
    return new ChargeId(id);
  }

  public StoreId getStoreId() {
    return new StoreId(storeId);
  }

  public TransactionTokenId getTransactionTokenId() {
    return new TransactionTokenId(transactionTokenId);
  }

  public TransactionTokenType getTransactionTokenType() {
    return transactionTokenType;
  }

  public SubscriptionId getSubscriptionId() {
    return new SubscriptionId(subscriptionId);
  }

  public BigInteger getRequestedAmount() {
    return requestedAmount;
  }

  public String getRequestedCurrency() {
    return requestedCurrency;
  }

  public BigDecimal getRequestedAmountFormatted() {
    return requestedAmountFormatted;
  }

  public BigInteger getChargedAmount() {
    return chargedAmount;
  }

  public String getChargedCurrency() {
    return chargedCurrency;
  }

  public BigDecimal getChargedAmountFormatted() {
    return chargedAmountFormatted;
  }

  public Boolean getOnlyDirectCurrency() {
    return onlyDirectCurrency;
  }

  public OffsetDateTime getCaptureAt() {
    return captureAt;
  }

  public String getDescriptor() {
    return descriptor;
  }

  public ChargeStatus getStatus() {
    return status;
  }

  public PaymentError getError() {
    return error;
  }

  public MetadataMap getMetadata() {
    return metadata;
  }

  public <T> T getMetadata(MetadataAdapter<T> deserializer) {
    return deserializer.deserialize(metadata);
  }

  public ProcessingMode getMode() {
    return mode;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }
}
