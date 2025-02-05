package com.univapay.sdk.models.response.charge;

import com.google.gson.annotations.SerializedName;
import com.univapay.sdk.models.common.ChargeId;
import com.univapay.sdk.models.common.StoreId;
import com.univapay.sdk.models.common.SubscriptionId;
import com.univapay.sdk.models.common.TransactionTokenId;
import com.univapay.sdk.models.common.threeDs.ChargeThreeDsData;
import com.univapay.sdk.models.response.PaymentError;
import com.univapay.sdk.models.response.SimpleModel;
import com.univapay.sdk.models.response.UnivapayResponse;
import com.univapay.sdk.types.ChargeStatus;
import com.univapay.sdk.types.ProcessingMode;
import com.univapay.sdk.types.TransactionTokenType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;

public class Charge extends UnivapayResponse implements SimpleModel<ChargeId> {
  @SerializedName("id")
  private UUID id;

  @SerializedName("store_id")
  private UUID storeId;

  @SerializedName("transaction_token_id")
  private UUID transactionTokenId;

  @Getter
  @SerializedName("transaction_token_type")
  private TransactionTokenType transactionTokenType;

  @SerializedName("subscription_id")
  private UUID subscriptionId;

  @Getter
  @SerializedName("requested_amount")
  private BigInteger requestedAmount;

  @Getter
  @SerializedName("requested_currency")
  private String requestedCurrency;

  @Getter
  @SerializedName("requested_amount_formatted")
  private BigDecimal requestedAmountFormatted;

  @Getter
  @SerializedName("charged_amount")
  private BigInteger chargedAmount;

  @Getter
  @SerializedName("charged_currency")
  private String chargedCurrency;

  @Getter
  @SerializedName("charged_amount_formatted")
  private BigDecimal chargedAmountFormatted;

  @Getter
  @SerializedName("only_direct_currency")
  private Boolean onlyDirectCurrency;

  @Getter
  @SerializedName("capture_at")
  private OffsetDateTime captureAt;

  @Getter
  @SerializedName("descriptor")
  private String descriptor;

  @Getter
  @SerializedName("status")
  private ChargeStatus status;

  @Getter
  @SerializedName("error")
  private PaymentError error;

  @Getter
  @SerializedName("metadata")
  private Map<String, Object> metadata;

  @Getter
  @SerializedName("mode")
  private ProcessingMode mode;

  @Getter
  @SerializedName("created_on")
  private OffsetDateTime createdOn;

  @Getter
  @SerializedName("three_ds")
  private ChargeThreeDsData threeDS;

  public ChargeId getId() {
    return new ChargeId(id);
  }

  public StoreId getStoreId() {
    return new StoreId(storeId);
  }

  public TransactionTokenId getTransactionTokenId() {
    return new TransactionTokenId(transactionTokenId);
  }

  public SubscriptionId getSubscriptionId() {
    return new SubscriptionId(subscriptionId);
  }
}
