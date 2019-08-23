package com.univapay.sdk.models.response.refund;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.RefundId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.PaymentError;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.types.MetadataMap;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.RefundReason;
import com.univapay.sdk.types.RefundStatus;
import com.univapay.sdk.utils.MetadataAdapter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

public class Refund extends UnivapayResponse implements SimpleModel<RefundId> {
  @SerializedName("id")
  private UUID id;

  @SerializedName("store_id")
  private UUID storeId;

  @SerializedName("charge_id")
  private UUID chargeId;

  @SerializedName("status")
  private RefundStatus status;

  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("currency")
  private String currency;

  @SerializedName("amount_formatted")
  private BigDecimal amountFormatted;

  @SerializedName("reason")
  private RefundReason reason;

  @SerializedName("message")
  private String message;

  @SerializedName("error")
  private PaymentError error;

  @SerializedName("metadata")
  private MetadataMap metadata;

  @SerializedName("mode")
  private ProcessingMode mode;

  @SerializedName("created_on")
  private Date createdOn;

  public RefundId getId() {
    return new RefundId(id);
  }

  public ChargeId getChargeId() {
    return new ChargeId(chargeId);
  }

  public RefundStatus getStatus() {
    return status;
  }

  public BigInteger getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public RefundReason getReason() {
    return reason;
  }

  public String getMessage() {
    return message;
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

  public StoreId getStoreId() {
    return new StoreId(storeId);
  }

  public BigDecimal getAmountFormatted() {
    return amountFormatted;
  }

  public Date getCreatedOn() {
    return createdOn;
  }
}
