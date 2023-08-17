package com.univapay.sdk.models.response.merchant;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.ResourceId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.types.PaymentTypeName;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.TransactionStatus;
import com.univapay.sdk.types.TransactionType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

public class Transaction implements SimpleModel<ResourceId> {

  @SerializedName("store_id")
  private UUID storeId;

  @SerializedName("resource_id")
  private UUID resourceId;

  @SerializedName("charge_id")
  private UUID chargeId;

  @SerializedName("amount")
  private BigInteger amount;

  @SerializedName("currency")
  private String currency;

  @SerializedName("amount_formatted")
  private BigDecimal amountFormatted;

  @SerializedName("type")
  private TransactionType transactionType;

  @SerializedName("status")
  private TransactionStatus status;

  @SerializedName("metadata")
  private Map<String, Object> metadata;

  @SerializedName("created_on")
  private OffsetDateTime createdOn;

  @SerializedName("mode")
  private ProcessingMode mode;

  @SerializedName("merchant_name")
  private String merchantName;

  @SerializedName("payment_type")
  private PaymentTypeName paymentType;

  @SerializedName("user_data")
  private TransactionUserData userData;

  public StoreId getStoreId() {
    return new StoreId(storeId);
  }

  public ResourceId getResourceId() {
    return new ResourceId(resourceId);
  }

  public ChargeId getChargeId() {
    return new ChargeId(chargeId);
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

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public TransactionStatus getStatus() {
    return status;
  }

  public Map<String, Object> getMetadata() {
    return metadata;
  }

  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }

  public ProcessingMode getMode() {
    return mode;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public PaymentTypeName getPaymentType() {
    return paymentType;
  }

  public TransactionUserData getUserData() {
    return userData;
  }

  @Override
  public ResourceId getId() {
    return new ResourceId(resourceId);
  }
}
